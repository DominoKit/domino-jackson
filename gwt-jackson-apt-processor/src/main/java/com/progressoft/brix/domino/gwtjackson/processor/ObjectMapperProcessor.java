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

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic.Kind;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.progressoft.brix.domino.gwtjackson.AbstractObjectMapper;
import com.progressoft.brix.domino.gwtjackson.AbstractObjectReader;
import com.progressoft.brix.domino.gwtjackson.AbstractObjectWriter;
import com.progressoft.brix.domino.gwtjackson.JsonDeserializer;
import com.progressoft.brix.domino.gwtjackson.JsonSerializer;
import com.google.auto.common.MoreTypes;
import com.google.auto.service.AutoService;
import com.progressoft.brix.domino.gwtjackson.annotation.JSONMapper;
import com.progressoft.brix.domino.gwtjackson.annotation.JSONReader;
import com.progressoft.brix.domino.gwtjackson.annotation.JSONWriter;
import com.progressoft.brix.domino.gwtjackson.processor.deserialization.AptDeserializerBuilder;
import com.progressoft.brix.domino.gwtjackson.processor.serialization.AptSerializerBuilder;
import com.squareup.javapoet.*;

import static java.util.Objects.isNull;

@AutoService(Processor.class)
public class ObjectMapperProcessor extends AbstractProcessor {

    public static final WildcardTypeName DEFAULT_WILDCARD = WildcardTypeName.subtypeOf(Object.class);
    static Messager messager;
    public static Types typeUtils;
    static Elements elementUtils;

    private Filer filer;

    private Set<String> serializers = new HashSet<>();
    private Set<String> deserializers = new HashSet<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {

            Set<? extends Element> mappers = roundEnv.getElementsAnnotatedWith(JSONMapper.class);
            mappers.forEach(this::generateMappers);

            Set<? extends Element> readers = roundEnv.getElementsAnnotatedWith(JSONReader.class);
            readers.forEach(this::generateMapperForReader);

            Set<? extends Element> writers = roundEnv.getElementsAnnotatedWith(JSONWriter.class);
            writers.forEach(this::generateMapperForWriter);


        } catch (Exception e) {
            final StringWriter out = new StringWriter();
            e.printStackTrace(new PrintWriter(out));
            messager.printMessage(Kind.ERROR, "" + out.getBuffer().toString());
        }

