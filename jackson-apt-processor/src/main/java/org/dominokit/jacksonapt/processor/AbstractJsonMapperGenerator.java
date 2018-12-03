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
import com.squareup.javapoet.*;

import javax.annotation.processing.Filer;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

    private final Filer filer;

    /**
     * <p>Constructor for AbstractJsonMapperGenerator.</p>
     *
     * @param beanType a {@link javax.lang.model.type.TypeMirror} object.
     * @param filer    a {@link javax.annotation.processing.Filer} object.
     */
    public AbstractJsonMapperGenerator(TypeMirror beanType, Filer filer) {
        this.beanType = beanType;
        this.filer = filer;
    }

    /**
     * <p>generate.</p>
     *
     * @param beanName    a {@link javax.lang.model.element.Name} object.
     * @param packageName a {@link java.lang.String} object.
     * @throws java.io.IOException if any.
     */
    protected void generate(Name beanName, String packageName) throws IOException {
        MethodSpec constructor = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC).build();

        final TypeSpec.Builder builder = TypeSpec.classBuilder(beanName + namePostfix())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .superclass(superClass())
                .addMethod(constructor)
                .addMethod(targetTypeMethod());

        moreMethods().forEach(builder::addMethod);

        builder.addMethod(initMethod());

        JavaFile.builder(packageName, builder.build()).build().writeTo(filer);
    }

    private MethodSpec targetTypeMethod() {
        return MethodSpec.methodBuilder(targetTypeMethodName())
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(ClassName.get(Class.class))
                .addStatement("return $T.class", TypeName.get(beanType))
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
    protected abstract MethodSpec initMethod();

    /**
     * <p>orderedFields.</p>
     *
     * @return a {@link java.util.List} object.
     */
    protected List<Element> orderedFields() {
        TypeElement typeElement = (TypeElement) typeUtils.asElement(beanType);

        final List<Element> fields = new ArrayList<>();

        List<Element> orderedFields = getOrderedFields(typeElement);
        fields.addAll(orderedFields);


        return fields;
    }

    private List<Element> getOrderedFields(TypeElement typeElement) {

        TypeMirror superclass = typeElement.getSuperclass();
        if (superclass.getKind().equals(TypeKind.NONE)) {
            return new ArrayList<>();
        }

        final List<Element> orderedProperties = new ArrayList<>();

        final List<Element> enclosedFields = typeElement.getEnclosedElements().stream().filter(e -> ElementKind.FIELD
                .equals(e.getKind()) && isEligibleForSerializationDeserialization(e)).collect(Collectors.toList());

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

        enclosedFields.addAll(getOrderedFields((TypeElement) typeUtils.asElement(superclass)));
        return enclosedFields;
    }

    public static class AccessorInfo {

        public boolean present;
        public String accessor;

        public AccessorInfo(boolean present, String accessor) {
            this.present = present;
            this.accessor = accessor;
        }
    }

    /**
     *
     * @param field
     * @return boolean true if the field is not static
     */
    protected boolean isNotStatic(Element field) {
        return !field.getModifiers().contains(Modifier.STATIC);
    }

    /**
     *
     * @param field
     * @return boolean true only if {@link JsonIgnore} present and its value is true
     */
    protected boolean isIgnored(Element field) {
        JsonIgnore annotation = field.getAnnotation(JsonIgnore.class);
        return nonNull(annotation) && annotation.value();
    }

    protected boolean isEligibleForSerializationDeserialization(Element field){
        return isNotStatic(field) && !isIgnored(field);
    }
}
