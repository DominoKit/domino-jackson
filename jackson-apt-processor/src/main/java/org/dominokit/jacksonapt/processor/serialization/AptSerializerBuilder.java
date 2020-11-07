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

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.google.auto.common.MoreElements;
import com.google.auto.common.MoreTypes;
import com.squareup.javapoet.*;
import org.dominokit.jacksonapt.JsonSerializationContext;
import org.dominokit.jacksonapt.JsonSerializer;
import org.dominokit.jacksonapt.processor.AbstractJsonMapperGenerator;
import org.dominokit.jacksonapt.processor.BeanIdentityInfo;
import org.dominokit.jacksonapt.processor.Type;
import org.dominokit.jacksonapt.ser.bean.*;
import org.dominokit.jacksonapt.ser.bean.SubtypeSerializer.BeanSubtypeSerializer;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.util.*;

import static org.dominokit.jacksonapt.processor.AbstractMapperProcessor.messager;
import static org.dominokit.jacksonapt.processor.AbstractMapperProcessor.typeUtils;
import static org.dominokit.jacksonapt.processor.ObjectMapperProcessor.DEFAULT_WILDCARD;

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
    protected Optional<MethodSpec> initMethod() {
        return buildInitSerializersMethod(beanType);
    }

    @Override
    protected Set<MethodSpec> moreMethods() {
        Set<MethodSpec> methods = new HashSet<>();

        Optional<BeanIdentityInfo> beanIdentityInfo = Type.processIdentity(beanType);
        if (beanIdentityInfo.isPresent()) {
            Optional<CodeBlock> serializerType = getIdentitySerializerType(beanIdentityInfo.get());
            methods.add(buildInitIdentityInfoMethod(serializerType, beanIdentityInfo));
        }

        return methods;
    }

    private Optional<CodeBlock> getIdentitySerializerType(BeanIdentityInfo identityInfo) {
        if (identityInfo.isIdABeanProperty()) {
            return Optional.empty();
        } else {
            return Optional.of(new FieldSerializerChainBuilder(identityInfo.getType().get()).getInstance(identityInfo.getType().get()));
        }
    }


    private MethodSpec buildInitIdentityInfoMethod(Optional<CodeBlock> serializerType, Optional<BeanIdentityInfo> beanIdentityInfo) {
        return MethodSpec.methodBuilder("initIdentityInfo")
                .addModifiers(Modifier.PROTECTED)
                .addAnnotation(Override.class)
                .returns(ParameterizedTypeName.get(ClassName.get(IdentitySerializationInfo.class), TypeName.get(beanType)))
                .addStatement("return $L",
                        generateIdentifierSerializationInfo(beanIdentityInfo.get(), serializerType))
                .build();
    }

    private TypeSpec generateIdentifierSerializationInfo(BeanIdentityInfo identityInfo,
                                                         Optional<CodeBlock> serializerType) {

        TypeSpec.Builder builder = TypeSpec
                .anonymousClassBuilder("$L, $S", identityInfo.isAlwaysAsId(), identityInfo.getPropertyName());

        if (identityInfo.isIdABeanProperty()) {

            Map<Element, TypeMirror> fieldsMap = orderedFields();
            Optional<Map.Entry<Element, TypeMirror>> propertyEntry = fieldsMap
                    .entrySet()
                    .stream()
                    .filter(entry -> entry.getKey().getSimpleName().toString().equals(identityInfo.getPropertyName()))
                    .findFirst();
            if (propertyEntry.isPresent()) {
                builder.superclass(ParameterizedTypeName.get(ClassName.get(PropertyIdentitySerializationInfo.class), TypeName.get(beanType), Type.wrapperType(propertyEntry.get().getValue())));
                buildBeanPropertySerializerBody(builder, propertyEntry.get());
            } else {
                messager.printMessage(Diagnostic.Kind.ERROR, "Property [" + identityInfo.getPropertyName() + "] not found in type [" + beanType.toString() + "]!.");
            }

        } else {
            TypeMirror qualifiedType = identityInfo.getType().get();

            builder.superclass(ParameterizedTypeName.get(ClassName.get(AbstractIdentitySerializationInfo.class), TypeName.get(beanType), TypeName.get(qualifiedType)));

            builder.addMethod(MethodSpec.methodBuilder("newSerializer")
                    .addModifiers(Modifier.PROTECTED)
                    .addAnnotation(Override.class)
                    .returns(ParameterizedTypeName.get(ClassName.get(JsonSerializer.class), DEFAULT_WILDCARD))
                    .addStatement("return $L", serializerType.get())
                    .build());

            TypeName generatorType = ParameterizedTypeName.get(ClassName.get(ObjectIdGenerator.class), TypeName.get(qualifiedType));
            TypeName returnType = ParameterizedTypeName.get(ClassName.get(ObjectIdSerializer.class), TypeName.get(qualifiedType));

            builder.addMethod(MethodSpec.methodBuilder("getObjectId")
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Override.class)
                    .returns(returnType)
                    .addParameter(TypeName.get(beanType), "bean")
                    .addParameter(JsonSerializationContext.class, "ctx")
                    .addStatement("$T generator = new $T().forScope($T.class)",
                            generatorType, identityInfo.getGenerator(), identityInfo.getScope().get())
                    .addStatement("$T scopedGen = ctx.findObjectIdGenerator(generator)", generatorType)
                    .beginControlFlow("if (null == scopedGen)")
                    .addStatement("scopedGen = generator.newForSerialization(ctx)")
                    .addStatement("ctx.addGenerator(scopedGen)")
                    .endControlFlow()
                    .addStatement("return new $T(scopedGen.generateId(bean), getSerializer())", returnType)
                    .build());
        }

        return builder.build();
    }


    private void buildBeanPropertySerializerBody(TypeSpec.Builder builder, Map.Entry<Element, TypeMirror> property) {
        String paramName = "bean";
        AccessorInfo accessorInfo = new SerializerBuilder(typeUtils, beanType, property.getKey(), property.getValue()).getterInfo();

        MethodSpec.Builder newSerializerMethodBuilder = MethodSpec.methodBuilder("newSerializer")
                .addModifiers(Modifier.PROTECTED)
                .addAnnotation(Override.class)
                .addStatement("return $L", new FieldSerializerChainBuilder(property.getValue()).getInstance(property.getValue()));
        newSerializerMethodBuilder.returns(ParameterizedTypeName.get(ClassName.get(JsonSerializer.class), DEFAULT_WILDCARD));
        builder.addMethod(newSerializerMethodBuilder.build());

        builder.addMethod(MethodSpec.methodBuilder("getValue")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(Type.wrapperType(property.getValue()))
                .addParameter(TypeName.get(beanType), paramName)
                .addParameter(JsonSerializationContext.class, "ctx")
                .addStatement("return $L.$L()", paramName, accessorInfo.getName())
                .build()
        );
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
                String pkg = MoreElements.getPackage(
                        MoreTypes.asTypeElement(subtypeEntry.getValue())).getQualifiedName().toString();
                TypeSpec subtypeType = TypeSpec.anonymousClassBuilder("")
                        .superclass(ClassName.get(BeanSubtypeSerializer.class))
                        .addMethod(MethodSpec.methodBuilder("newSerializer")
                                .addModifiers(Modifier.PROTECTED)
                                .addAnnotation(Override.class)
                                .returns(ParameterizedTypeName.get(ClassName.get(JsonSerializer.class), WildcardTypeName.subtypeOf(Object.class)))
                                .addStatement("return new $L()", ClassName.bestGuess(
                                        Type.serializerName(pkg, subtypeEntry.getValue())))
                                .build()
                        ).build();

                builder.addStatement("map.put($T.class, $L)", TypeName.get(subtypeEntry.getValue()), subtypeType);
            }

            builder.addStatement("return map");
            return builder.build();
        }

    }

    private Optional<MethodSpec> buildInitSerializersMethod(TypeMirror beanType) {
        if (isAbstract(beanType)) {
            return Optional.empty();
        }
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
                        index[0]++, new SerializerBuilder(typeUtils, beanType, entry.getKey(), entry.getValue()).buildSerializer()));

        builder.addStatement("return result");
        return Optional.of(builder.build());
    }

    @Override
    protected Class<?> getMapperType() {
        return TypeSerializationInfo.class;
    }
}
