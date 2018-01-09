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
package com.progressoft.brix.domino.jacksonapt.processor;

import com.progressoft.brix.domino.jacksonapt.processor.serialization.AptSerializerBuilder;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import jsinterop.annotations.JsType;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.util.Collection;
import java.util.Map;

import static com.progressoft.brix.domino.jacksonapt.processor.ObjectMapperProcessor.elementUtils;
import static com.progressoft.brix.domino.jacksonapt.processor.ObjectMapperProcessor.messager;
import static com.progressoft.brix.domino.jacksonapt.processor.ObjectMapperProcessor.typeUtils;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Type {

    private static final int FIRST_ARGUMENT = 0;
    private static final int SECOND_ARGUMENT = 1;
    public static final String BEAN_JSON_SERIALIZER_IMPL = "BeanJsonSerializerImpl";
    public static final String BEAN_JSON_DESERIALIZER_IMPL = "BeanJsonDeserializerImpl";

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

    public static boolean isPrimitiveArray(TypeMirror typeMirror) {
        return (isArray(typeMirror) && isPrimitive(arrayComponentType(typeMirror))) || isPrimitive2dArray(typeMirror);
    }

    private static boolean isPrimitive2dArray(TypeMirror typeMirror) {
        return is2dArray(typeMirror) && isPrimitiveArray(arrayComponentType(typeMirror));
    }

    public static boolean isArray(TypeMirror typeMirror) {
        return TypeKind.ARRAY.compareTo(typeMirror.getKind()) == 0;
    }

    public static boolean is2dArray(TypeMirror typeMirror) {
        return isArray(typeMirror) && isArray(arrayComponentType(typeMirror));
    }

    public static TypeMirror arrayComponentType(TypeMirror typeMirror) {
        return ((ArrayType) typeMirror).getComponentType();
    }

    public static TypeMirror deepArrayComponentType(TypeMirror typeMirror) {
        TypeMirror type = ((ArrayType) typeMirror).getComponentType();
        return isArray(type) ? arrayComponentType(type) : type;
    }

    public static boolean isEnum(TypeMirror typeMirror) {
        return !isNull(typeUtils.asElement(typeMirror))
                && !Type.isPrimitive(typeMirror)
                && !Type.isPrimitiveArray(typeMirror)
                && ElementKind.ENUM.compareTo(typeUtils.asElement(typeMirror).getKind()) == 0;
    }

    public static boolean isCollection(TypeMirror typeMirror) {
        return !Type.isPrimitive(typeMirror) && isAssignableFrom(typeMirror, Collection.class);
    }

    public static boolean isIterable(TypeMirror typeMirror) {
        return !Type.isPrimitive(typeMirror) && isAssignableFrom(typeMirror, Iterable.class);
    }

    public static boolean isAssignableFrom(TypeMirror typeMirror, Class<?> targetClass) {
        return typeUtils.isAssignable(typeMirror, typeUtils.getDeclaredType(elementUtils.getTypeElement(targetClass.getName())));
    }

    public static boolean isMap(TypeMirror typeMirror) {
        return !Type.isPrimitive(typeMirror) && isAssignableFrom(typeMirror, Map.class);
    }

    public static TypeMirror firstTypeArgument(TypeMirror typeMirror) {
        return ((DeclaredType) typeMirror).getTypeArguments().get(FIRST_ARGUMENT);
    }

    public static TypeMirror secondTypeArgument(TypeMirror typeMirror) {
        return ((DeclaredType) typeMirror).getTypeArguments().get(SECOND_ARGUMENT);
    }

    public static boolean isBasicType(TypeMirror typeMirror) {
        return TypeRegistry.isBasicType(typeMirror.toString());
    }

    public static String getPackage(TypeMirror typeMirror) {
        return elementUtils.getPackageOf(typeUtils.asElement(typeMirror)).getSimpleName().toString();
    }

    public static Name simpleName(TypeMirror typeMirror) {
        return typeUtils.asElement(typeMirror).getSimpleName();
    }

    public static String serializerName(TypeMirror typeMirror) {
        ClassName type = ClassName.bestGuess(typeMirror.toString());
        return serializerName(type.packageName(), type.simpleName());
    }

    public static String serializerName(String packageName, String beanName) {
        return packageName + "." + beanName + BEAN_JSON_SERIALIZER_IMPL;
    }

    public static String deserializerName(TypeMirror typeMirror) {
        ClassName type = ClassName.bestGuess(typeMirror.toString());
        return deserializerName(type.packageName(), type.simpleName());
    }

    public static String deserializerName(String packageName, String beanName) {
        return packageName + "." + beanName + BEAN_JSON_DESERIALIZER_IMPL;
    }

    public static String generateDeserializer(TypeMirror typeMirror) {
        ClassName type = ClassName.bestGuess(typeMirror.toString());
        return new DeserializerGenerator().generate(typeMirror, type.packageName(), Type.simpleName(typeMirror));
    }

    public static String generateSerializer(TypeMirror typeMirror) {
        ClassName type = ClassName.bestGuess(typeMirror.toString());
        return new SerializerGenerator().generate(typeMirror, type.packageName(), Type.simpleName(typeMirror));
    }
}
