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
package org.dominokit.jacksonapt.processor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.squareup.javapoet.*;

import javax.annotation.processing.Filer;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import org.dominokit.jacksonapt.deser.bean.TypeDeserializationInfo;
import org.dominokit.jacksonapt.ser.bean.TypeSerializationInfo;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static org.dominokit.jacksonapt.processor.ObjectMapperProcessor.typeUtils;

/**
 * <p>Abstract AbstractJsonMapperGenerator class.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public abstract class AbstractJsonMapperGenerator {

    protected final TypeMirror beanType;

    private final Filer filer;

	protected SubTypesInfo subTypesInfo;

    /**
     * <p>Constructor for AbstractJsonMapperGenerator.</p>
     *
     * @param beanType a {@link javax.lang.model.type.TypeMirror} object.
     * @param filer    a {@link javax.annotation.processing.Filer} object.
     */
    public AbstractJsonMapperGenerator(TypeMirror beanType, SubTypesInfo subTypesInfo, Filer filer) {
        this.beanType = beanType;
        this.subTypesInfo = subTypesInfo;
        this.filer = filer;
    }

    /**
     * <p>generate.</p>
     *
     * @param packageName a {@link java.lang.String} object.
     * @throws java.io.IOException if any.
     */
    protected void generate(String packageName) throws IOException {
        MethodSpec constructor = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC).build();

        final TypeSpec.Builder builder = TypeSpec.classBuilder(Type.stringifyType(beanType) + namePostfix())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .superclass(superClass())
                .addMethod(constructor)
                .addMethod(targetTypeMethod());

        moreMethods().forEach(builder::addMethod);

        builder.addMethod(initMethod());
        if (subTypesInfo.hasSubTypes()) {
        	builder.addMethod(buildInitTypeInfoMethod());
        	builder.addMethod(initSubtypesMethod());
        }

        JavaFile.builder(packageName, builder.build()).build().writeTo(filer);
    }

    private MethodSpec targetTypeMethod() {
        return MethodSpec.methodBuilder(targetTypeMethodName())
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(ClassName.get(Class.class))
                .addStatement("return $T.class", TypeName.get(ObjectMapperProcessor.typeUtils.erasure(beanType)))
                .build();
    }

    /**
     * <p>superClass.</p>
     *
     * @return a {@link com.squareup.javapoet.TypeName} object.
     */
    protected abstract TypeName superClass();

    /**
     * <p>namePostfix.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    protected abstract String namePostfix();

    /**
     * <p>targetTypeMethodName.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    protected abstract String targetTypeMethodName();

    /**
     * <p>moreMethods.</p>
     *
     * @return a {@link java.util.Set} object.
     */
    protected Set<MethodSpec> moreMethods() {
        return Collections.emptySet();
    }

    /**
     * <p>initMethod.</p>
     *
     * @return a {@link com.squareup.javapoet.MethodSpec} object.
     */
    protected abstract MethodSpec initMethod();
    
    /**
     * <p>initMethod.</p>
     *
     * @return a {@link com.squareup.javapoet.MethodSpec} object.
     */
    protected abstract MethodSpec initSubtypesMethod();

    /**
     * <p>orderedFields.</p>
     *
     * @return a {@link java.util.List} object.
     */
    protected List<Element> orderedFields() {
        TypeElement typeElement = (TypeElement) typeUtils.asElement(beanType);
        
        List<? extends TypeMirror> typeArguments  = Collections.emptyList();
        List<? extends TypeParameterElement> typeParameters = Collections.emptyList();
        if (beanType instanceof DeclaredType) {
        	typeArguments = ((DeclaredType)beanType).getTypeArguments();
        	typeParameters = typeElement.getTypeParameters();
        }
        		
        final List<Element> fields = new ArrayList<>();

        List<Element> orderedFields = getOrderedFields(typeElement, typeParameters, typeArguments);
        fields.addAll(orderedFields);

        return fields;
    }

    private List<Element> getOrderedFields(
    		TypeElement typeElement,
    		List<? extends TypeParameterElement> typeParameters,
    		List<? extends TypeMirror> typeArguments) {

        TypeMirror superclass = typeElement.getSuperclass();
        if (superclass.getKind().equals(TypeKind.NONE)) {
            return new ArrayList<>();
        }

        final List<Element> orderedProperties = new ArrayList<>();

        final List<Element> enclosedFields = typeElement.getEnclosedElements().stream()
        	.filter(e -> ElementKind.FIELD.equals(e.getKind()) && isEligibleForSerializationDeserialization(e))
        	.collect(Collectors.toList());

        Optional.ofNullable(typeUtils.asElement(beanType).getAnnotation(JsonPropertyOrder.class))
                .ifPresent(jsonPropertyOrder -> {
                    final List<String> orderedFieldsNames = Arrays.asList(jsonPropertyOrder.value());
                    orderedProperties.addAll(enclosedFields.stream()
                            .filter(f -> orderedFieldsNames.contains(f.getSimpleName().toString()))
                            .collect(Collectors.toList()));

                    enclosedFields.removeAll(orderedProperties);
                    if (jsonPropertyOrder.alphabetic()) {
                        enclosedFields.sort(Comparator.comparing(f -> f.getSimpleName().toString()));
                    }

                    enclosedFields.addAll(0, orderedProperties);
                });

        // Map fields type parameters to type arguments by using wrapper element
        List<Element> orderedFields = enclosedFields
        	.stream()
        	.map(field -> 
        		typeParameters.contains(typeUtils.asElement(field.asType()))? 
        			wrapElementWithType(field, typeArguments.get(typeParameters.indexOf(typeUtils.asElement(field.asType())))):
        			field)
        	.collect(Collectors.toList());
        
        List<? extends TypeMirror> superTypeArguments  = Collections.emptyList();
        List<? extends TypeParameterElement> superTypeParameters = Collections.emptyList();
        if (superclass instanceof DeclaredType) {
        	superTypeArguments = ((DeclaredType)superclass).getTypeArguments();
        	superTypeParameters = ((TypeElement) typeUtils.asElement(superclass)).getTypeParameters();
        }
        
        // Map type arguments from base type to super type arguments
        superTypeArguments = superTypeArguments.stream()
        	.map(typeArgument -> 
    			typeParameters.contains(typeUtils.asElement(typeArgument))?
    				typeArguments.get(typeParameters.indexOf(typeUtils.asElement(typeArgument)))
    				:typeArgument)
        	.collect(Collectors.toList());
    					
        orderedFields.addAll(
        	getOrderedFields(
        		(TypeElement) typeUtils.asElement(superclass),
        		superTypeParameters,
        		superTypeArguments));
        
        return orderedFields;
    }
    
    private Element wrapElementWithType(Element element, TypeMirror typeMirror) {
    	return new Element() {

			@Override
			public <A extends Annotation> A[] getAnnotationsByType(Class<A> annotationType) {
				return element.getAnnotationsByType(annotationType);
			}

			@Override
			public TypeMirror asType() {
				return typeMirror;
			}

			@Override
			public ElementKind getKind() {
				return element.getKind();
			}

			@Override
			public Set<Modifier> getModifiers() {
				return element.getModifiers();
			}

			@Override
			public Name getSimpleName() {
				return element.getSimpleName();
			}

			@Override
			public Element getEnclosingElement() {
				return element.getEnclosingElement();
			}

			@Override
			public List<? extends Element> getEnclosedElements() {
				return element.getEnclosedElements();
			}

			@Override
			public List<? extends AnnotationMirror> getAnnotationMirrors() {
				return element.getAnnotationMirrors();
			}

			@Override
			public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
				return element.getAnnotation(annotationType);
			}

			@Override
			public <R, P> R accept(ElementVisitor<R, P> v, P p) {
				return element.accept(v, p);
			}
    	};
    }
    
    public static class AccessorInfo {

        public boolean present;
        public String accessor;

        public AccessorInfo(boolean present, String accessor) {
            this.present = present;
            this.accessor = accessor;
        }
    }

    /**
     *
     * @param field
     * @return boolean true if the field is not static
     */
    protected boolean isNotStatic(Element field) {
        return !field.getModifiers().contains(Modifier.STATIC);
    }

    /**
     *
     * @param field
     * @return boolean true only if {@link JsonIgnore} present and its value is true
     */
    protected boolean isIgnored(Element field) {
        JsonIgnore annotation = field.getAnnotation(JsonIgnore.class);
        return nonNull(annotation) && annotation.value();
    }

    protected boolean isEligibleForSerializationDeserialization(Element field){
        return isNotStatic(field) && !isIgnored(field);
    }
    
    protected abstract boolean isSerializer();
    
    /**
     * Build the code to initialize a {@link TypeSerializationInfo} or {@link TypeDeserializationInfo}.
     *
     * @param typeInfo the type information obtained through the {@link JsonTypeInfo} annotation
     * @return the code built
     */
    protected final CodeBlock generateTypeInfo() {
        final Class<?> type = isSerializer()? TypeSerializationInfo.class: TypeDeserializationInfo.class;
        CodeBlock.Builder builder = CodeBlock.builder()
                .add( "new $T($T.$L, $S)", type, As.class, subTypesInfo.getAs(), subTypesInfo.getPropertyName() )
                .indent()
                .indent();

        for ( Map.Entry<String, TypeMirror> entry : subTypesInfo.getSubTypes().entrySet() ) {
            builder.add( "\n.addTypeInfo($T.class, $S)",entry.getValue(), entry.getKey());
        }

        return builder.unindent().unindent().build();
    }
    
    private MethodSpec buildInitTypeInfoMethod() {
    	final Class<?> type = isSerializer()? TypeSerializationInfo.class: TypeDeserializationInfo.class;
        
    	return MethodSpec.methodBuilder( "initTypeInfo")
                .addModifiers( Modifier.PROTECTED )
                .addAnnotation( Override.class )
                .returns(ParameterizedTypeName.get(ClassName.get(type), TypeName.get(beanType)))
                .addStatement( "return $L", generateTypeInfo())
                .build();
    }
}
