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
package com.progressoft.brix.domino.gwtjackson.processor.serialization;

import com.progressoft.brix.domino.gwtjackson.processor.MappersChainBuilder;
import com.progressoft.brix.domino.gwtjackson.processor.Type;
import com.progressoft.brix.domino.gwtjackson.processor.TypeRegistry;
import com.progressoft.brix.domino.gwtjackson.ser.CollectionJsonSerializer;
import com.progressoft.brix.domino.gwtjackson.ser.EnumJsonSerializer;
import com.progressoft.brix.domino.gwtjackson.ser.IterableJsonSerializer;
import com.progressoft.brix.domino.gwtjackson.ser.array.ArrayJsonSerializer;
import com.progressoft.brix.domino.gwtjackson.ser.array.dd.Array2dJsonSerializer;
import com.progressoft.brix.domino.gwtjackson.ser.map.MapJsonSerializer;
import com.squareup.javapoet.CodeBlock;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import java.util.Deque;
import java.util.LinkedList;

public class FieldSerializerChainBuilder implements MappersChainBuilder {

    private static final String GET_INSTANCE = "$T.getInstance()";
    private static final String NEW_INSTANCE = "$T.newInstance(";

    private CodeBlock.Builder builder=CodeBlock.builder();
    private Deque<Class> serializers=new LinkedList<>();

    @Override
    public CodeBlock getInstance(Element field){
        return builder.add(getFieldSerializer(field.asType()), asClassesArray()).build();
    }

    private Class[] asClassesArray() {
        return serializers.toArray(new Class[serializers.size()]);
    }

    private String getFieldSerializer(TypeMirror typeMirror) {
        if(Type.isCollection(typeMirror))
            return getCollectionSerializer(typeMirror);
        if(Type.isIterable(typeMirror))
            return getIterableSerializer(typeMirror);
        if(Type.isMap(typeMirror))
            return getMapSerializer(typeMirror);
        if(Type.isArray(typeMirror))
            return getArraySerializer(typeMirror);
        if(Type.isEnum(typeMirror))
            return getEnumSerializer();
        return getBasicSerializer(typeMirror);
    }

    private String getEnumSerializer() {
        serializers.addLast(EnumJsonSerializer.class);
        return GET_INSTANCE;
    }

    private String getBasicSerializer(TypeMirror typeMirror) {
        serializers.addLast(TypeRegistry.getSerializer(typeMirror));
        return GET_INSTANCE;
    }

    private String getMapSerializer(TypeMirror typeMirror) {
        serializers.addLast(MapJsonSerializer.class);
        return NEW_INSTANCE+getKeySerializer(Type.firstTypeArgument(typeMirror))+", " + getFieldSerializer(Type.secondTypeArgument(typeMirror))+")";
    }

    private String getKeySerializer(TypeMirror typeMirror) {
        if(Type.isEnum(typeMirror))
            serializers.addLast(TypeRegistry.getKeySerializer(Enum.class.getName()));
        else
            serializers.addLast(TypeRegistry.getKeySerializer(typeMirror.toString()));
        return GET_INSTANCE;
    }

    private String getCollectionSerializer(TypeMirror typeMirror) {
        serializers.addLast(CollectionJsonSerializer.class);
        return NEW_INSTANCE+getFieldSerializer(Type.firstTypeArgument(typeMirror))+")";
    }

    private String getIterableSerializer(TypeMirror typeMirror) {
        serializers.addLast(IterableJsonSerializer.class);
        return NEW_INSTANCE+getFieldSerializer(Type.firstTypeArgument(typeMirror))+")";
    }

    private String getArraySerializer(TypeMirror typeMirror) {
        if(Type.isPrimitiveArray(typeMirror)) {
            return getBasicSerializer(typeMirror);
        }else if(Type.is2dArray(typeMirror)){
            serializers.addLast(Array2dJsonSerializer.class);
            return NEW_INSTANCE+getFieldSerializer(Type.deepArrayComponentType(typeMirror))+")";
        }else{
            serializers.addLast(ArrayJsonSerializer.class);
            return NEW_INSTANCE+getFieldSerializer(Type.arrayComponentType(typeMirror))+")";
        }
    }

}
