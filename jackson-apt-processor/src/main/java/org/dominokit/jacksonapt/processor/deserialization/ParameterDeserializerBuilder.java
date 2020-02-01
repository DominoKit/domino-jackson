package org.dominokit.jacksonapt.processor.deserialization;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.squareup.javapoet.*;
import org.dominokit.jacksonapt.JacksonContextProvider;
import org.dominokit.jacksonapt.JsonDeserializer;
import org.dominokit.jacksonapt.JsonDeserializerParameters;
import org.dominokit.jacksonapt.deser.bean.HasDeserializerAndParameters;
import org.dominokit.jacksonapt.processor.Type;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

import static java.util.Objects.nonNull;

public class ParameterDeserializerBuilder {

    private Types typeUtils;
    private TypeMirror type;
    private VariableElement parameter;
    private String packageName;

    public ParameterDeserializerBuilder(Types typeUtils, TypeMirror type, VariableElement parameter, String packageName) {
        this.typeUtils = typeUtils;
        this.type = type;
        this.parameter = parameter;
        this.packageName = packageName;
    }

    CodeBlock build() {
        TypeName typeName = Type.wrapperType(parameter.asType());

        MethodSpec build = MethodSpec.methodBuilder("newDeserializer")
                .addModifiers(Modifier.PROTECTED)
                .returns(ParameterizedTypeName.get(JsonDeserializer.class))
                .addAnnotation(Override.class)
                .addStatement("return $L", new FieldDeserializersChainBuilder(packageName, parameter.asType()).getInstance(parameter))
                .build();

        ParameterizedTypeName deserializerType = ParameterizedTypeName.get(ClassName.get(HasDeserializerAndParameters.class), typeName, ParameterizedTypeName
                .get(ClassName.get(JsonDeserializer.class), typeName));
        TypeSpec.Builder typeBuilder = TypeSpec.anonymousClassBuilder("")
                .addSuperinterface(deserializerType)
                .addMethod(build);

        typeUtils.asElement(type)
                .getEnclosedElements()
                .stream()
                .filter(o -> o.getKind().isField())
                .filter(o -> o.getSimpleName().toString().equals(getParameterName()))
                .filter(o -> nonNull(o.getAnnotation(JsonFormat.class)))
                .findFirst()
                .ifPresent(o -> {
                    typeBuilder.addMethod(buildParametersMethod(o));
                });

        return CodeBlock.builder()
                .addStatement("final $T $L = $L", deserializerType, getDeserializerName(), typeBuilder.build())
                .build();
    }

    private MethodSpec buildParametersMethod(Element element) {
        JsonFormat jsonFormat = element.getAnnotation(JsonFormat.class);
        return MethodSpec.methodBuilder("newParameters")
                .addModifiers(Modifier.PROTECTED)
                .addAnnotation(Override.class)
                .returns(JsonDeserializerParameters.class)
                .addStatement("return $T.get()\n\t\t.newDeserializerParameters()\n\t\t.setPattern($S)\n\t\t.setShape($T.$L)"
                        , TypeName.get(JacksonContextProvider.class)
                        , jsonFormat.pattern()
                        , TypeName.get(JsonFormat.Shape.class)
                        , jsonFormat.shape().toString())
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
