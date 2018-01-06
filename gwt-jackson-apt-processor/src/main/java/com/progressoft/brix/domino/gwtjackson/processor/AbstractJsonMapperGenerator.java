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
package com.progressoft.brix.domino.gwtjackson.processor;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.squareup.javapoet.*;

import javax.annotation.processing.Filer;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.progressoft.brix.domino.gwtjackson.processor.ObjectMapperProcessor.typeUtils;

public abstract class AbstractJsonMapperGenerator {

    protected final TypeMirror beanType;

    private final Filer filer;

    public AbstractJsonMapperGenerator(TypeMirror beanType, Filer filer) {
        this.beanType = beanType;
        this.filer = filer;
    }

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

    protected abstract TypeName superClass();

    protected abstract String namePostfix();

    protected abstract String targetTypeMethodName();

    protected Set<MethodSpec> moreMethods() {
        return Collections.emptySet();
    }

    protected abstract MethodSpec initMethod();

    protected List<Element> orderedFields() {
        TypeElement typeElement = (TypeElement) typeUtils.asElement(beanType);

        final List<Element> orderedProperties = new ArrayList<>();

        final List<Element> fields = typeElement.getEnclosedElements().stream().filter(e -> ElementKind.FIELD
                .equals(e.getKind()) && !e.getModifiers()
                .contains(Modifier.STATIC)).collect(Collectors.toList());

        Optional.ofNullable(typeUtils.asElement(beanType).getAnnotation(JsonPropertyOrder.class))
                .ifPresent(jsonPropertyOrder -> {
                    final List<String> orderedFieldsNames = Arrays.asList(jsonPropertyOrder.value());
                    orderedProperties.addAll(fields.stream()
                            .filter(f -> orderedFieldsNames.contains(f.getSimpleName().toString()))
                            .collect(Collectors.toList()));

                    fields.removeAll(orderedProperties);
                    if (jsonPropertyOrder.alphabetic()) {
                        fields.sort(Comparator.comparing(f -> f.getSimpleName().toString()));
                    }

                    fields.addAll(0, orderedProperties);
                });

        return fields;
    }

    public static class AccessorInfo {

        public boolean present;
        public String accessor;

        public AccessorInfo(boolean present, String accessor) {
            this.present = present;
            this.accessor = accessor;
        }
    }

    protected boolean isNotStatic(Element field) {
        return !field.getModifiers().contains(Modifier.STATIC);
    }
}
