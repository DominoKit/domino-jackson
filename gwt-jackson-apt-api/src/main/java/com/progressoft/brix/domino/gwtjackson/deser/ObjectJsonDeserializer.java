/*
 * Copyright 2015 Nicolas Morel
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

package com.progressoft.brix.domino.gwtjackson.deser;

import com.progressoft.brix.domino.gwtjackson.JsonDeserializationContext;
import com.progressoft.brix.domino.gwtjackson.JsonDeserializer;
import com.progressoft.brix.domino.gwtjackson.JsonDeserializerParameters;
import com.google.gwt.core.client.JavaScriptObject;
import com.progressoft.brix.domino.gwtjackson.stream.JsonReader;
import jsinterop.base.Js;

/**
 * Default {@link JsonDeserializer} implementation for {@link JavaScriptObject}.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class ObjectJsonDeserializer<T extends Object> extends JsonDeserializer<T> {

    private static final ObjectJsonDeserializer INSTANCE = new ObjectJsonDeserializer();

    /**
     * <p>getInstance</p>
     *
     * @return an instance of {@link ObjectJsonDeserializer}
     */
    public static ObjectJsonDeserializer getInstance() {
        return INSTANCE;
    }

    private ObjectJsonDeserializer() { }

    /** {@inheritDoc} */
    @Override
    public T doDeserialize(JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params ) {
        return Js.cast(reader.nextObject( ctx.isUseSafeEval() ));
    }
}
