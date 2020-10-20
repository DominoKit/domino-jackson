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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.squareup.javapoet.*;
import org.dominokit.jacksonapt.JacksonContextProvider;
import org.dominokit.jacksonapt.JsonDeserializationContext;
import org.dominokit.jacksonapt.JsonDeserializer;
import org.dominokit.jacksonapt.JsonDeserializerParameters;
import org.dominokit.jacksonapt.deser.bean.BeanPropertyDeserializer;
import org.dominokit.jacksonapt.processor.AbstractJsonMapperGenerator.AccessorInfo;
import org.dominokit.jacksonapt.processor.AccessorsFilter;
import org.dominokit.jacksonapt.processor.ObjectMapperProcessor;
import org.dominokit.jacksonapt.processor.Type;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import java.util.Optional;

import static java.util.Objects.nonNull;

class DeserializerBuilder extends AccessorsFilter {

    private final TypeMirror beanType;
    private final Element field;
    private final TypeMirror fieldType;

    DeserializerBuilder(Types typeUtils, TypeMirror beanType, Element field, TypeMirror fieldType) {
        super(typeUtils);
        this.beanType = beanType;
        this.field = field;
        this.fieldType = fieldType;
    }
    
    TypeSpec buildDeserializer() {
        final String paramValue = "value";
        final String paramBean = "bean";

        TypeSpec.Builder builder = TypeSpec.anonymousClassBuilder("")
                .superclass(ParameterizedTypeName
                        .get(ClassName.get(BeanPropertyDeserializer.class), TypeName.get(beanType), Type.wrapperType(fieldType)));

        builder.addMethod(buildDeserializerMethod());

        if(nonNull(field.getAnnotation(JsonFormat.class))){
            builder.addMethod(buildParametersMethod());
        }

        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("setValue")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(ClassName.get(beanType), paramBean);

        AccessorInfo accessorInfo = setterInfo(field);

        methodBuilder.addParameter(Type.wrapperType(fieldType), paramValue)
                .addParameter(JsonDeserializationContext.class, "ctx")
                .addStatement("$L", paramBean + "." + accessorInfo.getName() + (accessorInfo.method.isPresent() ? "(" : "=") + paramValue + (accessorInfo.method.isPresent() ? ")" : ""));

        builder.addMethod(methodBuilder.build());

        return builder.build();
    }

    private MethodSpec buildDeserializerMethod() {
        return MethodSpec.methodBuilder("newDeserializer")
                .addModifiers(Modifier.PROTECTED)
                .addAnnotation(Override.class)
                .returns(ParameterizedTypeName.get(ClassName.get(JsonDeserializer.class), ObjectMapperProcessor.DEFAULT_WILDCARD))
                .addStatement("return $L", new FieldDeserializersChainBuilder(beanType).getInstance(fieldType))
                .build();
    }

    private MethodSpec buildParametersMethod(){
        JsonFormat jsonFormat = field.getAnnotation(JsonFormat.class);
        return MethodSpec.methodBuilder("newParameters")
                .addModifiers(Modifier.PROTECTED)
                .addAnnotation(Override.class)
                .returns(JsonDeserializerParameters.class)
                .addStatement("return $T.get()\n\t\t.newDeserializerParameters()\n\t\t.setPattern($S)\n\t\t.setShape($T.$L)"
                        ,TypeName.get(JacksonContextProvider.class)
                        ,jsonFormat.pattern()
                        ,TypeName.get(JsonFormat.Shape.class)
                        ,jsonFormat.shape().toString())
                .build();
    }

    private AccessorInfo setterInfo(Element field) {
        final String upperCaseFirstLetter = upperCaseFirstLetter(field.getSimpleName().toString());

        Optional<AccessorInfo> accessor = getAccessors(beanType)
                .stream()
                .filter(accessorInfo -> accessorInfo.getName().equals("set" + upperCaseFirstLetter))
                .findFirst();

        return accessor.orElseGet(() -> new AccessorInfo(field.getSimpleName().toString()));
    }

    private String upperCaseFirstLetter(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }


}
