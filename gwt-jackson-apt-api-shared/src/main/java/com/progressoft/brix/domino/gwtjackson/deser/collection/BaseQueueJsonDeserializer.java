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

package com.progressoft.brix.domino.gwtjackson.deser.collection;

import com.progressoft.brix.domino.gwtjackson.JsonDeserializer;

import java.util.Queue;

/**
 * Base {@link JsonDeserializer} implementation for {@link Queue}.
 *
 * @param <Q> {@link Queue} type
 * @param <T> Type of the elements inside the {@link Queue}
 * @author Nicolas Morel
 * @version $Id: $
 */
public abstract class BaseQueueJsonDeserializer<Q extends Queue<T>, T> extends BaseCollectionJsonDeserializer<Q, T> {

    /**
     * <p>Constructor for BaseQueueJsonDeserializer.</p>
     *
     * @param deserializer {@link JsonDeserializer} used to map the objects inside the {@link Queue}.
     */
    public BaseQueueJsonDeserializer(JsonDeserializer<T> deserializer) {
        super(deserializer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isNullValueAllowed() {
        return false;
    }
}