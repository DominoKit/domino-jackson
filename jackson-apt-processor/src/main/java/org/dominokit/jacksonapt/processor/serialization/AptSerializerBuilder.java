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
package org.dominokit.jacksonapt.processor.serialization;

import com.squareup.javapoet.*;

import org.dominokit.jacksonapt.JsonSerializer;
import org.dominokit.jacksonapt.deser.bean.TypeDeserializationInfo;
import org.dominokit.jacksonapt.processor.AbstractJsonMapperGenerator;
import org.dominokit.jacksonapt.processor.Type;
import org.dominokit.jacksonapt.ser.bean.AbstractBeanJsonSerializer;
import org.dominokit.jacksonapt.ser.bean.BeanPropertySerializer;
import org.dominokit.jacksonapt.ser.bean.SubtypeSerializer;
import org.dominokit.jacksonapt.ser.bean.SubtypeSerializer.BeanSubtypeSerializer;
import org.dominokit.jacksonapt.ser.bean.TypeSerializationInfo;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;

import static org.dominokit.jacksonapt.processor.AbstractMapperProcessor.typeUtils;

/**
 * <p>AptSerializerBuilder class.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public class AptSerializerBuilder extends AbstractJsonMapperGenerator {

    /**
     * <p>Constructor for AptSerializerBuilder.</p>
     *
     * @param beanType    a {@link javax.lang.model.type.TypeMirror} object.
     * @param packageName a {@link java.lang.String} object.
     * @param filer       a {@link javax.annotation.processing.Filer} object.
     */
    public AptSerializerBuilder(String packageName, TypeMirror beanType, Filer filer) {
        super(packageName, beanType, filer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected TypeName superClass() {
        return ParameterizedTypeName.get(ClassName.get(AbstractBeanJsonSerializer.class),
                ClassName.get(beanType));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String namePostfix() {
        return Type.BEAN_JSON_SERIALIZER_IMPL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String targetTypeMethodName() {
        return "getSerializedType";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected MethodSpec initMethod() {
        return buildInitSerializersMethod(beanType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected MethodSpec initSubtypesMethod() {
        if (subTypesInfo == null) {
            return MethodSpec.methodBuilder("initMapSubtypeClassToSerializer")
                    .addModifiers(Modifier.PROTECTED)
                    .addAnnotation(Override.class)
                    .returns(
                            ParameterizedTypeName.get(
                                    ClassName.get(Map.class),
                                    ClassName.get(Class.class),
                                    ClassName.get(SubtypeSerializer.class)))
                    .addStatement("return $T.emptyMap()", Collections.class).build();
        } else {
            MethodSpec.Builder builder = MethodSpec.methodBuilder("initMapSubtypeClassToSerializer")
                    .addModifiers(Modifier.PROTECTED)
                    .addAnnotation(Override.class)
                    .returns(
                            ParameterizedTypeName.get(
                                    ClassName.get(Map.class),
                                    ClassName.get(Class.class),
                                    ClassName.get(SubtypeSerializer.class)))
                    .addStatement("$T map = new $T($L)",
                            ParameterizedTypeName.get(
                                    ClassName.get(Map.class),
                                    ClassName.get(Class.class),
                                    ClassName.get(SubtypeSerializer.class)),
                            ClassName.get(IdentityHashMap.class),
                            subTypesInfo.getSubTypes().size());

            for (Map.Entry<String, TypeMirror> subtypeEntry : subTypesInfo.getSubTypes().entrySet()) {
                // Prepare anonymous BeanTypeSerializer to delegate to the "real" serializer
                TypeSpec subtypeType = TypeSpec.anonymousClassBuilder("")
                        .superclass(ClassName.get(BeanSubtypeSerializer.class))
                        .addMethod(MethodSpec.methodBuilder("newSerializer")
                                .addModifiers(Modifier.PROTECTED)
                                .addAnnotation(Override.class)
                                .returns(ParameterizedTypeName.get(ClassName.get(JsonSerializer.class), WildcardTypeName.subtypeOf(Object.class)))
                                .addStatement("return new $T()", ClassName.bestGuess(Type.serializerName(packageName, subtypeEntry.getValue())))
                                .build()
                        ).build();

                builder.addStatement("map.put($T.class, $L)", TypeName.get(subtypeEntry.getValue()), subtypeType);
            }

            builder.addStatement("return map");
            return builder.build();
        }

    }

    private MethodSpec buildInitSerializersMethod(TypeMirror beanType) {

        int[] index = new int[]{0};
        final Map<Element, TypeMirror> fields = orderedFields();

        MethodSpec.Builder builder = MethodSpec.methodBuilder("initSerializers")
                .addModifiers(Modifier.PROTECTED)
                .addAnnotation(Override.class)
                .returns(TypeName.get(BeanPropertySerializer[].class))
                .addStatement("$T result = new $T[$L]",
                        ArrayTypeName.of(BeanPropertySerializer.class), BeanPropertySerializer.class, fields.size());

        fields.entrySet().stream()
                .filter(entry -> isEligibleForSerializationDeserialization(entry.getKey()))
                .forEach(entry -> builder.addStatement("result[$L] = $L",
                        index[0]++, new SerializerBuilder(typeUtils, beanType, packageName, entry.getKey(), entry.getValue()).buildSerializer()));

        builder.addStatement("return result");
        return builder.build();
    }

    @Override
    protected Class<?> getMapperType(){
        return TypeSerializationInfo.class;
    }
}
