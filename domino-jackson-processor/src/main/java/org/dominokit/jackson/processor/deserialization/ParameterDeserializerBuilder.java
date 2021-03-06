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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.squareup.javapoet.*;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import org.dominokit.jackson.JacksonContextProvider;
import org.dominokit.jackson.JsonDeserializer;
import org.dominokit.jackson.JsonDeserializerParameters;
import org.dominokit.jackson.deser.bean.HasDeserializerAndParameters;
import org.dominokit.jackson.processor.Type;

/**
 * this class is used with {@link com.fasterxml.jackson.annotation.JsonCreator} to generate the
 * deserializer based on creator parameters.
 */
public class ParameterDeserializerBuilder {

  private Types typeUtils;
  private TypeMirror type;
  private VariableElement parameter;

  public ParameterDeserializerBuilder(Types typeUtils, TypeMirror type, VariableElement parameter) {
    this.typeUtils = typeUtils;
    this.type = type;
    this.parameter = parameter;
  }

  CodeBlock build() {
    TypeName typeName = Type.wrapperType(parameter.asType());

    MethodSpec build =
        MethodSpec.methodBuilder("newDeserializer")
            .addModifiers(Modifier.PROTECTED)
            .returns(ParameterizedTypeName.get(JsonDeserializer.class))
            .addAnnotation(Override.class)
            .addStatement(
                "return $L",
                new FieldDeserializersChainBuilder(parameter.asType()).getInstance(parameter))
            .build();

    ParameterizedTypeName deserializerType =
        ParameterizedTypeName.get(
            ClassName.get(HasDeserializerAndParameters.class),
            typeName,
            ParameterizedTypeName.get(ClassName.get(JsonDeserializer.class), typeName));
    TypeSpec.Builder typeBuilder =
        TypeSpec.anonymousClassBuilder("").addSuperinterface(deserializerType).addMethod(build);

    typeUtils.asElement(type).getEnclosedElements().stream()
        .filter(o -> o.getKind().isField())
        .filter(o -> o.getSimpleName().toString().equals(getParameterName()))
        .filter(o -> nonNull(o.getAnnotation(JsonFormat.class)))
        .findFirst()
        .ifPresent(
            o -> {
              typeBuilder.addMethod(buildParametersMethod(o));
            });

    return CodeBlock.builder()
        .addStatement(
            "final $T $L = $L", deserializerType, getDeserializerName(), typeBuilder.build())
        .build();
  }

  private MethodSpec buildParametersMethod(Element element) {
    JsonFormat jsonFormat = element.getAnnotation(JsonFormat.class);
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

  public String getParameterName() {
    String parameterName = parameter.getSimpleName().toString();
    JsonProperty jsonProperty = parameter.getAnnotation(JsonProperty.class);
    if (nonNull(jsonProperty)) {
      String value = jsonProperty.value();
      parameterName = value.isEmpty() ? parameterName : value;
    }
    return parameterName;
  }

  public String getDeserializerName() {
    return getParameterName() + "Deserializer";
  }

  public TypeMirror getParameterType() {
    return parameter.asType();
  }
}
