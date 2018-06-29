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

import java.util.Vector;

/**
 * Default {@link org.dominokit.jacksonapt.JsonDeserializer} implementation for {@link java.util.Vector}.
 *
 * @param <T> Type of the elements inside the {@link java.util.Vector}
 * @author Nicolas Morel
 * @version $Id: $
 */
public class VectorJsonDeserializer<T> extends BaseListJsonDeserializer<Vector<T>, T> {

    /**
     * <p>newInstance</p>
     *
     * @param deserializer {@link org.dominokit.jacksonapt.JsonDeserializer} used to deserialize the objects inside the {@link java.util.Vector}.
     * @param <T>          Type of the elements inside the {@link java.util.Vector}
     * @return a new instance of {@link org.dominokit.jacksonapt.deser.collection.VectorJsonDeserializer}
     */
    public static <T> VectorJsonDeserializer<T> newInstance(JsonDeserializer<T> deserializer) {
        return new VectorJsonDeserializer<T>(deserializer);
    }

    /**
     * @param deserializer {@link JsonDeserializer} used to deserialize the objects inside the {@link Vector}.
     */
    private VectorJsonDeserializer(JsonDeserializer<T> deserializer) {
        super(deserializer);
    }

    /** {@inheritDoc} */
    @Override
    protected Vector<T> newCollection() {
        return new Vector<T>();
    }
}
