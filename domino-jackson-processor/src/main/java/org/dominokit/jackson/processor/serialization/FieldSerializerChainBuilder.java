/*
 * Copyright © 2019 Dominokit
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
package org.dominokit.jackson.processor.serialization;

import com.google.auto.common.MoreElements;
import com.google.auto.common.MoreTypes;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import java.util.Deque;
import java.util.LinkedList;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import org.dominokit.jackson.processor.MappersChainBuilder;
import org.dominokit.jackson.processor.Type;
import org.dominokit.jackson.processor.TypeRegistry;
import org.dominokit.jackson.ser.CollectionJsonSerializer;
import org.dominokit.jackson.ser.EnumJsonSerializer;
import org.dominokit.jackson.ser.IterableJsonSerializer;
import org.dominokit.jackson.ser.array.ArrayJsonSerializer;
import org.dominokit.jackson.ser.array.dd.Array2dJsonSerializer;
import org.dominokit.jackson.ser.map.MapJsonSerializer;

/**
 * Generate recursively a chain of Serializers for a specific type. for example for the <code>
 * List&lt;Long&gt;[]</code> type will result in generating the following Serializers chain <code>
 * ListJsonDeserializer.newInstance(ArrayJsonDeserializer.newInstance(BaseNumberJsonDeserializer.LongJsonDeserializer.getInstance(), (ArrayJsonDeserializer.ArrayCreator&lt;Long&gt;) Long[]::new))
 * </code>
 */
public class FieldSerializerChainBuilder implements MappersChainBuilder {

  private static final String GET_INSTANCE = "$T.getInstance()";
  private static final String NEW_INSTANCE = "$T.newInstance(";
  private boolean rootGenerated;

  private CodeBlock.Builder builder = CodeBlock.builder();
  private Deque<TypeName> serializers = new LinkedList<>();
  private final TypeMirror beanType;

  /**
   * Constructor for FieldSerializerChainBuilder.
   *
   * @param beanType a {@link javax.lang.model.type.TypeMirror} object.
   */
  public FieldSerializerChainBuilder(TypeMirror beanType) {
    this.beanType = beanType;
    this.rootGenerated = true;
  }

  /**
   * Constructor for FieldSerializerChainBuilder.
   *
   * @param beanType a {@link javax.lang.model.type.TypeMirror} object.
   * @param rootGenerated boolean.
   */
  public FieldSerializerChainBuilder(TypeMirror beanType, boolean rootGenerated) {
    this.beanType = beanType;
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

    if (Type.isCollection(typeMirror)) return getCollectionSerializer(typeMirror);
    if (Type.isIterable(typeMirror)) return getIterableSerializer(typeMirror);
    if (Type.isMap(typeMirror)) return getMapSerializer(typeMirror);
    if (Type.isArray(typeMirror)) return getArraySerializer(typeMirror);
    if (Type.isEnum(typeMirror)) return getEnumSerializer();
    return getBasicOrCustomSerializer(typeMirror);
  }

  private String getBasicOrCustomSerializer(TypeMirror typeMirror) {
    if (Type.isBasicType(typeMirror)) return getBasicSerializer(typeMirror);
    return getCustomSerializer(typeMirror);
  }

  private String getCustomSerializer(TypeMirror typeMirror) {
    if (Type.stringifyTypeWithPackage(typeMirror).equals(Type.stringifyTypeWithPackage(beanType))
        && rootGenerated) {
      serializers.addLast(
          ClassName.bestGuess(Type.serializerName(getPackageName(typeMirror), typeMirror)));
    } else {
      if (TypeRegistry.containsSerializer(Type.stringifyTypeWithPackage(typeMirror))) {
        serializers.addLast(TypeRegistry.getCustomSerializer(typeMirror));
      } else {
        TypeRegistry.registerSerializer(
            Type.stringifyTypeWithPackage(typeMirror),
            ClassName.bestGuess(generateCustomSerializer(typeMirror)));
        serializers.addLast(TypeRegistry.getCustomSerializer(typeMirror));
        this.rootGenerated = true;
      }
    }
    return "$T.getInstance()";
  }

  private String getPackageName(TypeMirror typeMirror) {
    return MoreElements.getPackage(MoreTypes.asTypeElement(typeMirror))
        .getQualifiedName()
        .toString();
  }

  private String generateCustomSerializer(TypeMirror typeMirror) {
    if (TypeRegistry.containsSerializer(typeMirror)
        || TypeRegistry.isInActiveGenSerializer(typeMirror)) {
      return Type.serializerName(getPackageName(typeMirror), typeMirror);
    }
    TypeRegistry.addInActiveGenSerializer(typeMirror);
    String serializerName = Type.generateSerializer(typeMirror);
    TypeRegistry.removeInActiveGenSerializer(typeMirror);
    return serializerName;
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
    return NEW_INSTANCE
        + getKeySerializer(Type.firstTypeArgument(typeMirror))
        + ", "
        + getFieldSerializer(Type.secondTypeArgument(typeMirror))
        + ")";
  }

  private String getKeySerializer(TypeMirror typeMirror) {
    typeMirror = Type.removeOuterWildCards(typeMirror);
    if (Type.isEnum(typeMirror))
      serializers.addLast(TypeRegistry.getKeySerializer(Enum.class.getName()));
    else
      serializers.addLast(
          TypeRegistry.getKeySerializer(
              Type.stringifyTypeWithPackage(Type.removeOuterWildCards(typeMirror))));
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
