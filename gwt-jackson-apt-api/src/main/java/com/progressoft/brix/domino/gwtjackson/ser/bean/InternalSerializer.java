/*
 * Copyright 2014 Nicolas Morel
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

import com.progressoft.brix.domino.gwtjackson.JsonSerializerParameters;
import com.progressoft.brix.domino.gwtjackson.JsonSerializationContext;
import com.progressoft.brix.domino.gwtjackson.stream.JsonWriter;

/**
 * Interface hiding the actual implementation doing the bean serialization.
 *
 * @author Nicolas Morel.
 */
interface InternalSerializer<T> {

    /**
     * <p>serializeInternally</p>
     *
     * @param writer a {@link com.progressoft.brix.domino.gwtjackson.stream.JsonWriter} object.
     * @param value a T object.
     * @param ctx a {@link JsonSerializationContext} object.
     * @param params a {@link JsonSerializerParameters} object.
     * @param defaultIdentityInfo a {@link com.progressoft.brix.domino.gwtjackson.ser.bean.IdentitySerializationInfo} object.
     * @param defaultTypeInfo a {@link com.progressoft.brix.domino.gwtjackson.ser.bean.TypeSerializationInfo} object.
     */
    void serializeInternally(JsonWriter writer, T value, JsonSerializationContext ctx, JsonSerializerParameters params,
                             IdentitySerializationInfo<T> defaultIdentityInfo, TypeSerializationInfo<T> defaultTypeInfo);

}

