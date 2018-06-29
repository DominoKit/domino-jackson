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

package org.dominokit.jacksonapt.deser.collection;

import org.dominokit.jacksonapt.JsonDeserializer;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Default {@link org.dominokit.jacksonapt.JsonDeserializer} implementation for {@link java.util.Collection}. The deserialization process returns an {@link java.util.ArrayList}.
 *
 * @param <T> Type of the elements inside the {@link java.util.Collection}
 * @author Nicolas Morel
 * @version $Id: $
 */
public class CollectionJsonDeserializer<T> extends BaseCollectionJsonDeserializer<Collection<T>, T> {

    /**
     * <p>newInstance</p>
     *
     * @param deserializer {@link org.dominokit.jacksonapt.JsonDeserializer} used to deserialize the objects inside the {@link java.util.Collection}.
     * @param <T>          Type of the elements inside the {@link java.util.Collection}
     * @return a new instance of {@link org.dominokit.jacksonapt.deser.collection.CollectionJsonDeserializer}
     */
    public static <T> CollectionJsonDeserializer<T> newInstance(JsonDeserializer<T> deserializer) {
        return new CollectionJsonDeserializer<T>(deserializer);
    }

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link Collection}.
     */
    private CollectionJsonDeserializer(JsonDeserializer<T> deserializer) {
        super(deserializer);
    }

    /** {@inheritDoc} */
    @Override
    protected Collection<T> newCollection() {
        return new ArrayList<T>();
    }
}
