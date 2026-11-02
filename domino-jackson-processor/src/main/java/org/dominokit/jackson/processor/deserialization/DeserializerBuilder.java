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

import static java.util.Objects.nonNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.squareup.javapoet.*;
import java.beans.Introspector;
import java.util.Optional;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import org.dominokit.jackson.JacksonContextProvider;
import org.dominokit.jackson.JsonDeserializationContext;
import org.dominokit.jackson.JsonDeserializer;
import org.dominokit.jackson.JsonDeserializerParameters;
import org.dominokit.jackson.deser.bean.BeanPropertyDeserializer;
import org.dominokit.jackson.processor.AbstractJsonMapperGenerator.AccessorInfo;
import org.dominokit.jackson.processor.AccessorsFilter;
import org.dominokit.jackson.processor.ObjectMapperProcessor;
import org.dominokit.jackson.processor.Type;

/** A class that generates a deserializer for a pojo. */
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

    TypeSpec.Builder builder =
        TypeSpec.anonymousClassBuilder("")
            .superclass(
                ParameterizedTypeName.get(
                    ClassName.get(BeanPropertyDeserializer.class),
                    TypeName.get(beanType),
                    Type.wrapperType(fieldType)));

    builder.addMethod(buildDeserializerMethod());

    if (nonNull(field.getAnnotation(JsonFormat.class))) {
      builder.addMethod(buildParametersMethod());
    }

    MethodSpec.Builder methodBuilder =
        MethodSpec.methodBuilder("setValue")
            .addModifiers(Modifier.PUBLIC)
            .addAnnotation(Override.class)
            .addParameter(ClassName.get(beanType), paramBean);

    AccessorInfo accessorInfo = setterInfo(field);

    methodBuilder
        .addParameter(Type.wrapperType(fieldType), paramValue)
        .addParameter(JsonDeserializationContext.class, "ctx")
        .addStatement(
            "$L",
            paramBean
                + "."
                + accessorInfo.getName()
                + (accessorInfo.method.isPresent() ? "(" : "=")
                + paramValue
                + (accessorInfo.method.isPresent() ? ")" : ""));

    builder.addMethod(methodBuilder.build());

    return builder.build();
  }

  private MethodSpec buildDeserializerMethod() {
    return MethodSpec.methodBuilder("newDeserializer")
        .addModifiers(Modifier.PROTECTED)
        .addAnnotation(Override.class)
        .returns(
            ParameterizedTypeName.get(
                ClassName.get(JsonDeserializer.class), ObjectMapperProcessor.DEFAULT_WILDCARD))
        .addStatement(
            "return $L", new FieldDeserializersChainBuilder(beanType).getInstance(fieldType))
        .build();
  }

  private MethodSpec buildParametersMethod() {
    JsonFormat jsonFormat = field.getAnnotation(JsonFormat.class);
    return MethodSpec.methodBuilder("newParameters")
        .addModifiers(Modifier.PROTECTED)
        .addAnnotation(Override.class)
        .returns(JsonDeserializerParameters.class)
        .addStatement(
            "return $T.get()\n\t\t.newDeserializerParameters()\n\t\t.setPattern($S)\n\t\t.setShape($T.$L)",
            TypeName.get(JacksonContextProvider.class),
            jsonFormat.pattern(),
            TypeName.get(JsonFormat.Shape.class),
            jsonFormat.shape().toString())
        .build();
  }

  private AccessorInfo setterInfo(Element field) {
    String fieldName = field.getSimpleName().toString();
    // Records are skipped: initDeserializers does not call this method for them, so the record
    // accessor lookup that used to live here was unused.

    // @JsonPOJOBuilder(withPrefix) names the builder method. An empty prefix means the method is
    // the field name (id(String)). Any other prefix is stripped and decapitalized (withId).
    return Optional.ofNullable(typeUtils.asElement(beanType).getAnnotation(JsonPOJOBuilder.class))
        .flatMap(annotation -> findAccessor(annotation.withPrefix(), fieldName))
        // Compatibility with builders written before withPrefix was honored. The annotation
        // defaults withPrefix to "with", while those methods were matched as set*.
        .or(() -> findAccessor("set", fieldName))
        .orElseGet(() -> new AccessorInfo(fieldName));
  }

  private Optional<AccessorInfo> findAccessor(String prefix, String fieldName) {
    return getAccessors(beanType).stream()
        .filter(
            accessorInfo ->
                accessorInfo.method.isPresent()
                    && accessorInfo.method.get().getParameters().size() == 1)
        .filter(accessorInfo -> accessorInfo.getName().startsWith(prefix))
        .filter(
            accessorInfo ->
                Introspector.decapitalize(accessorInfo.getName().substring(prefix.length()))
                    .equals(fieldName))
        .findFirst();
  }
}
