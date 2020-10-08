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
package org.dominokit.jacksonapt.processor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.squareup.javapoet.*;
import org.dominokit.jacksonapt.deser.bean.TypeDeserializationInfo;
import org.dominokit.jacksonapt.ser.bean.TypeSerializationInfo;

import javax.annotation.processing.Filer;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Objects.nonNull;
import static org.dominokit.jacksonapt.processor.ObjectMapperProcessor.typeUtils;

/**
 * <p>Abstract AbstractJsonMapperGenerator class.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public abstract class AbstractJsonMapperGenerator {

    protected final TypeMirror beanType;

    protected final SubTypesInfo subTypesInfo;

    protected final String packageName;

    private final Filer filer;

    /**
     * <p>Constructor for AbstractJsonMapperGenerator.</p>
     *
     * @param beanType    a {@link javax.lang.model.type.TypeMirror} object.
     * @param packageName a {@link java.lang.String} object.
     * @param filer       a {@link javax.annotation.processing.Filer} object.
     */
    public AbstractJsonMapperGenerator(String packageName, TypeMirror beanType, Filer filer) {
        this.beanType = beanType;
        this.subTypesInfo = Type.getSubTypes(beanType);
        this.packageName = packageName;
        this.filer = filer;
    }

    /**
     * <p>generate.</p>
     *
     * @throws java.io.IOException if any.
     */
    protected void generate() throws IOException {
        MethodSpec constructor = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC).build();

        final TypeSpec.Builder builder = TypeSpec.classBuilder(Type.stringifyType(beanType) + namePostfix())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .superclass(superClass())
                .addMethod(constructor)
                .addMethod(targetTypeMethod());

        moreMethods().forEach(builder::addMethod);

        Optional<MethodSpec> initMethod = initMethod();
        initMethod.ifPresent(builder::addMethod);
        if (subTypesInfo.hasSubTypes()) {
            builder.addMethod(buildInitTypeInfoMethod());
            builder.addMethod(initSubtypesMethod());
        }

        JavaFile.builder(packageName, builder.build()).build().writeTo(filer);
    }

    private MethodSpec targetTypeMethod() {
        return MethodSpec.methodBuilder(targetTypeMethodName())
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(ClassName.get(Class.class))
                .addStatement("return $T.class", TypeName.get(ObjectMapperProcessor.typeUtils.erasure(beanType)))
                .build();
    }

    /**
     * <p>superClass.</p>
     *
     * @return a {@link com.squareup.javapoet.TypeName} object.
     */
    protected abstract TypeName superClass();

    /**
     * <p>namePostfix.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    protected abstract String namePostfix();

    /**
     * <p>targetTypeMethodName.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    protected abstract String targetTypeMethodName();

    /**
     * <p>moreMethods.</p>
     *
     * @return a {@link java.util.Set} object.
     */
    protected Set<MethodSpec> moreMethods() {
        return Collections.emptySet();
    }

    /**
     * <p>initMethod.</p>
     *
     * @return a {@link com.squareup.javapoet.MethodSpec} object.
     */
    protected abstract Optional<MethodSpec> initMethod();

    /**
     * <p>initMethod.</p>
     *
     * @return a {@link com.squareup.javapoet.MethodSpec} object.
     */
    protected abstract MethodSpec initSubtypesMethod();

    /**
     * <p>orderedFields.</p>
     *
     * @return a {@link java.util.List} object.
     */
    protected Map<Element, TypeMirror> orderedFields() {
        return
                beanType.getKind() == TypeKind.DECLARED ?
                        getOrderedFields(((DeclaredType) beanType)) :
                        Collections.emptyMap();
    }

    private Map<Element, TypeMirror> getOrderedFields(DeclaredType enclosingType) {
        TypeElement enclosingElement = ((TypeElement) enclosingType.asElement());
        TypeMirror superclass = enclosingElement.getSuperclass();
        if (superclass.getKind().equals(TypeKind.NONE)) {
            return new HashMap<>();
        }

        final List<Element> orderedProperties = new ArrayList<>();

        final List<Element> enclosedFields = enclosingElement.getEnclosedElements().stream()
                .filter(e -> ElementKind.FIELD.equals(e.getKind()) && isEligibleForSerializationDeserialization(e))
                .collect(Collectors.toList());

        Optional.ofNullable(typeUtils.asElement(beanType).getAnnotation(JsonPropertyOrder.class))
                .ifPresent(jsonPropertyOrder -> {
                    final List<String> orderedFieldsNames = Arrays.asList(jsonPropertyOrder.value());
                    orderedProperties.addAll(enclosedFields.stream()
                            .filter(f -> orderedFieldsNames.contains(f.getSimpleName().toString()))
                            .collect(Collectors.toList()));

                    enclosedFields.removeAll(orderedProperties);
                    if (jsonPropertyOrder.alphabetic()) {
                        enclosedFields.sort(Comparator.comparing(f -> f.getSimpleName().toString()));
                    }

                    enclosedFields.addAll(0, orderedProperties);
                });


        List<? extends TypeParameterElement> typeParameters = enclosingElement.getTypeParameters();
        List<? extends TypeMirror> typeArguments = enclosingType.getTypeArguments();
        final Map<? extends TypeParameterElement, ? extends TypeMirror> typeParameterMap =
                IntStream.range(0, typeArguments.size())
                        .boxed()
                        .collect(Collectors.toMap(typeParameters::get, typeArguments::get));

        Map<Element, TypeMirror> res = enclosedFields.stream().collect(
                Collectors.toMap(
                        fieldElement -> fieldElement,
                        fieldElement -> Type.getDeclaredType(fieldElement.asType(), typeParameterMap),
                        (u, v) -> {
                            throw new IllegalStateException(String.format("Duplicate key %s", u));
                        },
                        LinkedHashMap::new));

        if (superclass.getKind() == TypeKind.DECLARED)
            res.putAll(getOrderedFields((DeclaredType) Type.getDeclaredType(superclass, typeParameterMap)));

        return res;
    }


    public static class AccessorInfo {

        public Optional<ExecutableElement> method;
        private String name;

        public AccessorInfo(Optional<ExecutableElement> method) {
            this.method = method;
        }

        public AccessorInfo(String name) {
            this.name = name;
            this.method = Optional.empty();
        }

        public String getName(){
            if(method.isPresent()){
                return method.get().getSimpleName().toString();
            }
            return name;
        }
    }

    /**
     * <p>isNotStatic</p>
     * <p>
     * Check if given field has static modifier
     *
     * @param field {@link javax.lang.model.element.Element} object
     * @return boolean true if the field is not static
     */
    protected boolean isNotStatic(Element field) {
        return !field.getModifiers().contains(Modifier.STATIC);
    }

    /**
     * <p>isIgnored</p>
     * <p>
     * Check if given field has been annotated with {@link JsonIgnore} present and its value is true
     *
     * @param field {@link javax.lang.model.element.Element} object
     * @return boolean true only if {@link JsonIgnore} present and its value is true
     */
    protected boolean isIgnored(Element field) {
        JsonIgnore annotation = field.getAnnotation(JsonIgnore.class);
        return nonNull(annotation) && annotation.value();
    }

    protected boolean isEligibleForSerializationDeserialization(Element field) {
        return isNotStatic(field) && !isIgnored(field);
    }

    public boolean isAbstract(TypeMirror beanType){
        return typeUtils.asElement(beanType).getModifiers().contains(Modifier.ABSTRACT);
    }

    protected abstract Class<?> getMapperType();

    /**
     * Build the code to initialize a {@link TypeSerializationInfo} or {@link TypeDeserializationInfo}.
     *
     * @return the code built
     */
    protected final CodeBlock generateTypeInfo() {
        final Class<?> type = getMapperType();
        CodeBlock.Builder builder = CodeBlock.builder()
                .add("new $T($T.$L, $S)", type, As.class, subTypesInfo.getInclude(), subTypesInfo.getPropertyName())
                .indent()
                .indent();

        for (Map.Entry<String, TypeMirror> entry : subTypesInfo.getSubTypes().entrySet()) {
            builder.add("\n.addTypeInfo($T.class, $S)", entry.getValue(), entry.getKey());
        }

        return builder.unindent().unindent().build();
    }

    private MethodSpec buildInitTypeInfoMethod() {
        final Class<?> type = getMapperType();

        return MethodSpec.methodBuilder("initTypeInfo")
                .addModifiers(Modifier.PROTECTED)
                .addAnnotation(Override.class)
                .returns(ParameterizedTypeName.get(ClassName.get(type), TypeName.get(beanType)))
                .addStatement("return $L", generateTypeInfo())
                .build();
    }
}
