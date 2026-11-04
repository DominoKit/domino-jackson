/*
 * Copyright © 2019 Dominokit
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
package org.dominokit.jackson.processor.deserialization;

import static org.dominokit.jackson.processor.AbstractMapperProcessor.filer;
import static org.dominokit.jackson.processor.AbstractMapperProcessor.messager;
import static org.dominokit.jackson.processor.AbstractMapperProcessor.typeUtils;

import com.google.auto.common.MoreElements;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.util.List;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import org.dominokit.jackson.JsonDeserializer;
import org.dominokit.jackson.deser.collection.BaseCollectionJsonDeserializer;
import org.dominokit.jackson.deser.collection.BaseListJsonDeserializer;
import org.dominokit.jackson.processor.Type;
import org.dominokit.jackson.processor.TypeRegistry;

/**
 * Generates a deserializer for a {@link java.util.Collection} that is not already in the registry.
 * JSON stays an array. {@code newCollection} returns {@code new TheDeclaredClass()}. One class is
 * generated per raw type and is generic over that type's own parameters, so two fields of the same
 * subclass with different type arguments share it.
 */
public final class CollectionDeserializerGenerator {

  private CollectionDeserializerGenerator() {}

  static ClassName generate(TypeMirror typeMirror) {
    TypeElement element = rawElement(typeMirror);
    requireConstructable(element);
    if (!Type.isCollection(typeMirror)) {
      fail(element, "is not a Collection");
    }
    Class<?> base =
        Type.isAssignableFrom(typeMirror, List.class)
            ? BaseListJsonDeserializer.class
            : BaseCollectionJsonDeserializer.class;
    DeclaredContainer container = new DeclaredContainer(element);
    TypeName elementType = TypeName.get(Type.elementType(container.getOpen()));
    String packageName = MoreElements.getPackage(element).getQualifiedName().toString();
    String simpleName = element.getSimpleName() + "BeanJsonDeserializerImpl";
    ClassName generated = ClassName.get(packageName, simpleName);
    TypeName self = container.parameterize(generated);
    TypeSpec.Builder type =
        TypeSpec.classBuilder(simpleName)
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
            .superclass(
                ParameterizedTypeName.get(
                    ClassName.get(base), container.getDeclared(), elementType));
    container.addTypeVariables(type);
    type.addMethod(newInstance(self, container, elementType))
        .addMethod(constructor(elementType))
        .addMethod(newCollection(container.getDeclared()));
    write(element, packageName, type.build());
    TypeRegistry.registerCollectionDeserializer(typeMirror, generated);
    return generated;
  }

  private static MethodSpec newInstance(
      TypeName self, DeclaredContainer container, TypeName elementType) {
    MethodSpec.Builder method =
        MethodSpec.methodBuilder("newInstance")
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
            .returns(self);
    container.addTypeVariables(method);
    return method
        .addParameter(
            ParameterizedTypeName.get(ClassName.get(JsonDeserializer.class), elementType),
            "deserializer")
        .addStatement("return new $T(deserializer)", self)
        .build();
  }

  private static MethodSpec constructor(TypeName elementType) {
    return MethodSpec.constructorBuilder()
        .addModifiers(Modifier.PRIVATE)
        .addParameter(
            ParameterizedTypeName.get(ClassName.get(JsonDeserializer.class), elementType),
            "deserializer")
        .addStatement("super(deserializer)")
        .build();
  }

  private static MethodSpec newCollection(TypeName raw) {
    return MethodSpec.methodBuilder("newCollection")
        .addAnnotation(Override.class)
        .addModifiers(Modifier.PROTECTED)
        .returns(raw)
        .addStatement("return new $T()", raw)
        .build();
  }

  private static void write(TypeElement element, String packageName, TypeSpec type) {
    try {
      JavaFile.builder(packageName, type).build().writeTo(filer);
    } catch (IOException e) {
      throw new IllegalStateException(
          "Failed to generate deserializer for " + element.getQualifiedName(), e);
    }
  }

  private static void requireConstructable(TypeElement element) {
    if (element.getKind() == ElementKind.INTERFACE
        || element.getModifiers().contains(Modifier.ABSTRACT)) {
      fail(element, "is abstract");
    }
    if (!hasAccessibleNoArgConstructor(element)) {
      fail(element, "has no accessible no-arg constructor");
    }
  }

  private static boolean hasAccessibleNoArgConstructor(TypeElement element) {
    List<javax.lang.model.element.ExecutableElement> constructors =
        ElementFilter.constructorsIn(element.getEnclosedElements());
    if (constructors.isEmpty()) {
      return true;
    }
    return constructors.stream()
        .anyMatch(
            constructor ->
                constructor.getParameters().isEmpty()
                    && !constructor.getModifiers().contains(Modifier.PRIVATE));
  }

  private static TypeElement rawElement(TypeMirror typeMirror) {
    return (TypeElement) typeUtils.asElement(typeUtils.erasure(typeMirror));
  }

  private static void fail(TypeElement element, String reason) {
    String message =
        "Cannot generate a deserializer for " + element.getQualifiedName() + ": " + reason;
    messager.printMessage(Diagnostic.Kind.ERROR, message, element);
    throw new IllegalStateException(message);
  }
}
