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

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleTypeVisitor6;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

/**
 * <p>Type class.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public class Type {

    private static final int FIRST_ARGUMENT = 0;
    private static final int SECOND_ARGUMENT = 1;
    /** Constant <code>BEAN_JSON_SERIALIZER_IMPL="BeanJsonSerializerImpl"</code> */
    public static final String BEAN_JSON_SERIALIZER_IMPL = "BeanJsonSerializerImpl";
    /** Constant <code>BEAN_JSON_DESERIALIZER_IMPL="BeanJsonDeserializerImpl"</code> */
    public static final String BEAN_JSON_DESERIALIZER_IMPL = "BeanJsonDeserializerImpl";
    /**
     * <p>wrapperType.</p>
     *
     * @param type a {@link javax.lang.model.type.TypeMirror} object.
     * @return a {@link com.squareup.javapoet.TypeName} object.
     */
    public static TypeName wrapperType(TypeMirror type) {
        if (isPrimitive(type)) {
            if ("boolean".equals(type.toString())) {
                return TypeName.get(Boolean.class);
            } else if ("byte".equals(type.toString())) {
                return TypeName.get(Byte.class);
            } else if ("short".equals(type.toString())) {
                return TypeName.get(Short.class);
            } else if ("int".equals(type.toString())) {
                return TypeName.get(Integer.class);
            } else if ("long".equals(type.toString())) {
                return TypeName.get(Long.class);
            } else if ("char".equals(type.toString())) {
                return TypeName.get(Character.class);
            } else if ("float".equals(type.toString())) {
                return TypeName.get(Float.class);
            } else if ("double".equals(type.toString())) {
                return TypeName.get(Double.class);
            } else {
                return TypeName.get(Void.class);
            }
        } else {
            return TypeName.get(type);
        }
    }

    private static boolean isPrimitive(TypeMirror typeMirror) {
        return typeMirror.getKind().isPrimitive();
    }

    /**
     * <p>isPrimitiveArray.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @return a boolean.
     */
    public static boolean isPrimitiveArray(TypeMirror typeMirror) {
        return (isArray(typeMirror) && isPrimitive(arrayComponentType(typeMirror))) || isPrimitive2dArray(typeMirror);
    }

    private static boolean isPrimitive2dArray(TypeMirror typeMirror) {
        return is2dArray(typeMirror) && isPrimitiveArray(arrayComponentType(typeMirror));
    }

    /**
     * <p>isArray.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @return a boolean.
     */
    public static boolean isArray(TypeMirror typeMirror) {
        return TypeKind.ARRAY.compareTo(typeMirror.getKind()) == 0;
    }

    /**
     * <p>is2dArray.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @return a boolean.
     */
    public static boolean is2dArray(TypeMirror typeMirror) {
        return isArray(typeMirror) && isArray(arrayComponentType(typeMirror));
    }

    /**
     * <p>arrayComponentType.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @return a {@link javax.lang.model.type.TypeMirror} object.
     */
    public static TypeMirror arrayComponentType(TypeMirror typeMirror) {
        return ((ArrayType) typeMirror).getComponentType();
    }

    /**
     * <p>deepArrayComponentType.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @return a {@link javax.lang.model.type.TypeMirror} object.
     */
    public static TypeMirror deepArrayComponentType(TypeMirror typeMirror) {
        TypeMirror type = ((ArrayType) typeMirror).getComponentType();
        return isArray(type) ? arrayComponentType(type) : type;
    }

    /**
     * <p>isEnum.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @return a boolean.
     */
    public static boolean isEnum(TypeMirror typeMirror) {
        return !isNull(ObjectMapperProcessor.typeUtils.asElement(typeMirror))
                && !Type.isPrimitive(typeMirror)
                && !Type.isPrimitiveArray(typeMirror)
                && ElementKind.ENUM.compareTo(ObjectMapperProcessor.typeUtils.asElement(typeMirror).getKind()) == 0;
    }

    /**
     * <p>isCollection.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @return a boolean.
     */
    public static boolean isCollection(TypeMirror typeMirror) {
        return !Type.isPrimitive(typeMirror) && isAssignableFrom(typeMirror, Collection.class);
    }

    /**
     * <p>isIterable.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @return a boolean.
     */
    public static boolean isIterable(TypeMirror typeMirror) {
        return !Type.isPrimitive(typeMirror) && isAssignableFrom(typeMirror, Iterable.class);
    }

    /**
     * <p>isAssignableFrom.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @param targetClass a {@link java.lang.Class} object.
     * @return a boolean.
     */
    public static boolean isAssignableFrom(TypeMirror typeMirror, Class<?> targetClass) {
        return ObjectMapperProcessor.typeUtils.isAssignable(typeMirror, ObjectMapperProcessor.typeUtils.getDeclaredType(ObjectMapperProcessor.elementUtils.getTypeElement(targetClass.getName())));
    }

    /**
     * <p>isMap.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @return a boolean.
     */
    public static boolean isMap(TypeMirror typeMirror) {
        return !Type.isPrimitive(typeMirror) && isAssignableFrom(typeMirror, Map.class);
    }

    /**
     * <p>firstTypeArgument.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @return a {@link javax.lang.model.type.TypeMirror} object.
     */
    public static TypeMirror firstTypeArgument(TypeMirror typeMirror) {
        return ((DeclaredType) typeMirror).getTypeArguments().get(FIRST_ARGUMENT);
    }

    /**
     * <p>secondTypeArgument.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @return a {@link javax.lang.model.type.TypeMirror} object.
     */
    public static TypeMirror secondTypeArgument(TypeMirror typeMirror) {
        return ((DeclaredType) typeMirror).getTypeArguments().get(SECOND_ARGUMENT);
    }

    /**
     * <p>isBasicType.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @return a boolean.
     */
    public static boolean isBasicType(TypeMirror typeMirror) {
        return TypeRegistry.isBasicType(typeMirror.toString());
    }

    /**
     * <p>getPackage.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @return a {@link java.lang.String} object.
     */
    public static String getPackage(TypeMirror typeMirror) {
        return ObjectMapperProcessor.elementUtils.getPackageOf(ObjectMapperProcessor.typeUtils.asElement(typeMirror)).getSimpleName().toString();
    }

    /**
     * <p>simpleName.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @return a {@link javax.lang.model.element.Name} object.
     */
    public static Name simpleName(TypeMirror typeMirror) {
        return ObjectMapperProcessor.typeUtils.asElement(typeMirror).getSimpleName();
    }

    /**
     * <p>serializerName.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @return a {@link java.lang.String} object.
     */
    public static String serializerName(TypeMirror typeMirror) {
        ClassName type = ClassName.bestGuess(typeMirror.toString());
        return serializerName(type.packageName(), typeMirror);
    }

    /**
     * <p>serializerName.</p>
     *
     * @param packageName a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String serializerName(String packageName, TypeMirror beanType) {
        return packageName + "." + stringifyType(beanType) + BEAN_JSON_SERIALIZER_IMPL;
    }

    /**
     * <p>deserializerName.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @return a {@link java.lang.String} object.
     */
    public static String deserializerName(TypeMirror typeMirror) {
        ClassName type = ClassName.bestGuess(typeMirror.toString());
        return deserializerName(type.packageName(), typeMirror);
    }
    
    /**
     * <p>deserializerName.</p>
     *
     * @param packageName a {@link java.lang.String} object.
     * @param beanType a {@link javax.lang.model.type.TypeMirror} object
     * @return a {@link java.lang.String} object.
     */
    public static String deserializerName(String packageName, TypeMirror beanType) {
        return packageName + "." + stringifyType(beanType) + BEAN_JSON_DESERIALIZER_IMPL;
    }
    
    /**
     * <p>stringifyType</p>
     * Stringify given TypeMirror including generic arguments
     *  
     * @param type a {@link javax.lang.model.type.TypeMirror} object
     * @return a {@link java.lang.String} containing string representation of given TypeMirror
     */
    public static String stringifyType(TypeMirror type) {
    	return type.accept(new SimpleTypeVisitor6<String, String>() {
			@Override
			public String visitDeclared(DeclaredType t, String p) {
				return 
					p 
					+ t.asElement().getSimpleName()
					+ ((!t.getTypeArguments().isEmpty())?
						"_" + t.getTypeArguments().stream().map(type -> visit(type, "")).collect(Collectors.joining("_"))
						: "");
			}

    	}, "");
    }
    /**
     * <p>generateDeserializer.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @return a {@link java.lang.String} object.
     */
    public static String generateDeserializer(TypeMirror typeMirror) {
        ClassName type = ClassName.bestGuess(typeMirror.toString());
        return new DeserializerGenerator().generate(typeMirror, type.packageName());
    }

    /**
     * <p>generateSerializer.</p>
     *
     * @param typeMirror a {@link javax.lang.model.type.TypeMirror} object.
     * @return a {@link java.lang.String} object.
     */
    public static String generateSerializer(TypeMirror typeMirror) {
        ClassName type = ClassName.bestGuess(typeMirror.toString());
        return new SerializerGenerator().generate(typeMirror, type.packageName());
    }
}
