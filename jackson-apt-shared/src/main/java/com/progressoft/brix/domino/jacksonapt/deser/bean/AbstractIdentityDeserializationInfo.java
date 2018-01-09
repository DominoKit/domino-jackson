package com.progressoft.brix.domino.jacksonapt.deser.bean;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey;
import com.progressoft.brix.domino.jacksonapt.JsonDeserializationContext;
import com.progressoft.brix.domino.jacksonapt.JsonDeserializer;
import com.progressoft.brix.domino.jacksonapt.stream.JsonReader;

/**
 * <p>Abstract AbstractIdentityDeserializationInfo class.</p>
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public abstract class AbstractIdentityDeserializationInfo<T, V> extends HasDeserializer<V,
        JsonDeserializer<V>> implements IdentityDeserializationInfo<T> {

    /**
     * Name of the property holding the identity
     */
    private final String propertyName;

    /**
     * Type of {@link ObjectIdGenerator} used for generating Object Id
     */
    private final Class<?> type;

    /**
     * Scope of the Object Id (may be null, to denote global)
     */
    private final Class<?> scope;

    /**
     * <p>Constructor for AbstractIdentityDeserializationInfo.</p>
     *
     * @param propertyName a {@link String} object.
     * @param type         a {@link Class} object.
     * @param scope        a {@link Class} object.
     */
    protected AbstractIdentityDeserializationInfo(String propertyName, Class<?> type, Class<?> scope) {
        this.propertyName = propertyName;
        this.type = type;
        this.scope = scope;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String getPropertyName() {
        return propertyName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isProperty() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IdKey newIdKey(Object id) {
        return new IdKey(type, scope, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Object readId(JsonReader reader, JsonDeserializationContext ctx) {
        return getDeserializer().deserialize(reader, ctx);
    }

}
