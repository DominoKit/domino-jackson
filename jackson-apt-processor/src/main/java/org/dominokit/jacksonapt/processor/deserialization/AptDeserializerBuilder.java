/*
 * Copyright 2017 Ahmad Bawaneh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dominokit.jacksonapt.processor.deserialization;

import com.squareup.javapoet.*;
import org.dominokit.jacksonapt.JacksonContextProvider;
import org.dominokit.jacksonapt.JsonDeserializationContext;
import org.dominokit.jacksonapt.JsonDeserializerParameters;
import org.dominokit.jacksonapt.deser.bean.*;
import org.dominokit.jacksonapt.processor.AbstractJsonMapperGenerator;
import org.dominokit.jacksonapt.processor.Type;
import org.dominokit.jacksonapt.stream.JsonReader;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AptDeserializerBuilder extends AbstractJsonMapperGenerator {

    private static final WildcardTypeName DEFAULT_WILDCARD = WildcardTypeName.subtypeOf(Object.class);


    public AptDeserializerBuilder(TypeMirror beanType, Filer filer) {
        super(beanType, filer);
    }

    @Override
    protected TypeName superClass() {
        return ParameterizedTypeName.get(ClassName.get(AbstractBeanJsonDeserializer.class),
                ClassName.get(beanType));
    }

    @Override
    protected String namePostfix() {
        return Type.BEAN_JSON_DESERIALIZER_IMPL;
    }

    @Override
    protected String targetTypeMethodName() {
        return "getDeserializedType";
    }

    @Override
    protected MethodSpec initMethod() {
        return buildInitDeserializersMethod(beanType);
    }

    @Override
    protected Set<MethodSpec> moreMethods() {
        return Stream.of(buildInitInstanceBuilderMethod(beanType, ParameterizedTypeName
                .get(ClassName.get(MapLike.class), ClassName
                        .get(HasDeserializerAndParameters.class)))).collect(Collectors.toSet());
    }

    private MethodSpec buildInitInstanceBuilderMethod(TypeMirror beanType, ParameterizedTypeName parameterizedTypeName) {
        return MethodSpec.methodBuilder("initInstanceBuilder")
                .addModifiers(Modifier.PROTECTED)
                .returns(ParameterizedTypeName.get(ClassName.get(InstanceBuilder.class), ClassName.get(beanType)))
                .addStatement("final $T deserializers = null", parameterizedTypeName)
                .addStatement("return $L", instanceBuilderReturnType())
                .addAnnotation(Override.class)
                .build();
    }

    private TypeSpec instanceBuilderReturnType() {

        final MethodSpec createMethod = MethodSpec.methodBuilder("create")
                .addModifiers(Modifier.PRIVATE)
                .returns(ClassName.get(beanType))
                .addStatement("return new $T()", TypeName.get(beanType))
                .build();

        return TypeSpec.anonymousClassBuilder("")
                .addSuperinterface(ParameterizedTypeName.get(ClassName.get(InstanceBuilder.class),
                        ClassName.get(beanType)))
                .addMethod(newInstanceMethod(beanType, createMethod))
                .addMethod(getDeserializerMethod(beanType))
                .addMethod(createMethod).build();
    }

    private MethodSpec newInstanceMethod(TypeMirror beanType, MethodSpec createMethod) {
        return MethodSpec.methodBuilder("newInstance")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(ParameterizedTypeName.get(ClassName.get(Instance.class), ClassName.get(beanType)))
                .addParameter(JsonReader.class, "reader")
                .addParameter(JsonDeserializationContext.class, "ctx")
                .addParameter(JsonDeserializerParameters.class, "params")
                .addParameter(ParameterizedTypeName.get(Map.class, String.class, String.class), "bufferedProperties")
                .addParameter(ParameterizedTypeName.get(Map.class, String.class, Object.class), "bufferedPropertiesValues")
                .addStatement("return new $T($N(), bufferedProperties)",
                        ParameterizedTypeName.get(ClassName.get(Instance.class), ClassName.get(beanType)),
                        createMethod)
                .build();
    }

    private MethodSpec getDeserializerMethod(TypeMirror beanType) {
        return MethodSpec.methodBuilder("getParametersDeserializer")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addStatement("return deserializers")
                .returns(ParameterizedTypeName.get(ClassName.get(MapLike.class),
                        ClassName.get(HasDeserializerAndParameters.class)))
                .build();
    }

    private MethodSpec buildInitDeserializersMethod(TypeMirror beanType) {

        TypeName resultType = ParameterizedTypeName.get(ClassName.get(MapLike.class),
                ParameterizedTypeName.get(ClassName.get(BeanPropertyDeserializer.class),
                        TypeName.get(beanType), DEFAULT_WILDCARD));

        MethodSpec.Builder builder = MethodSpec.methodBuilder("initDeserializers")
                .addModifiers(Modifier.PROTECTED)
                .addAnnotation(Override.class)
                .returns(resultType)
                .addStatement("$T map = $T.get().mapLikeFactory().make()", resultType, JacksonContextProvider.class);

        orderedFields().stream().filter(this::isNotStatic).forEach(field -> builder.addStatement("map.put($S, $L)",
                field.getSimpleName(), new DeserializerBuilder(beanType, field).buildDeserializer()));

        builder.addStatement("return map");
        return builder.build();
    }

}
