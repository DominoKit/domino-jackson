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
package org.dominokit.jacksonapt.processor.serialization;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import org.dominokit.jacksonapt.processor.MappersChainBuilder;
import org.dominokit.jacksonapt.processor.Type;
import org.dominokit.jacksonapt.processor.TypeRegistry;
import org.dominokit.jacksonapt.ser.CollectionJsonSerializer;
import org.dominokit.jacksonapt.ser.EnumJsonSerializer;
import org.dominokit.jacksonapt.ser.IterableJsonSerializer;
import org.dominokit.jacksonapt.ser.array.ArrayJsonSerializer;
import org.dominokit.jacksonapt.ser.array.dd.Array2dJsonSerializer;
import org.dominokit.jacksonapt.ser.map.MapJsonSerializer;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import java.util.Deque;
import java.util.LinkedList;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * <p>FieldSerializerChainBuilder class.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public class FieldSerializerChainBuilder implements MappersChainBuilder {

    private static final String GET_INSTANCE = "$T.getInstance()";
    private static final String NEW_INSTANCE = "$T.newInstance(";
    private boolean rootGenerated = true;

    private CodeBlock.Builder builder = CodeBlock.builder();
    private Deque<TypeName> serializers = new LinkedList<>();
    private final TypeMirror beanType;
	private final String packageName;


    /**
     * <p>Constructor for FieldSerializerChainBuilder.</p>
     *
     * @param beanType a {@link javax.lang.model.type.TypeMirror} object.
     */
    public FieldSerializerChainBuilder(TypeMirror beanType) {
        this.beanType = beanType;
        this.packageName = null;
        this.rootGenerated = true;
    }

    /**
     * <p>Constructor for FieldSerializerChainBuilder.</p>
     *
     * @param beanType a {@link javax.lang.model.type.TypeMirror} object.
     */
    public FieldSerializerChainBuilder(TypeMirror beanType, boolean rootGenerated) {
        this.beanType = beanType;
        this.packageName = null;
        this.rootGenerated = rootGenerated;
    }

    /**
     * <p>Constructor for FieldSerializerChainBuilder.</p>
     *
     * @param beanType a {@link javax.lang.model.type.TypeMirror} object.
     * @param packageName a {@link java.lang.String} object.
     */
    public FieldSerializerChainBuilder(String packageName, TypeMirror beanType) {
        this.beanType = beanType;
        this.packageName = packageName;
        this.rootGenerated = true;
    }

    /**
     * <p>Constructor for FieldSerializerChainBuilder.</p>
     *
     * @param beanType a {@link javax.lang.model.type.TypeMirror} object.
     * @param packageName a {@link java.lang.String} object.
     */
    public FieldSerializerChainBuilder(String packageName, TypeMirror beanType, boolean rootGenerated) {
        this.beanType = beanType;
        this.packageName = packageName;
        this.rootGenerated = rootGenerated;
    }

    /** {@inheritDoc} */
    @Override
    public CodeBlock getInstance(Element field) {
        return builder.add(getFieldSerializer(field.asType()), asClassesArray()).build();
    }

    /** {@inheritDoc} */
    @Override
    public CodeBlock getInstance(TypeMirror fieldType) {
        return builder.add(getFieldSerializer(fieldType), asClassesArray()).build();
    }
    
    private TypeName[] asClassesArray() {
        return serializers.toArray(new TypeName[serializers.size()]);
    }

    private String getFieldSerializer(TypeMirror typeMirror) {
    	typeMirror = Type.removeOuterWildCards(typeMirror);
    	
        if (Type.isCollection(typeMirror))
            return getCollectionSerializer(typeMirror);
        if (Type.isIterable(typeMirror))
            return getIterableSerializer(typeMirror);
        if (Type.isMap(typeMirror))
            return getMapSerializer(typeMirror);
        if (Type.isArray(typeMirror))
            return getArraySerializer(typeMirror);
        if (Type.isEnum(typeMirror))
            return getEnumSerializer();
        return getBasicOrCustomSerializer(typeMirror);
    }

    private String getBasicOrCustomSerializer(TypeMirror typeMirror) {
        if (Type.isBasicType(typeMirror))
            return getBasicSerializer(typeMirror);
        return getCustomSerializer(typeMirror);
    }

    private String getCustomSerializer(TypeMirror typeMirror) {
        if (Type.stringifyTypeWithPackage(typeMirror).equals(Type.stringifyTypeWithPackage(beanType)) && rootGenerated) {
            serializers.addLast(ClassName.bestGuess(Type.serializerName(getPackageName(typeMirror), typeMirror)));
        } else {
            if (TypeRegistry.containsSerializer(Type.stringifyTypeWithPackage(typeMirror))) {
                serializers.addLast(TypeRegistry.getCustomSerializer(typeMirror));
            } else {
                TypeRegistry.registerSerializer(Type.stringifyTypeWithPackage(typeMirror), ClassName.bestGuess(generateCustomSerializer(typeMirror)));
                serializers.addLast(TypeRegistry.getCustomSerializer(typeMirror));
                this.rootGenerated = true;
            }
        }
        return "new $T()";
    }

    private String getPackageName(TypeMirror typeMirror) {
        if (Type.isJsonMapper(typeMirror) || isNull(this.packageName)) {
            return ClassName.bestGuess(typeMirror.toString()).packageName();
        } else {
            return this.packageName;
        }
    }

    private String generateCustomSerializer(TypeMirror typeMirror) {
    	return Type.generateSerializer(getPackageName(typeMirror), typeMirror);
    }

    private String getEnumSerializer() {
        serializers.addLast(TypeName.get(EnumJsonSerializer.class));
        return GET_INSTANCE;
    }

    private String getBasicSerializer(TypeMirror typeMirror) {
        serializers.addLast(TypeRegistry.getSerializer(typeMirror));
        return GET_INSTANCE;
    }

    private String getMapSerializer(TypeMirror typeMirror) {
        serializers.addLast(TypeName.get(MapJsonSerializer.class));
        return NEW_INSTANCE + getKeySerializer(Type.firstTypeArgument(typeMirror)) + ", " + getFieldSerializer(Type.secondTypeArgument(typeMirror)) + ")";
    }

    private String getKeySerializer(TypeMirror typeMirror) {
    	typeMirror = Type.removeOuterWildCards(typeMirror);
        if (Type.isEnum(typeMirror))
            serializers.addLast(TypeRegistry.getKeySerializer(Enum.class.getName()));
        else
            serializers.addLast(TypeRegistry.getKeySerializer(Type.stringifyTypeWithPackage(Type.removeOuterWildCards(typeMirror))));
        return GET_INSTANCE;
    }

    private String getCollectionSerializer(TypeMirror typeMirror) {
        serializers.addLast(TypeName.get(CollectionJsonSerializer.class));
        return NEW_INSTANCE + getFieldSerializer(Type.firstTypeArgument(typeMirror)) + ")";
    }

    private String getIterableSerializer(TypeMirror typeMirror) {
        serializers.addLast(TypeName.get(IterableJsonSerializer.class));
        return NEW_INSTANCE + getFieldSerializer(Type.firstTypeArgument(typeMirror)) + ")";
    }

    private String getArraySerializer(TypeMirror typeMirror) {
        if (Type.isPrimitiveArray(typeMirror)) {
            return getBasicSerializer(typeMirror);
        } else if (Type.is2dArray(typeMirror)) {
            serializers.addLast(TypeName.get(Array2dJsonSerializer.class));
            return NEW_INSTANCE + getFieldSerializer(Type.deepArrayComponentType(typeMirror)) + ")";
        } else {
            serializers.addLast(TypeName.get(ArrayJsonSerializer.class));
            return NEW_INSTANCE + getFieldSerializer(Type.arrayComponentType(typeMirror)) + ")";
        }
    }

}
