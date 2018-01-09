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

package com.progressoft.brix.domino.jacksonapt.deser.bean;

import com.progressoft.brix.domino.jacksonapt.JsonDeserializerParameters;
import com.progressoft.brix.domino.jacksonapt.JsonDeserializationContext;
import com.progressoft.brix.domino.jacksonapt.stream.JsonReader;

import java.util.Map;

/**
 * <p>InstanceBuilder interface.</p>
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public interface InstanceBuilder<T> {

    /**
     * <p>newInstance</p>
     *
     * @param reader                   a {@link com.progressoft.brix.domino.jacksonapt.stream.JsonReader} object.
     * @param ctx                      a {@link JsonDeserializationContext} object.
     * @param params                   a {@link JsonDeserializerParameters} object.
     * @param bufferedProperties       a {@link Map} object.
     * @param bufferedPropertiesValues a {@link Map} object.
     * @param bufferedPropertiesValues a {@link Map} object.
     * @return a {@link com.progressoft.brix.domino.jacksonapt.deser.bean.Instance} object.
     */
    Instance<T> newInstance(JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params,
                            Map<String, String> bufferedProperties,
                            Map<String, Object> bufferedPropertiesValues);

    /**
     * <p>getParametersDeserializer</p>
     *
     * @return a {@link MapLike} object.
     */
    MapLike<HasDeserializerAndParameters> getParametersDeserializer();

}
