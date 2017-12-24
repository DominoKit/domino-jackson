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

package com.progressoft.brix.domino.gwtjackson.deser.array.cast;

import com.progressoft.brix.domino.gwtjackson.JsonDeserializerParameters;
import com.progressoft.brix.domino.gwtjackson.JsonDeserializationContext;
import com.progressoft.brix.domino.gwtjackson.JsonDeserializer;
import com.progressoft.brix.domino.gwtjackson.deser.BaseNumberJsonDeserializer.IntegerJsonDeserializer;
import com.progressoft.brix.domino.gwtjackson.deser.array.AbstractArrayJsonDeserializer;
import com.progressoft.brix.domino.gwtjackson.stream.JsonReader;
import com.progressoft.brix.domino.gwtjackson.stream.JsonToken;
import elemental2.core.JsArray;
import elemental2.core.JsNumber;
import jsinterop.base.Js;

/**
 * Default {@link JsonDeserializer} implementation for array of int.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class PrimitiveIntegerArrayJsonDeserializer extends AbstractArrayJsonDeserializer<int[]> {

    private static final PrimitiveIntegerArrayJsonDeserializer INSTANCE = new PrimitiveIntegerArrayJsonDeserializer();

    /**
     * <p>getInstance</p>
     *
     * @return an instance of {@link PrimitiveIntegerArrayJsonDeserializer}
     */
    public static PrimitiveIntegerArrayJsonDeserializer getInstance() {
        return INSTANCE;
    }

    private static int[] reinterpretCast(JsArray<JsNumber> value) {
        JsNumber[] sliced = value.slice();
        return Js.uncheckedCast(sliced);
    }

    ;

    private PrimitiveIntegerArrayJsonDeserializer() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int[] doDeserializeArray(JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {
        JsArray<JsNumber> jsArray = new JsArray<>();
        reader.beginArray();
        while (JsonToken.END_ARRAY != reader.peek()) {
            if (JsonToken.NULL == reader.peek()) {
                reader.skipValue();
                jsArray.push(null);
            } else {
                jsArray.push((JsNumber) Js.cast(reader.nextInt()));
            }
        }
        reader.endArray();

        return reinterpretCast(jsArray);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected int[] doDeserializeSingleArray(JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {
        return new int[]{IntegerJsonDeserializer.getInstance().deserialize(reader, ctx, params)};
    }
}
