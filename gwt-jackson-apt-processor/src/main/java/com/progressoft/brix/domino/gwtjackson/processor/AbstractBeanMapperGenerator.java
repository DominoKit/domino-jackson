package com.progressoft.brix.domino.gwtjackson.processor;

import com.google.auto.common.MoreTypes;
import com.progressoft.brix.domino.gwtjackson.JsonDeserializer;
import com.progressoft.brix.domino.gwtjackson.JsonSerializer;
import com.squareup.javapoet.*;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;

import static com.progressoft.brix.domino.gwtjackson.processor.ObjectMapperProcessor.*;
import static java.util.Objects.isNull;

public abstract class AbstractBeanMapperGenerator {

    void generate(Element element) throws IOException {
        String className = enclosingName(element, "_") + element.getSimpleName() + "Impl";
        String packageName = elementUtils.getPackageOf(element).getQualifiedName().toString();
        TypeMirror beanType = getBeanType(element);
        Name beanName = typeUtils.asElement(beanType).getSimpleName();

        generateJsonMappers(beanType, packageName, beanName);

        TypeSpec classSpec = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .superclass(abstractObjectMapper(element))
                .addSuperinterface(TypeName.get(element.asType()))
                .addMethod(makeConstructor(beanName))
                .addMethods(getMapperMethods(element, beanName))
                .build();

        JavaFile.builder(packageName, classSpec).build().writeTo(filer);
    }

    private String enclosingName(Element element, String postfix) {
        if (isNull(element.getEnclosingElement())) {
            return "";
        }
        return element.getEnclosingElement().getSimpleName().toString() + postfix;
    }

    private TypeMirror getBeanType(Element element) {
        TypeMirror objectReader = ((TypeElement) typeUtils.asElement(element.asType())).getInterfaces().get(0);
        return MoreTypes.asDeclared(objectReader).getTypeArguments().get(0);
    }

    private TypeName abstractObjectMapper(Element element) {
        TypeMirror beanType = getBeanType(element);
        return ParameterizedTypeName.get(ClassName.get(getSuperClass()),
                ClassName.get(beanType));
    }

    private MethodSpec makeConstructor(Name beanName) {
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addStatement("super(\"" + beanName + "\")").build();
    }

    MethodSpec makeNewDeserializerMethod(Element element, Name beanName) {
        return MethodSpec.methodBuilder("newDeserializer")
                .addModifiers(Modifier.PROTECTED)
                .addAnnotation(Override.class)
                .returns(ParameterizedTypeName.get(ClassName.get(JsonDeserializer.class),
                        ClassName.get(getBeanType(element))))
                .addStatement("return new " + beanName + "BeanJsonDeserializerImpl()")
                .build();
    }

    MethodSpec makeNewSerializerMethod(Name beanName) {
        return MethodSpec.methodBuilder("newSerializer")
                .addModifiers(Modifier.PROTECTED)
                .addAnnotation(Override.class)
                .returns(ParameterizedTypeName.get(ClassName.get(JsonSerializer.class), DEFAULT_WILDCARD))
                .addStatement("return new " + beanName + "BeanJsonSerializerImpl()")
                .build();
    }

    protected abstract Class<?> getSuperClass();

    protected abstract Iterable<MethodSpec> getMapperMethods(Element element, Name beanName);

    protected abstract void generateJsonMappers(TypeMirror beanType, String packageName, Name beanName);
}
