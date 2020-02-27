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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.squareup.javapoet.*;
import org.dominokit.jacksonapt.JacksonContextProvider;
import org.dominokit.jacksonapt.JsonDeserializationContext;
import org.dominokit.jacksonapt.JsonDeserializer;
import org.dominokit.jacksonapt.JsonDeserializerParameters;
import org.dominokit.jacksonapt.deser.bean.*;
import org.dominokit.jacksonapt.deser.bean.SubtypeDeserializer.BeanSubtypeDeserializer;
import org.dominokit.jacksonapt.exception.JsonDeserializationException;
import org.dominokit.jacksonapt.processor.AbstractJsonMapperGenerator;
import org.dominokit.jacksonapt.processor.AbstractMapperProcessor;
import org.dominokit.jacksonapt.processor.BeanIdentityInfo;
import org.dominokit.jacksonapt.processor.Type;
import org.dominokit.jacksonapt.stream.JsonReader;
import org.dominokit.jacksonapt.stream.JsonToken;

import javax.annotation.processing.Filer;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.dominokit.jacksonapt.processor.AbstractMapperProcessor.elementUtils;
import static org.dominokit.jacksonapt.processor.AbstractMapperProcessor.typeUtils;

/**
 * <p>AptDeserializerBuilder class.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public class AptDeserializerBuilder extends AbstractJsonMapperGenerator {

    private static final WildcardTypeName DEFAULT_WILDCARD = WildcardTypeName.subtypeOf(Object.class);
    private List<ParameterDeserializerBuilder> parameterBuilders;


    /**
     * <p>Constructor for AptDeserializerBuilder.</p>
     *
     * @param beanType    a {@link javax.lang.model.type.TypeMirror} object.
     * @param packageName a {@link java.lang.String} object.
     * @param filer       a {@link javax.annotation.processing.Filer} object.
     */
    public AptDeserializerBuilder(String packageName, TypeMirror beanType, Filer filer) {
        super(packageName, beanType, filer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected TypeName superClass() {
        return ParameterizedTypeName.get(
                ClassName.get(AbstractBeanJsonDeserializer.class),
                ClassName.get(beanType));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String namePostfix() {
        return Type.BEAN_JSON_DESERIALIZER_IMPL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String targetTypeMethodName() {
        return "getDeserializedType";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected MethodSpec initMethod() {
        return buildInitDeserializersMethod();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected MethodSpec initSubtypesMethod() {
        if (subTypesInfo == null)
            return MethodSpec.methodBuilder("initMapSubtypeClassToDeserializer")
                    .addModifiers(Modifier.PROTECTED)
                    .addAnnotation(Override.class)
                    .returns(
                            ParameterizedTypeName.get(
                                    ClassName.get(Map.class),
                                    ClassName.get(Class.class),
                                    ClassName.get(SubtypeDeserializer.class)))
                    .addStatement("return $T.emptyMap()", Collections.class).build();
        else {
            MethodSpec.Builder builder = MethodSpec.methodBuilder("initMapSubtypeClassToDeserializer")
                    .addModifiers(Modifier.PROTECTED)
                    .addAnnotation(Override.class)
                    .returns(
                            ParameterizedTypeName.get(
                                    ClassName.get(Map.class),
                                    ClassName.get(Class.class),
                                    ClassName.get(SubtypeDeserializer.class)))
                    .addStatement("$T map = new $T($L)",
                            ParameterizedTypeName.get(
                                    ClassName.get(Map.class),
                                    ClassName.get(Class.class),
                                    ClassName.get(SubtypeDeserializer.class)),
                            ClassName.get(IdentityHashMap.class),
                            subTypesInfo.getSubTypes().size());

            for (Map.Entry<String, TypeMirror> subtypeEntry : subTypesInfo.getSubTypes().entrySet()) {
                // Prepare anonymous BeanTypeSerializer to delegate to the "real" serializer
                TypeSpec subtypeType = TypeSpec.anonymousClassBuilder("")
                        .superclass(ClassName.get(BeanSubtypeDeserializer.class))
                        .addMethod(MethodSpec.methodBuilder("newDeserializer")
                                .addModifiers(Modifier.PROTECTED)
                                .addAnnotation(Override.class)
                                .returns(ParameterizedTypeName.get(ClassName.get(JsonDeserializer.class), WildcardTypeName.subtypeOf(Object.class)))
                                .addStatement("return new $T()", ClassName.bestGuess(Type.deserializerName(packageName, subtypeEntry.getValue())))
                                .build()
                        ).build();

                builder.addStatement("map.put($T.class, $L)", TypeName.get(subtypeEntry.getValue()), subtypeType);
            }

            builder.addStatement("return map");

            return builder.build();

        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Set<MethodSpec> moreMethods() {
        Set<MethodSpec> methods = new HashSet<>();
        // Object instance can be created by InstanceBuilder
        // only for non-abstract classes
        if (beanType.getKind() == TypeKind.DECLARED
                && ((DeclaredType) beanType).asElement().getKind() == ElementKind.CLASS
                && !((DeclaredType) beanType).asElement().getModifiers().contains(Modifier.ABSTRACT)) {
            methods.add(buildInitInstanceBuilderMethod());
        }

        MethodSpec initIgnoreFieldsMethod = buildInitIgnoreFields(beanType);
        if (nonNull(initIgnoreFieldsMethod)) {
            methods.add(initIgnoreFieldsMethod);
        }

        JsonIgnoreProperties ignorePropertiesAnnotation = typeUtils.asElement(beanType).getAnnotation(JsonIgnoreProperties.class);

        if (nonNull(ignorePropertiesAnnotation)) {
            methods.add(buildIgnoreUnknownMethod(ignorePropertiesAnnotation.ignoreUnknown()));
        }

        Optional<BeanIdentityInfo> beanIdentityInfo = Type.processIdentity(beanType);
        if(beanIdentityInfo.isPresent()){
            methods.add(buildInitIdentityInfoMethod(beanIdentityInfo.get()));
        }

        return methods;
    }

    private MethodSpec buildInitIdentityInfoMethod(BeanIdentityInfo identityInfo) {

        MethodSpec.Builder builder = MethodSpec.methodBuilder("initIdentityInfo")
                .addModifiers(Modifier.PROTECTED)
                .addAnnotation(Override.class)
                .returns(ParameterizedTypeName.get(ClassName.get(IdentityDeserializationInfo.class), TypeName.get(beanType)));

        if (identityInfo.isIdABeanProperty()) {
            builder.addStatement("return $L", buildPropertyIdentifierDeserializationInfo(beanType, identityInfo));
        } else {
            builder.addStatement("return $L",
                    buildIdentifierDeserializationInfo(beanType, identityInfo,
                            new FieldDeserializersChainBuilder(identityInfo.getType().get()).getInstance(identityInfo.getType().get())));
        }

        return builder.build();
    }


    private TypeSpec buildIdentifierDeserializationInfo(TypeMirror type, BeanIdentityInfo identityInfo,
                                                        CodeBlock deserializerType) {
        return TypeSpec.anonymousClassBuilder("$S, $T.class, $T.class",
                identityInfo.getPropertyName(), identityInfo.getGenerator(), identityInfo.getScope().get())
                .superclass(ParameterizedTypeName.get(ClassName.get(AbstractIdentityDeserializationInfo.class), TypeName.get(type), TypeName.get(identityInfo.getType().get())))
                .addMethod(MethodSpec.methodBuilder("newDeserializer")
                        .addModifiers(Modifier.PROTECTED)
                        .addAnnotation(Override.class)
                        .returns(ParameterizedTypeName.get(ClassName.get(JsonDeserializer.class), DEFAULT_WILDCARD))
                        .addStatement("return $L", deserializerType)
                        .build()
                ).build();
    }

    private CodeBlock buildPropertyIdentifierDeserializationInfo(TypeMirror type, BeanIdentityInfo identityInfo) {
        return CodeBlock.builder().
                add("new $T($S, $T.class, $T.class)", ParameterizedTypeName.get(ClassName.get(PropertyIdentityDeserializationInfo.class), TypeName.get(type)),
                        identityInfo.getPropertyName(), identityInfo.getGenerator(), identityInfo.getScope().get())
                .build();
    }

    private boolean isUseJsonCreator() {
        return ((DeclaredType) beanType).asElement().getEnclosedElements()
                .stream()
                .anyMatch(o -> o.getAnnotation(JsonCreator.class) != null);
    }

    private ExecutableElement getCreator() {
        return ((DeclaredType) beanType).asElement()
                .getEnclosedElements()
                .stream()
                .filter(o -> o.getAnnotation(JsonCreator.class) != null)
                .map(o -> (ExecutableElement) o)
                .findFirst()
                .orElse(null);
    }

    private boolean isUseBuilder() {
        String builderName = getBuilderName();
        return builderName != null && !builderName.isEmpty();
    }

    private String getBuilderName() {
        JsonDeserialize jsonDeserialize = typeUtils.asElement(beanType).getAnnotation(JsonDeserialize.class);
        if (isNull(jsonDeserialize)) {
            return null;
        }
        String builderName;
        try {
            builderName = jsonDeserialize.builder().getName();
        } catch (MirroredTypeException e) {
            TypeMirror typeMirror = e.getTypeMirror();
            builderName = typeMirror.toString();
        }
        return builderName;
    }

    private MethodSpec buildIgnoreUnknownMethod(boolean ignored) {

        MethodSpec.Builder builder = MethodSpec.methodBuilder("isDefaultIgnoreUnknown")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .returns(TypeName.BOOLEAN)
                .addStatement("return $L", ignored);

        return builder.build();
    }


    /**
     * @param beanType
     * @return MethodSpec for the set of ignored fields. if no ignored fields exist return null;
     */
    private MethodSpec buildInitIgnoreFields(TypeMirror beanType) {
        MethodSpec.Builder builder;
        final List<Element> ignoredFields = getIgnoredFields(beanType);

        if (!ignoredFields.isEmpty()) {
            builder = MethodSpec.methodBuilder("initIgnoredProperties");
            builder
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PROTECTED)
                    .returns(ParameterizedTypeName.get(HashSet.class, String.class))
                    .addStatement("HashSet<String> col = new HashSet<String>(" + ignoredFields.size() + ")");
            ignoredFields.forEach(f -> builder.addStatement("col.add(\"" + getPropertyName(f) + "\")"));
            builder.addStatement("return col");

            return builder.build();
        }

        return null;
    }

    private List<Element> getIgnoredFields(TypeMirror beanType) {
        TypeElement typeElement = (TypeElement) typeUtils.asElement(beanType);
        List<Element> ignoredFields = getIgnoredFields(typeElement);
        return new ArrayList<>(ignoredFields);
    }

    private List<Element> getIgnoredFields(TypeElement typeElement) {
        TypeMirror superclass = typeElement.getSuperclass();
        if (superclass.getKind().equals(TypeKind.NONE)) {
            return new ArrayList<>();
        }

        final List<Element> ignoredFields = typeElement
                .getEnclosedElements()
                .stream()
                .filter(e -> ElementKind.FIELD
                        .equals(e.getKind()) && isNotStatic(e) && isIgnored(e))
                .collect(Collectors.toList());

        ignoredFields.addAll(getIgnoredFields((TypeElement) typeUtils.asElement(superclass)));
        return ignoredFields;
    }

    private MethodSpec buildInitInstanceBuilderMethod() {
        ParameterizedTypeName deserializersTypeName = ParameterizedTypeName
                .get(ClassName.get(MapLike.class), ClassName
                        .get(HasDeserializerAndParameters.class));
        MethodSpec.Builder initInstanceMethodBuilder = MethodSpec.methodBuilder("initInstanceBuilder")
                .addModifiers(Modifier.PROTECTED)
                .returns(ParameterizedTypeName.get(ClassName.get(InstanceBuilder.class), ClassName.get(beanType)));

        if (isUseJsonCreator()) {
            ExecutableElement creator = getCreator();
            List<? extends VariableElement> parameterTypes = creator.getParameters();
            parameterBuilders = parameterTypes.stream()
                    .map(o -> new ParameterDeserializerBuilder(typeUtils, beanType, o, packageName))
                    .collect(Collectors.toList());
            initInstanceMethodBuilder.addStatement("final $T deserializers = $T.get().mapLikeFactory().make()", deserializersTypeName, JacksonContextProvider.class);
            buildPropertiesDeserializers(initInstanceMethodBuilder);
        } else {
            initInstanceMethodBuilder.addStatement("final $T deserializers = null", deserializersTypeName);
        }

        boolean useBuilder = isUseBuilder();
        if (useBuilder) {
            TypeElement builderElement = getBuilderElement();
            initInstanceMethodBuilder.addStatement("final $T builderDeserializer = new $T()",
                    ParameterizedTypeName.get(ClassName.get(AbstractBeanJsonDeserializer.class), ClassName.get(builderElement)),
                    builderDeserializerName(builderElement));
            try {
                new AptDeserializerBuilder(packageName, builderElement.asType(), AbstractMapperProcessor.filer).generate();
            } catch (IOException e) {
                throw new JsonDeserializationException(e);
            }
        }

        return initInstanceMethodBuilder
                .addStatement("return $L", instanceBuilderReturnType(useBuilder))
                .addAnnotation(Override.class)
                .build();
    }

    private void buildPropertiesDeserializers(MethodSpec.Builder initInstanceMethodBuilder) {
        for (ParameterDeserializerBuilder parameterBuilder : parameterBuilders) {
            initInstanceMethodBuilder.addCode(parameterBuilder.build());
            initInstanceMethodBuilder.addStatement("deserializers.put($S, $L)", parameterBuilder.getParameterName(), parameterBuilder.getDeserializerName());
        }
    }

    private TypeElement getBuilderElement() {
        String builderName = getBuilderName();
        return elementUtils.getTypeElement(builderName);
    }

    private ClassName builderDeserializerName(TypeElement builder) {
        return ClassName.bestGuess(builder.getSimpleName() + namePostfix());
    }

    private TypeSpec instanceBuilderReturnType(boolean useBuilder) {
        MethodSpec.Builder createMethodBuilder = MethodSpec.methodBuilder("create")
                .addModifiers(Modifier.PRIVATE)
                .returns(ClassName.get(beanType));

        if (isUseJsonCreator()) {
            for (ParameterDeserializerBuilder parameterBuilder : parameterBuilders) {
                createMethodBuilder.addParameter(Type.wrapperType(parameterBuilder.getParameterType()), parameterBuilder.getParameterName());
            }
            StringBuilder newBeanBuilder = new StringBuilder()
                    .append("return new $T")
                    .append(Type.isGenericType(beanType) ? "<>(" : "(");
            String parametersString = parameterBuilders.stream()
                    .map(ParameterDeserializerBuilder::getParameterName)
                    .collect(Collectors.joining(", "));
            newBeanBuilder.append(parametersString).append(")");
            createMethodBuilder.addStatement(newBeanBuilder.toString(), TypeName.get(typeUtils.erasure(beanType)));
        } else {
            createMethodBuilder.addStatement((Type.isGenericType(beanType) ?
                            "return new $T<>()" :
                            "return new $T()"),
                    TypeName.get(typeUtils.erasure(beanType)));
        }

        final MethodSpec createMethod = createMethodBuilder.build();

        TypeSpec.Builder builder = TypeSpec.anonymousClassBuilder("")
                .addSuperinterface(ParameterizedTypeName.get(ClassName.get(InstanceBuilder.class),
                        ClassName.get(beanType)))
                .addMethod(newInstanceMethod(beanType, createMethod, useBuilder))
                .addMethod(getDeserializerMethod());

        if (!useBuilder) {
            builder.addMethod(createMethod);
        }
        return builder.build();
    }

    private MethodSpec newInstanceMethod(TypeMirror beanType, MethodSpec createMethod, boolean useBuilder) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("newInstance")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(ParameterizedTypeName.get(ClassName.get(Instance.class), ClassName.get(beanType)))
                .addParameter(JsonReader.class, "reader")
                .addParameter(JsonDeserializationContext.class, "ctx")
                .addParameter(JsonDeserializerParameters.class, "params")
                .addParameter(ParameterizedTypeName.get(Map.class, String.class, String.class), "bufferedProperties")
                .addParameter(ParameterizedTypeName.get(Map.class, String.class, Object.class), "bufferedPropertiesValues");

        if (useBuilder) {
            TypeElement builderElement = getBuilderElement();
            JsonPOJOBuilder jsonPOJOBuilder = builderElement.getAnnotation(JsonPOJOBuilder.class);
            String buildMethodName = JsonPOJOBuilder.DEFAULT_BUILD_METHOD;
            if (nonNull(jsonPOJOBuilder)) {
                buildMethodName = jsonPOJOBuilder.buildMethodName();
            }
            builder.addStatement("return new $T(builderDeserializer.deserializeInline(reader, ctx, params, null, null, null, bufferedProperties)." + buildMethodName + "(), bufferedProperties)",
                    ParameterizedTypeName.get(ClassName.get(Instance.class), ClassName.get(beanType)));
        } else if (isUseJsonCreator()) {
            buildAssignProperties(beanType, createMethod, builder);
        } else {
            builder.addStatement("return new $T($N(), bufferedProperties)",
                    ParameterizedTypeName.get(ClassName.get(Instance.class), ClassName.get(beanType)),
                    createMethod);
        }

        return builder.build();
    }

    private void buildAssignProperties(TypeMirror beanType, MethodSpec createMethod, MethodSpec.Builder builder) {
        for (ParameterDeserializerBuilder parameterBuilder : parameterBuilders) {
            builder.addStatement("$T $L = null", Type.wrapperType(parameterBuilder.getParameterType()), parameterBuilder.getParameterName() + "Property");
        }
        builder.beginControlFlow("while ($T.NAME == reader.peek())", JsonToken.class);
        builder.addStatement("String nextName = reader.nextName()");
        for (ParameterDeserializerBuilder parameterBuilder : parameterBuilders) {
            String paramName = parameterBuilder.getParameterName();
            builder.beginControlFlow("if($S.equals(nextName))", paramName);
            builder.addStatement("$L = $L.deserialize(reader, ctx)", paramName + "Property", paramName + "Deserializer");
            builder.addStatement("continue");
            builder.endControlFlow();
        }
        builder.endControlFlow();
        String argumentsString = parameterBuilders
                .stream()
                .map(ParameterDeserializerBuilder::getParameterName)
                .map(name -> name + "Property")
                .collect(Collectors.joining(", "));

        builder.addStatement("return new $T($N($L), bufferedProperties)",
                ParameterizedTypeName.get(ClassName.get(Instance.class), ClassName.get(beanType)),
                createMethod,
                argumentsString);
    }

    private MethodSpec getDeserializerMethod() {
        return MethodSpec.methodBuilder("getParametersDeserializer")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addStatement("return deserializers")
                .returns(ParameterizedTypeName.get(ClassName.get(MapLike.class),
                        ClassName.get(HasDeserializerAndParameters.class)))
                .build();
    }

    private MethodSpec buildInitDeserializersMethod() {
        if (isUseBuilder() || isUseJsonCreator()) {
            return null;
        }
        TypeName resultType = ParameterizedTypeName.get(ClassName.get(MapLike.class),
                ParameterizedTypeName.get(
                        ClassName.get(BeanPropertyDeserializer.class),
                        TypeName.get(beanType), DEFAULT_WILDCARD));

        MethodSpec.Builder builder = MethodSpec.methodBuilder("initDeserializers")
                .addModifiers(Modifier.PROTECTED)
                .addAnnotation(Override.class)
                .returns(resultType)
                .addStatement("$T map = $T.get().mapLikeFactory().make()", resultType, JacksonContextProvider.class);

        orderedFields().entrySet().stream()
                .filter(entry -> isEligibleForSerializationDeserialization(entry.getKey()))
                .forEach(entry -> builder.addStatement("map.put($S, $L)",
                        getPropertyName(entry.getKey()), new DeserializerBuilder(typeUtils, beanType, packageName, entry.getKey(), entry.getValue()).buildDeserializer()));

        builder.addStatement("return map");
        return builder.build();
    }

    /**
     * @param field
     * @return the field provided in the {@link JsonProperty} as long as the provided name is not JsonProperty.USE_DEFAULT_NAME otherwise return the field simple name
     */
    private String getPropertyName(Element field) {
        JsonProperty annotation = field.getAnnotation(JsonProperty.class);
        if (isNull(annotation) || JsonProperty.USE_DEFAULT_NAME.equals(annotation.value())) {
            return field.getSimpleName().toString();
        } else {
            return annotation.value();
        }
    }

    @Override
    protected Class<?> getMapperType() {
        return TypeDeserializationInfo.class;
    }
}
