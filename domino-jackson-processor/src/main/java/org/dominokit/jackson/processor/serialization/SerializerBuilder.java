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
package org.dominokit.jackson.processor.serialization;

import static java.util.Objects.nonNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.squareup.javapoet.*;
import java.beans.Introspector;
import java.util.Optional;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import org.dominokit.jackson.JacksonContextProvider;
import org.dominokit.jackson.JsonSerializationContext;
import org.dominokit.jackson.JsonSerializer;
import org.dominokit.jackson.JsonSerializerParameters;
import org.dominokit.jackson.processor.AbstractJsonMapperGenerator;
import org.dominokit.jackson.processor.AccessorsFilter;
import org.dominokit.jackson.processor.ObjectMapperProcessor;
import org.dominokit.jackson.processor.Type;
import org.dominokit.jackson.ser.bean.BeanPropertySerializer;
import org.dominokit.jackson.stream.impl.DefaultJsonWriter;

/** A class that generates a deserializer for a pojo. */
class SerializerBuilder extends AccessorsFilter {

  private final TypeMirror beanType;
  private final Element field;
  private final TypeMirror fieldType;

  SerializerBuilder(Types typeUtils, TypeMirror beanType, Element field, TypeMirror fieldType) {
    super(typeUtils);
    this.beanType = beanType;
    this.field = field;
    this.fieldType = fieldType;
  }

  TypeSpec buildSerializer() {
    final String paramBean = "bean";

    TypeSpec.Builder builder =
        TypeSpec.anonymousClassBuilder(
                "\"$L\"", DefaultJsonWriter.encodeString(getPropertyName(field)))
            .superclass(
                ParameterizedTypeName.get(
                    ClassName.get(BeanPropertySerializer.class),
                    TypeName.get(beanType),
                    Type.wrapperType(fieldType)));

    builder.addMethod(buildSerializerMethod());

    if (shouldAddParametersMethod()) {
      builder.addMethod(buildParametersMethod());
    }

    MethodSpec.Builder methodBuilder =
        MethodSpec.methodBuilder("getValue")
            .returns(Type.wrapperType(fieldType))
            .addModifiers(Modifier.PUBLIC)
            .addAnnotation(Override.class)
            .addParameter(ClassName.get(beanType), paramBean);
    AbstractJsonMapperGenerator.AccessorInfo accessorInfo = getterInfo();

    methodBuilder
        .addParameter(JsonSerializationContext.class, "ctx")
        .addStatement(
            "return $L",
            paramBean
                + "."
                + accessorInfo.getName()
                + (accessorInfo.method.isPresent() ? "()" : ""));

    builder.addMethod(methodBuilder.build());

    return builder.build();
  }

  private boolean shouldAddParametersMethod() {
    return nonNull(field.getAnnotation(JsonFormat.class))
        || nonNull(field.getAnnotation(JsonInclude.class))
        || hasTypeJsonInclude();
  }

  private MethodSpec buildSerializerMethod() {
    return MethodSpec.methodBuilder("newSerializer")
        .addModifiers(Modifier.PROTECTED)
        .addAnnotation(Override.class)
        .returns(
            ParameterizedTypeName.get(
                ClassName.get(JsonSerializer.class), ObjectMapperProcessor.DEFAULT_WILDCARD))
        .addStatement("return $L", new FieldSerializerChainBuilder(beanType).getInstance(fieldType))
        .build();
  }

  private MethodSpec buildParametersMethod() {
    JsonFormat jsonFormat = field.getAnnotation(JsonFormat.class);
    JsonInclude jsonInclude = getJsonInclude();
    MethodSpec.Builder methodBuilder =
        MethodSpec.methodBuilder("newParameters")
            .addModifiers(Modifier.PROTECTED)
            .addAnnotation(Override.class)
            .returns(JsonSerializerParameters.class)
            .addStatement(
                "$T parameters = $T.get().newSerializerParameters()",
                TypeName.get(JsonSerializerParameters.class),
                TypeName.get(JacksonContextProvider.class));
    if (nonNull(jsonFormat)) {
      methodBuilder.addStatement(
          "parameters.setPattern($S).setShape($T.$L)",
          jsonFormat.pattern(),
          TypeName.get(JsonFormat.Shape.class),
          jsonFormat.shape().toString());
    }
    if (nonNull(jsonInclude)) {
      methodBuilder.addStatement(
          "parameters.setInclude($T.$L)",
          TypeName.get(JsonInclude.Include.class),
          jsonInclude.value().toString());
    }
    return methodBuilder.addStatement("return parameters").build();
  }

  private JsonInclude getJsonInclude() {
    if (nonNull(field.getAnnotation(JsonInclude.class))) {
      return field.getAnnotation(JsonInclude.class);
    }
    return beanType.getKind() == TypeKind.DECLARED
        ? getJsonInclude(((DeclaredType) beanType))
        : null;
  }

  private JsonInclude getJsonInclude(DeclaredType enclosingType) {
    TypeElement enclosingElement = ((TypeElement) enclosingType.asElement());
    return enclosingElement.getAnnotation(JsonInclude.class);
  }

  private boolean hasTypeJsonInclude() {
    if (beanType.getKind() == TypeKind.DECLARED) {
      return ((DeclaredType) beanType).asElement().getAnnotation(JsonInclude.class) != null;
    }
    return false;
  }

  AbstractJsonMapperGenerator.AccessorInfo getterInfo() {
    Optional<AbstractJsonMapperGenerator.AccessorInfo> accessor;
    if (Type.isRecord(beanType)) {
      accessor =
          getAccessors(beanType).stream()
              .filter(
                  accessorInfo -> accessorInfo.getName().equals(field.getSimpleName().toString()))
              .findFirst();
    } else {
      String prefix = field.asType().getKind() == TypeKind.BOOLEAN ? "is" : "get";
      accessor =
          getAccessors(beanType).stream()
              .filter(
                  accessorInfo ->
                      accessorInfo.getName().startsWith("is")
                          || accessorInfo.getName().startsWith("get"))
              .filter(
                  accessorInfo ->
                      Introspector.decapitalize(accessorInfo.getName().substring(prefix.length()))
                          .equals(field.getSimpleName().toString()))
              .findFirst();
    }

    return accessor.orElseGet(
        () -> new AbstractJsonMapperGenerator.AccessorInfo(field.getSimpleName().toString()));
  }
}
