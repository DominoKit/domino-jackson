package org.dominokit.jacksonapt.processor;

import static org.dominokit.jacksonapt.processor.AbstractMapperProcessor.elementUtils;
import static org.dominokit.jacksonapt.processor.AbstractMapperProcessor.filer;
import static org.dominokit.jacksonapt.processor.AbstractMapperProcessor.typeUtils;

import java.io.IOException;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import org.dominokit.jacksonapt.ObjectMapper;
import org.dominokit.jacksonapt.ObjectReader;
import org.dominokit.jacksonapt.ObjectWriter;

import com.google.auto.common.MoreTypes;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

public abstract class AbstractMapperGenerator implements MapperGenerator {

	@Override
	public void generate(Element element) throws IOException {
		String className = getBeanClassName(element);
		String packageName = elementUtils.getPackageOf(element).getQualifiedName().toString();
		TypeMirror beanType = getElementType(element);
		Name beanName = typeUtils.asElement(beanType).getSimpleName();

		generateJsonMappers(beanType, packageName, beanName);

		TypeSpec.Builder builder = TypeSpec.classBuilder(className)
				.addModifiers(Modifier.PUBLIC, Modifier.FINAL)
				.superclass(abstractObjectMapper(element))
				.addMethod(makeConstructor(beanName))
				.addMethods(getMapperMethods(element, beanName));
		if(useInterface(element))
			builder.addSuperinterface(TypeName.get(element.asType()));

		TypeSpec classSpec = builder
				.build();

		JavaFile.builder(packageName, classSpec).build().writeTo(filer);
	}

	protected String getBeanClassName(Element element) {
		return enclosingName(element, "_") + (useInterface(element)?element.getSimpleName():"Mapper") + "Impl";
	}
	
	protected static TypeMirror getElementType(Element element) {
		if(useInterface(element)){
			TypeMirror objectReader = ((TypeElement) typeUtils.asElement(element.asType())).getInterfaces().get(0);
			return MoreTypes.asDeclared(objectReader).getTypeArguments().get(0);
		}else{
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
		if (useInterface(element)) {
			return element.getEnclosingElement().getSimpleName().toString() + postfix;

		}
		return element.getSimpleName().toString() + postfix;

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
	 * <p>getSuperClass.</p>
	 *
	 * @return a {@link java.lang.Class} object.
	 */
	protected abstract Class<?> getSuperClass();

	/**
	 * <p>getMapperMethods.</p>
	 *
	 * @param element a {@link javax.lang.model.element.Element} object.
	 * @param beanName a {@link javax.lang.model.element.Name} object.
	 * @return a {@link java.lang.Iterable} object.
	 */
	protected abstract Iterable<MethodSpec> getMapperMethods(Element element, Name beanName);
	
	 /**
     * <p>generateJsonMappers.</p>
     *
     * @param beanType a {@link javax.lang.model.type.TypeMirror} object.
     * @param packageName a {@link java.lang.String} object.
     * @param beanName a {@link javax.lang.model.element.Name} object.
     */
    protected abstract void generateJsonMappers(TypeMirror beanType, String packageName, Name beanName);

}
