package org.dominokit.jacksonapt.processor;

import com.google.auto.common.MoreTypes;
import com.squareup.javapoet.*;
import org.dominokit.jacksonapt.*;

import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import java.io.IOException;

import static java.util.Objects.isNull;
import static org.dominokit.jacksonapt.processor.ObjectMapperProcessor.*;

/**
 * <p>Abstract AbstractBeanMapperGenerator class.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public abstract class AbstractBeanMapperGenerator {

    void generate(Element element) throws IOException {
        String className = enclosingName(element, "_") + (useInterface(element) ? element.getSimpleName() : "Mapper") + "Impl";
        String packageName = elementUtils.getPackageOf(element).getQualifiedName().toString();
        TypeMirror beanType = getBeanType(element);
        Name beanName = typeUtils.asElement(beanType).getSimpleName();

        generateJsonMappers(beanType, packageName, beanName);

        TypeSpec.Builder builder = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .superclass(abstractObjectMapper(element))
                .addField(FieldSpec.builder(ClassName.bestGuess(className), "INSTANCE")
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                        .initializer(CodeBlock.builder().add("new $T()", ClassName.bestGuess(className)).build()).
                                build())
                .addMethod(makeConstructor(beanName))
                .addMethods(getMapperMethods(element, beanName));
        if (useInterface(element))
            builder.addSuperinterface(TypeName.get(element.asType()));

        TypeSpec classSpec = builder
                .build();

        JavaFile.builder(packageName, classSpec).build().writeTo(filer);
    }

    private String enclosingName(Element element, String postfix) {
        if (useInterface(element)) {
            return element.getEnclosingElement().getSimpleName().toString() + postfix;

        }
        return element.getSimpleName().toString() + postfix;

    }

    private boolean notEnclosed(Element element) {
        return ElementKind.PACKAGE.equals(element.getEnclosingElement()) || isNull(element.getEnclosingElement());
    }

    private TypeMirror getBeanType(Element element) {
        if (useInterface(element)) {
            TypeMirror objectReader = ((TypeElement) typeUtils.asElement(element.asType())).getInterfaces().get(0);
            return MoreTypes.asDeclared(objectReader).getTypeArguments().get(0);
        } else {
            return element.asType();
        }

    }

    private boolean useInterface(Element element) {
        return Type.isAssignableFrom(element.asType(), ObjectMapper.class) || Type.isAssignableFrom(element.asType(), ObjectReader.class) || Type.isAssignableFrom(element.asType(), ObjectWriter.class);
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

    /**
     * <p>getSuperClass.</p>
     *
     * @return a {@link java.lang.Class} object.
     */
    protected abstract Class<?> getSuperClass();

    /**
     * <p>getMapperMethods.</p>
     *
     * @param element  a {@link javax.lang.model.element.Element} object.
     * @param beanName a {@link javax.lang.model.element.Name} object.
     * @return a {@link java.lang.Iterable} object.
     */
    protected abstract Iterable<MethodSpec> getMapperMethods(Element element, Name beanName);

    /**
     * <p>generateJsonMappers.</p>
     *
     * @param beanType    a {@link javax.lang.model.type.TypeMirror} object.
     * @param packageName a {@link java.lang.String} object.
     * @param beanName    a {@link javax.lang.model.element.Name} object.
     */
    protected abstract void generateJsonMappers(TypeMirror beanType, String packageName, Name beanName);
}