        return false;
    }

    private void generateMappers(Element element) {
        try {
            String className = enclosingName(element, "_") + element.getSimpleName() + "Impl";
            String packageName = elementUtils.getPackageOf(element).getQualifiedName().toString();
            Name beanName = typeUtils.asElement(getBeanType(element)).getSimpleName();

            generateDeSerializer(element, packageName, beanName);
            generateSerializer(element, packageName, beanName);

            MethodSpec constructor = makeConstructor(beanName);
            MethodSpec newDeserializerMethod = makeNewDeserializerMethod(element, beanName);
            MethodSpec newSerializerMethod = makeNewSerializerMethod(beanName);

            TypeSpec classSpec = TypeSpec.classBuilder(className)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .superclass(abstractObjectMapper(element))
                    .addSuperinterface(TypeName.get(element.asType()))
                    .addMethod(constructor)
                    .addMethod(newDeserializerMethod)
                    .addMethod(newSerializerMethod)
                    .build();

            JavaFile.builder(packageName, classSpec).build().writeTo(filer);
        } catch (Exception e) {
            messager.printMessage(Kind.ERROR, "error while creating source file " + e, element);
        }
    }

    private void generateSerializer(Element element, String packageName, Name beanName) throws IOException {
        if (!serializers.contains(packageName + "." + beanName + AptSerializerBuilder.BEAN_JSON_SERIALIZER_IMPL)) {
            new AptSerializerBuilder(getBeanType(element), filer)
                    .generate(beanName, packageName);
            serializers.add(packageName + "." + beanName + AptSerializerBuilder.BEAN_JSON_SERIALIZER_IMPL);
        }
    }

    private void generateDeSerializer(Element element, String packageName, Name beanName) throws IOException {
        if (!deserializers.contains(packageName + "." + beanName + AptDeserializerBuilder.BEAN_JSON_DESERIALIZER_IMPL)) {
            new AptDeserializerBuilder( getBeanType(element), filer)
                    .generate(beanName, packageName);
            deserializers.add(packageName + "." + beanName + AptDeserializerBuilder.BEAN_JSON_DESERIALIZER_IMPL);
        }
    }

    private void generateMapperForReader(Element element) {
        try {
            String className = enclosingName(element, "_") + element.getSimpleName() + "Impl";
            String packageName = elementUtils.getPackageOf(element).getQualifiedName().toString();
            Name beanName = typeUtils.asElement(getBeanType(element)).getSimpleName();

            generateDeSerializer(element, packageName, beanName);

            MethodSpec constructor = makeConstructor(beanName);
            MethodSpec newDeserializer = makeNewDeserializerMethod(element, beanName);

            TypeSpec classSpec = TypeSpec.classBuilder(className)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .superclass(abstractObjectReader(element))
                    .addSuperinterface(TypeName.get(element.asType()))
                    .addMethod(constructor)
                    .addMethod(newDeserializer)
                    .build();

            JavaFile.builder(packageName, classSpec).build().writeTo(filer);
        } catch (IOException e) {
            messager.printMessage(Kind.ERROR, "error while creating source file " + e, element);
        }
    }

    private void generateMapperForWriter(Element element) {
        try {
            String className = enclosingName(element, "_") + element.getSimpleName() + "Impl";
            String packageName = elementUtils.getPackageOf(element).getQualifiedName().toString();
            Name beanName = typeUtils.asElement(getBeanType(element)).getSimpleName();

            generateSerializer(element, packageName, beanName);

            MethodSpec constructor = makeConstructor(beanName);
            MethodSpec newSerializer = makeNewSerializerMethod(beanName);

            TypeSpec classSpec = TypeSpec.classBuilder(className)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .superclass(abstractObjectWriter(element))
                    .addSuperinterface(TypeName.get(element.asType()))
                    .addMethod(constructor)
                    .addMethod(newSerializer)
                    .build();

            JavaFile.builder(packageName, classSpec).build().writeTo(filer);
        } catch (IOException e) {
            messager.printMessage(Kind.ERROR, "error while creating source file " + e, element);
        }
    }

    private MethodSpec makeNewDeserializerMethod(Element element, Name beanName) {
        return MethodSpec.methodBuilder("newDeserializer")
                .addModifiers(Modifier.PROTECTED)
                .addAnnotation(Override.class)
                .returns(ParameterizedTypeName.get(ClassName.get(JsonDeserializer.class),
                        ClassName.get(getBeanType(element))))
                .addStatement("return new " + beanName + "BeanJsonDeserializerImpl()")
                .build();
    }

    private MethodSpec makeNewSerializerMethod(Name beanName) {
        return MethodSpec.methodBuilder("newSerializer")
                .addModifiers(Modifier.PROTECTED)
                .addAnnotation(Override.class)
                .returns(ParameterizedTypeName.get(ClassName.get(JsonSerializer.class), DEFAULT_WILDCARD))
                .addStatement("return new " + beanName + "BeanJsonSerializerImpl()")
                .build();
    }

    private MethodSpec makeConstructor(Name beanName) {
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addStatement("super(\"" + beanName + "\")").build();
    }

    private TypeName abstractObjectMapper(Element element) {
        TypeMirror beanType = getBeanType(element);
        return ParameterizedTypeName.get(ClassName.get(AbstractObjectMapper.class),
                ClassName.get(beanType));
    }

    private TypeName abstractObjectReader(Element element) {
        TypeMirror beanType = getBeanType(element);
        return ParameterizedTypeName.get(ClassName.get(AbstractObjectReader.class),
                ClassName.get(beanType));
    }

    private TypeName abstractObjectWriter(Element element) {
        TypeMirror beanType = getBeanType(element);
        return ParameterizedTypeName.get(ClassName.get(AbstractObjectWriter.class),
                ClassName.get(beanType));
    }

    private TypeMirror getBeanType(Element element) {
        TypeMirror objectReader = ((TypeElement) typeUtils.asElement(element.asType())).getInterfaces().get(0);
        return MoreTypes.asDeclared(objectReader).getTypeArguments().get(0);
    }

    private String enclosingName(Element element, String postfix) {
        if (isNull(element.getEnclosingElement())) {
            return "";
        }
        return element.getEnclosingElement().getSimpleName().toString() + postfix;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Stream
                .of(JSONReader.class, JSONWriter.class, JSONMapper.class)
                .map(Class::getCanonicalName).collect(Collectors.toSet());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


}
