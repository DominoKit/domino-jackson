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
import com.progressoft.brix.domino.gwtjackson.deser.array.AbstractArrayJsonDeserializer;
import com.progressoft.brix.domino.gwtjackson.stream.JsonReader;
import com.progressoft.brix.domino.gwtjackson.stream.JsonToken;
import elemental2.core.JsArray;
import elemental2.core.JsString;
import jsinterop.base.Js;

/**
 * Default {@link JsonDeserializer} implementation for array of {@link String}.
 * <p>Not working in production mode, cast problem. Can maybe work with disableCastChecking</p>
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class StringArrayJsonDeserializer extends AbstractArrayJsonDeserializer<String[]> {

    private static final StringArrayJsonDeserializer INSTANCE = new StringArrayJsonDeserializer();

    /**
     * <p>getInstance</p>
     *
     * @return an instance of {@link StringArrayJsonDeserializer}
     */
    public static StringArrayJsonDeserializer getInstance() {
        return INSTANCE;
    }

    private static String[] reinterpretCast(JsArray<JsString> value) {
        JsString[] sliced = value.slice();
        return Js.uncheckedCast(sliced);
    }

    private StringArrayJsonDeserializer() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] doDeserializeArray(JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {
        JsArray<JsString> jsArray = new JsArray<>();
        reader.beginArray();
        while (JsonToken.END_ARRAY != reader.peek()) {
            if (JsonToken.NULL == reader.peek()) {
                reader.skipValue();
                jsArray.push(null);
            } else {
                jsArray.push((JsString) Js.cast(reader.nextString()));
            }
        }
        reader.endArray();

        return reinterpretCast(jsArray);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String[] doDeserializeSingleArray(JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params) {
        return new String[]{reader.nextString()};
    }
}
