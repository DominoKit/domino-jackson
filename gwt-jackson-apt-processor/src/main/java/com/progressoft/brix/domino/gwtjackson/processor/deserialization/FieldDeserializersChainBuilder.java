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
package com.progressoft.brix.domino.gwtjackson.processor.deserialization;

import com.progressoft.brix.domino.gwtjackson.deser.EnumJsonDeserializer;
import com.progressoft.brix.domino.gwtjackson.deser.array.ArrayJsonDeserializer;
import com.progressoft.brix.domino.gwtjackson.deser.array.dd.Array2dJsonDeserializer;
import com.progressoft.brix.domino.gwtjackson.processor.MappersChainBuilder;
import com.progressoft.brix.domino.gwtjackson.processor.Type;
import com.progressoft.brix.domino.gwtjackson.processor.TypeRegistry;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import java.util.Deque;
import java.util.LinkedList;

import static com.progressoft.brix.domino.gwtjackson.processor.ObjectMapperProcessor.typeUtils;

public class FieldDeserializersChainBuilder implements MappersChainBuilder {
    private static final String GET_INSTANCE = "$T.getInstance()";
    private static final String NEW_INSTANCE = "$T.newInstance(";

    private CodeBlock.Builder builder = CodeBlock.builder();
    private StringBuffer format = new StringBuffer();
    private Deque<TypeName> deserializers = new LinkedList<>();

    @Override
    public CodeBlock getInstance(Element field) {
        return builder.add(getFieldDeserializer(field.asType()), asClassesArray()).build();
    }

    private TypeName[] asClassesArray() {
        return deserializers.toArray(new TypeName[deserializers.size()]);
    }

    private String getFieldDeserializer(TypeMirror typeMirror) {
        if (Type.isIterable(typeMirror))
            return getIterableDeserializer(typeMirror);
        if (Type.isMap(typeMirror))
            return getMapDeserializer(typeMirror);
        if (Type.isArray(typeMirror))
            return getArrayDeserializer(typeMirror);
        if (Type.isEnum(typeMirror))
            return getEnumDeserializer(typeMirror);
        return getBasicDeserializer(typeMirror);
    }

    private String getEnumDeserializer(TypeMirror typeMirror) {
        deserializers.addLast(TypeName.get(EnumJsonDeserializer.class));
        deserializers.addLast(TypeName.get(typeMirror));
        return NEW_INSTANCE+"$T.class)";
    }

    private String getBasicDeserializer(TypeMirror typeMirror) {
        deserializers.addLast(TypeName.get(TypeRegistry.getDeserializer(typeMirror)));
        return GET_INSTANCE;
    }

    private String getMapDeserializer(TypeMirror typeMirror) {
        deserializers.addLast(TypeName.get(TypeRegistry.getMapDeserializer(typeMirror)));
        return NEW_INSTANCE+getKeyDeserializer(Type.firstTypeArgument(typeMirror))+", "+getFieldDeserializer(Type.secondTypeArgument(typeMirror))+")";
    }

    private String getKeyDeserializer(TypeMirror typeMirror) {
        if (Type.isEnum(typeMirror))
            return getEnumKeyDeserializer(typeMirror);
        return getBasicKeyDeserializer(typeMirror);
    }

    private String getBasicKeyDeserializer(TypeMirror typeMirror) {
        deserializers.addLast(TypeName.get(TypeRegistry.getKeyDeserializer(typeMirror.toString())));
        return GET_INSTANCE;
    }

    private String getEnumKeyDeserializer(TypeMirror typeMirror) {
        deserializers.addLast(TypeName.get(TypeRegistry.getKeyDeserializer(Enum.class.getName())));
        deserializers.addLast(TypeName.get(typeMirror));
        return NEW_INSTANCE+"$T.class"+")";
    }

    private String getIterableDeserializer(TypeMirror typeMirror) {
        deserializers.addLast(TypeName.get(TypeRegistry.getCollectionDeserializer(typeMirror)));
        return NEW_INSTANCE+getFieldDeserializer(Type.firstTypeArgument(typeMirror))+")";
    }

    private String getArrayDeserializer(TypeMirror typeMirror) {
        if (Type.isPrimitiveArray(typeMirror)) {
            return getBasicDeserializer(typeMirror);
        } else if (Type.is2dArray(typeMirror)) {
            deserializers.addLast(TypeName.get(Array2dJsonDeserializer.class));
            return NEW_INSTANCE+getFieldDeserializer(Type.deepArrayComponentType(typeMirror))+", "+getArray2dCreatorFormat(typeMirror)+ ")";
        } else {
            deserializers.addLast(TypeName.get(ArrayJsonDeserializer.class));
            return NEW_INSTANCE+getFieldDeserializer(Type.arrayComponentType(typeMirror))+", "+ getArrayCreatorFormat(typeMirror)+")";
        }
    }

    //$T.newInstance($T.getInstance(), (first, second) -> new $T[first][second])

    private String getArrayCreatorFormat(TypeMirror typeMirror) {
        deserializers.addLast(TypeName.get(ArrayJsonDeserializer.ArrayCreator.class));
        deserializers.addLast(TypeName.get(Type.arrayComponentType(typeMirror)));
        deserializers.addLast(TypeName.get(typeUtils.erasure(Type.arrayComponentType(typeMirror))));
        return "($T<$T>) $T[]::new";
    }

    private String getArray2dCreatorFormat(TypeMirror typeMirror) {
        deserializers.addLast(TypeName.get(Array2dJsonDeserializer.Array2dCreator.class));
        deserializers.addLast(TypeName.get(Type.deepArrayComponentType(typeMirror)));
        deserializers.addLast(TypeName.get(typeUtils.erasure(Type.deepArrayComponentType(typeMirror))));
        return "($T<$T>) (first, second) -> new $T[first][second]";
    }
}
