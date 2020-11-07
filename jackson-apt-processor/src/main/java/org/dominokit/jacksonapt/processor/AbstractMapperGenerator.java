package org.dominokit.jacksonapt.processor;

import static org.dominokit.jacksonapt.processor.AbstractMapperProcessor.filer;
import static org.dominokit.jacksonapt.processor.AbstractMapperProcessor.typeUtils;
import static org.dominokit.jacksonapt.processor.ObjectMapperProcessor.DEFAULT_WILDCARD;

import java.io.IOException;

import javax.lang.model.element.*;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import com.google.auto.common.MoreElements;
import org.dominokit.jacksonapt.JsonDeserializer;
import org.dominokit.jacksonapt.JsonSerializer;
import org.dominokit.jacksonapt.ObjectMapper;
import org.dominokit.jacksonapt.ObjectReader;
import org.dominokit.jacksonapt.ObjectWriter;
import org.dominokit.jacksonapt.processor.deserialization.FieldDeserializersChainBuilder;
import org.dominokit.jacksonapt.processor.serialization.FieldSerializerChainBuilder;

import com.google.auto.common.MoreTypes;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

public abstract class AbstractMapperGenerator implements MapperGenerator {

    private String packageName;

    @Override
    public void generate(Element element) throws IOException {
        String className = enclosingName(element, "_") + (useInterface(element) ? element.getSimpleName() : "Mapper") + "Impl";
        packageName = MoreElements.getPackage(element).getQualifiedName().toString();
        TypeMirror beanType = getElementType(element);
        Name beanName = typeUtils.asElement(beanType).getSimpleName();

        generateJsonMappers(beanType);

        TypeSpec.Builder builder = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .superclass(abstractObjectMapper(element))
                .addField(FieldSpec.builder(ClassName.bestGuess(className), "INSTANCE")
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                        .initializer(CodeBlock.builder().add("new $T()", ClassName.bestGuess(className)).build()).
                                build())
                .addMethod(makeConstructor(beanName))
                .addMethods(getMapperMethods(element, beanType));

        if (useInterface(element)) {
            builder.addSuperinterface(TypeName.get(element.asType()));
        }

        TypeSpec classSpec = builder
                .build();

        JavaFile.builder(packageName, classSpec).build().writeTo(filer);
    }

    protected static TypeMirror getElementType(Element element) {
        if (useInterface(element)) {
            TypeMirror objectReader = ((TypeElement) typeUtils.asElement(element.asType())).getInterfaces().get(0);
            return MoreTypes.asDeclared(objectReader).getTypeArguments().get(0);
        } else {
            return element.asType();
        }

    }

    protected static boolean useInterface(Element element) {
        return
                Type.isAssignableFrom(element.asType(), ObjectMapper.class)
                        || Type.isAssignableFrom(element.asType(), ObjectReader.class)
                        || Type.isAssignableFrom(element.asType(), ObjectWriter.class);
    }

    protected String enclosingName(Element element, String postfix) {
        Element enclosingElement = element.getEnclosingElement();
        if (useInterface(element) && !ElementKind.PACKAGE.equals(enclosingElement.getKind())) {
            return enclosingElement.getSimpleName().toString() + postfix;
        }else if(useInterface(element) && ElementKind.PACKAGE.equals(enclosingElement.getKind())){
            return "";
        }else{
            String prefix = element.getEnclosingElement().getKind().equals(ElementKind.PACKAGE) ? ""
                    : element.getEnclosingElement().getSimpleName().toString() + "_";
            return prefix + element.getSimpleName().toString() + postfix;
        }
    }

    protected TypeName abstractObjectMapper(Element element) {
        TypeMirror beanType = getElementType(element);
        return ParameterizedTypeName.get(ClassName.get(getSuperClass()),
                ClassName.get(beanType));
    }

    protected MethodSpec makeConstructor(Name beanName) {
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addStatement("super(\"" + beanName + "\")").build();
    }

