/*
 * Copyright 2013 Nicolas Morel
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

package org.dominokit.jackson.ser.bean;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import org.dominokit.jackson.JsonSerializationContext;
import org.dominokit.jackson.JsonSerializer;
import org.dominokit.jackson.JsonSerializerParameters;
import org.dominokit.jackson.stream.JsonWriter;

/** Base implementation of {@link org.dominokit.jackson.JsonSerializer} for beans. */
public abstract class AbstractBeanJsonSerializer<T> extends JsonSerializer<T>
    implements InternalSerializer<T> {

  protected final BeanPropertySerializer[] serializers;

  private final Map<Class, SubtypeSerializer> subtypeClassToSerializer;

  private final IdentitySerializationInfo<T> defaultIdentityInfo;

  private final TypeSerializationInfo<T> defaultTypeInfo;

  private final AnyGetterPropertySerializer<T> anyGetterPropertySerializer;

  /** Constructor for AbstractBeanJsonSerializer. */
  protected AbstractBeanJsonSerializer() {
    this.serializers = initSerializers();
    this.defaultIdentityInfo = initIdentityInfo();
    this.defaultTypeInfo = initTypeInfo();
    this.subtypeClassToSerializer = initMapSubtypeClassToSerializer();
    this.anyGetterPropertySerializer = initAnyGetterPropertySerializer();
  }

  /**
   * Initialize the {@link java.util.Map} containing the property serializers. Returns an empty map
   * if there are no properties to serialize.
   *
   * @return an array of {@link org.dominokit.jackson.ser.bean.BeanPropertySerializer} objects.
   */
  protected BeanPropertySerializer[] initSerializers() {
    return new BeanPropertySerializer[0];
  }

  /**
   * Initialize the {@link org.dominokit.jackson.ser.bean.IdentitySerializationInfo}. Returns null
   * if there is no {@link com.fasterxml.jackson.annotation.JsonIdentityInfo} annotation on bean.
   *
   * @return a {@link org.dominokit.jackson.ser.bean.IdentitySerializationInfo} object.
   */
  protected IdentitySerializationInfo<T> initIdentityInfo() {
    return null;
  }

  /**
   * Initialize the {@link org.dominokit.jackson.ser.bean.TypeSerializationInfo}. Returns null if
   * there is no {@link com.fasterxml.jackson.annotation.JsonTypeInfo} annotation on bean.
   *
   * @return a {@link org.dominokit.jackson.ser.bean.TypeSerializationInfo} object.
   */
  protected TypeSerializationInfo<T> initTypeInfo() {
    return null;
  }

  /**
   * Initialize the {@link java.util.Map} containing the {@link
   * org.dominokit.jackson.ser.bean.SubtypeSerializer}. Returns an empty map if the bean has no
   * subtypes.
   *
   * @return a {@link java.util.Map} object.
   */
  protected Map<Class, SubtypeSerializer> initMapSubtypeClassToSerializer() {
    return Collections.emptyMap();
  }

  /**
   * Initialize the {@link org.dominokit.jackson.ser.bean.AnyGetterPropertySerializer}. Returns null
   * if there is no method annoted with {@link com.fasterxml.jackson.annotation.JsonAnyGetter} on
   * bean.
   *
   * @return a {@link org.dominokit.jackson.ser.bean.AnyGetterPropertySerializer} object.
   */
  protected AnyGetterPropertySerializer<T> initAnyGetterPropertySerializer() {
    return null;
  }

  /**
   * getSerializedType
   *
   * @return a {@link java.lang.Class} object.
   */
  public abstract Class getSerializedType();

  /** {@inheritDoc} */
  @Override
  public void doSerialize(
      JsonWriter writer, T value, JsonSerializationContext ctx, JsonSerializerParameters params) {
    getSerializer(writer, value, ctx)
        .serializeInternally(writer, value, ctx, params, defaultIdentityInfo, defaultTypeInfo);
  }

  private InternalSerializer<T> getSerializer(
      JsonWriter writer, T value, JsonSerializationContext ctx) {
    if (value.getClass() == getSerializedType()) {
      return this;
    }
    SubtypeSerializer subtypeSerializer = subtypeClassToSerializer.get(value.getClass());
    if (null == subtypeSerializer) {
      if (ctx.getLogger().isLoggable(Level.FINE)) {
        ctx.getLogger()
            .fine(
                "Cannot find serializer for class "
                    + value.getClass()
                    + ". Fallback to the serializer of "
                    + getSerializedType());
      }
      return this;
    }
    return subtypeSerializer;
  }

  /** {@inheritDoc} */
  public void serializeInternally(
      JsonWriter writer,
      T value,
      JsonSerializationContext ctx,
      JsonSerializerParameters params,
      IdentitySerializationInfo<T> defaultIdentityInfo,
      TypeSerializationInfo<T> defaultTypeInfo) {

    // Processing the parameters. We fallback to default if parameter is not present.
    final IdentitySerializationInfo identityInfo =
        null == params.getIdentityInfo() ? defaultIdentityInfo : params.getIdentityInfo();
    final TypeSerializationInfo typeInfo =
        null == params.getTypeInfo() ? defaultTypeInfo : params.getTypeInfo();
    final Set<String> ignoredProperties =
        null == params.getIgnoredProperties()
            ? Collections.<String>emptySet()
            : params.getIgnoredProperties();

    if (params.isUnwrapped()) {
      // if unwrapped, we serialize the properties inside the current object
      serializeProperties(writer, value, ctx, ignoredProperties, identityInfo);
      return;
    }

    ObjectIdSerializer<?> idWriter = null;
    if (null != identityInfo) {
      idWriter = ctx.getObjectId(value);
      if (null != idWriter) {
        // the bean has already been serialized, we just serialize the id
        idWriter.serializeId(writer, ctx);
        return;
      }

      idWriter = identityInfo.getObjectId(value, ctx);
      if (identityInfo.isAlwaysAsId()) {
        idWriter.serializeId(writer, ctx);
        return;
      }
      ctx.addObjectId(value, idWriter);
    }

    if (null != typeInfo) {
      String typeInformation = typeInfo.getTypeInfo(value.getClass());
      if (null == typeInformation) {
        ctx.getLogger().log(Level.WARNING, "Cannot find type info for class " + value.getClass());
      } else {
        switch (typeInfo.getInclude()) {
          case PROPERTY:
            // type info is included as a property of the object
            serializeObject(
                writer,
                value,
                ctx,
                ignoredProperties,
                identityInfo,
                idWriter,
                typeInfo.getPropertyName(),
                typeInformation);
            return;

          case WRAPPER_OBJECT:
            // type info is included in a wrapper object that contains only one property. The name
            // of this property is the type
            // info and the value the object
            writer.beginObject();
            writer.name(typeInformation);
            serializeObject(writer, value, ctx, ignoredProperties, identityInfo, idWriter);
            writer.endObject();
            return;

          case WRAPPER_ARRAY:
            // type info is included in a wrapper array that contains two elements. First one is the
            // type
            // info and the second one the object
            writer.beginArray();
            writer.value(typeInformation);
            serializeObject(writer, value, ctx, ignoredProperties, identityInfo, idWriter);
            writer.endArray();
            return;

          default:
            ctx.getLogger()
                .log(
                    Level.SEVERE, "JsonTypeInfo.As." + typeInfo.getInclude() + " is not supported");
        }
      }
    }

    serializeObject(writer, value, ctx, ignoredProperties, identityInfo, idWriter);
  }

  /**
   * Serializes all the properties of the bean in a json object.
   *
   * @param writer a {@link JsonWriter} writer
   * @param value bean to serialize
   * @param ctx a {@link JsonSerializationContext} context of the serialization process
   * @param ignoredProperties a {@link Set} of ignored properties
   * @param identityInfo a {@link IdentitySerializationInfo} identity info
   * @param idWriter a {@link ObjectIdSerializer} identifier writer
   */
  private void serializeObject(
      JsonWriter writer,
      T value,
      JsonSerializationContext ctx,
      Set<String> ignoredProperties,
      IdentitySerializationInfo identityInfo,
      ObjectIdSerializer<?> idWriter) {
    serializeObject(writer, value, ctx, ignoredProperties, identityInfo, idWriter, null, null);
  }

  /**
   * Serializes all the properties of the bean in a json object.
   *
   * @param writer a {@link JsonWriter} writer
   * @param value bean to serialize
   * @param ctx a {@link JsonSerializationContext} context of the serialization process
   * @param ignoredProperties a {@link Set} of ignored properties
   * @param identityInfo a {@link IdentitySerializationInfo} identity info
   * @param idWriter a {@link ObjectIdSerializer} identifier writer
   * @param typeName a {@link String} in case of type info as property, the name of the property
   * @param typeInformation a {@link String} in case of type info as property, the type information
   */
  protected void serializeObject(
      JsonWriter writer,
      T value,
      JsonSerializationContext ctx,
      Set<String> ignoredProperties,
      IdentitySerializationInfo identityInfo,
      ObjectIdSerializer<?> idWriter,
      String typeName,
      String typeInformation) {
    writer.beginObject();

    if (null != typeName && null != typeInformation) {
      writer.name(typeName);
      writer.value(typeInformation);
    }

    if (null != idWriter) {
      writer.name(identityInfo.getPropertyName());
      idWriter.serializeId(writer, ctx);
    }

    serializeProperties(writer, value, ctx, ignoredProperties, identityInfo);

    writer.endObject();
  }

  private void serializeProperties(
      JsonWriter writer,
      T value,
      JsonSerializationContext ctx,
      Set<String> ignoredProperties,
      IdentitySerializationInfo identityInfo) {
    for (BeanPropertySerializer<T, ?> propertySerializer : serializers) {
      if ((null == identityInfo
              || !identityInfo.isProperty()
              || !identityInfo.getPropertyName().equals(propertySerializer.getPropertyName()))
          && !ignoredProperties.contains(propertySerializer.getPropertyName())) {
        propertySerializer.serializePropertyName(writer, value, ctx);
        propertySerializer.serialize(writer, value, ctx);
      }
    }

    if (null != anyGetterPropertySerializer) {
      anyGetterPropertySerializer.serialize(writer, value, ctx);
    }
  }
}
