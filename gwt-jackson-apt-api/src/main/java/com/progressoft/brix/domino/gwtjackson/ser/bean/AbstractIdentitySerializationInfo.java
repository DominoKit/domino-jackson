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

package com.progressoft.brix.domino.gwtjackson.ser.bean;

import com.progressoft.brix.domino.gwtjackson.JsonSerializationContext;
import com.progressoft.brix.domino.gwtjackson.JsonSerializer;

/**
 * Contains identity informations for serialization process.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public abstract class AbstractIdentitySerializationInfo<T, I> extends HasSerializer<I,
        JsonSerializer<I>> implements IdentitySerializationInfo<T> {

    /**
     * if we always serialize the bean as an id even for the first encounter.
     */
    private final boolean alwaysAsId;

    /**
     * Name of the property holding the identity
     */
    private final String propertyName;

    /**
     * <p>Constructor for AbstractIdentitySerializationInfo.</p>
     *
     * @param alwaysAsId   a boolean.
     * @param propertyName a {@link String} object.
     */
    protected AbstractIdentitySerializationInfo(boolean alwaysAsId, String propertyName) {
        this.alwaysAsId = alwaysAsId;
        this.propertyName = propertyName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAlwaysAsId() {
        return alwaysAsId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isProperty() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract ObjectIdSerializer<I> getObjectId(T bean, JsonSerializationContext ctx);
}