    /**
     * <p>makeNewDeserializerMethod.</p>
     * <p>
     * Creates method for build corresponding deserializer for  given beanType. If beanType is
     * basic type, generated code utilize existing deserializers. Otherwise, it creates instances
     * of newly generated ones.
     *
     * @param element
     * @param beanType
     * @return
     */
    protected MethodSpec makeNewDeserializerMethod(Element element, TypeMirror beanType) {
        CodeBlock.Builder builder = CodeBlock.builder();
        if (Type.isBasicType(typeUtils.erasure(beanType))) {
            builder.addStatement("return $L", new FieldDeserializersChainBuilder(getElementType(element)).getInstance(getElementType(element)));
        } else {
            builder.addStatement("return new " + deserializerName(beanType));
        }

        return MethodSpec.methodBuilder("newDeserializer")
                .addModifiers(Modifier.PROTECTED)
                .addAnnotation(Override.class)
                .returns(ParameterizedTypeName.get(ClassName.get(JsonDeserializer.class),
                        ClassName.get(getElementType(element))))
                .addCode(builder.build())
                .build();

    }

    /**
     * <p>makeNewSerializerMethod.</p>
     * <p>
     * Creates method for build corresponding serializer for  given beanType. If beanType is
     * basic type, generated code utilize existing serializers. Otherwise, it creates instances
     * of newly generated ones.
     *
     * @param beanType
     * @return
     */
    protected MethodSpec makeNewSerializerMethod(TypeMirror beanType) {
        CodeBlock.Builder builder = CodeBlock.builder();
        if (Type.isBasicType(typeUtils.erasure(beanType))) {
            builder.addStatement("return $L", new FieldSerializerChainBuilder(beanType).getInstance(beanType));
        } else {
            builder.addStatement("return new " + serializerName(beanType));
        }

        return MethodSpec.methodBuilder("newSerializer")
                .addModifiers(Modifier.PROTECTED)
                .addAnnotation(Override.class)
                .returns(ParameterizedTypeName.get(ClassName.get(JsonSerializer.class), DEFAULT_WILDCARD))
                .addCode(builder.build())
                .build();
    }

    /**
     * Create deserializer name based on given TypeMirror.
     * <p>
     * The package, containing the deserializer is NOT returned as part of the result.
     *
     * @param type TypeMirror of the bean, deserializerr corresponds to
     * @return deserializer name as String
     */
    private String deserializerName(TypeMirror type) {
        return Type.stringifyType(type) + "BeanJsonDeserializerImpl()";
    }

    /**
     * Create serializer name based on given TypeMirror.
     * <p>
     * The package, containing the serializer is NOT returned as part of the result.
     *
     * @param type TypeMirror of the bean, serializerr corresponds to
     * @return serializer name as String
     */
    private String serializerName(TypeMirror type) {
        return Type.stringifyType(type) + "BeanJsonSerializerImpl()";
    }

    /**
     * <p>getSuperClass.</p>
     *
     * @return a {@link java.lang.Class} object.
     */
    protected abstract Class<?> getSuperClass();

    /**
     * <p>getMapperMethods.</p>
     *
     * @param element a {@link javax.lang.model.element.Element} object.
     * @param type    a {@link javax.lang.model.type.TypeMirror} object.
     * @return a {@link java.lang.Iterable} object.
     */
    protected abstract Iterable<MethodSpec> getMapperMethods(Element element, TypeMirror type);

    /**
     * <p>generateJsonMappers.</p>
     * <p>
     * Creates  mapper implementation for given beanType. If beanType is generic type, create
     * corresponding mappers for type arguments incl. their type arguments as well. All type
     * parameters will be included as part of the name of the mapper implementation.
     * <p>
     * Note that mappers are not generated for "simple" data types, since their implementation
     * already exist.
     *
     * @param beanType    a {@link javax.lang.model.type.TypeMirror} object.
     */

    private void generateJsonMappers(TypeMirror beanType) {
        // Root object for ObjectMapper can not have wildcards and/or type parameter.
        // TypeToken (and JsonRegistry) works only with declared types.
        if (Type.hasWildcards(beanType) || Type.hasTypeParameter(beanType)) {
            throw new IllegalArgumentException("Can not create mapper for type with wildcards or type parameters");
        }

        if (beanType.getKind() == TypeKind.DECLARED
                && !Type.isBasicType(typeUtils.erasure(beanType))) {
            generateDeserializer(beanType);
            generateSerializer(beanType);

        }
    }

    /**
     * Generate serializer for given beanType and packageName
     *
     * @param beanType
     */
    protected void generateSerializer(TypeMirror beanType) {
    }

    /**
     * Generate deserializer for given beanType and packageName
     *
     * @param beanType
     */
    protected void generateDeserializer(TypeMirror beanType) {
    }
}
