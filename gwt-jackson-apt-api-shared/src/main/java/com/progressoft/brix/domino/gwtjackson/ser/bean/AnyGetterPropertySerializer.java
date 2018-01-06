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

import com.progressoft.brix.domino.gwtjackson.JsonSerializationContext;
import com.progressoft.brix.domino.gwtjackson.ser.map.MapJsonSerializer;
import com.progressoft.brix.domino.gwtjackson.stream.JsonWriter;

import java.util.Map;

/**
 * Serializes a bean's property
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public abstract class AnyGetterPropertySerializer<T> extends BeanPropertySerializer<T, Map> {

    /**
     * <p>newSerializer</p>
     *
     * @return a {@link com.progressoft.brix.domino.gwtjackson.ser.map.MapJsonSerializer} object.
     */
    protected abstract MapJsonSerializer newSerializer();

    /**
     * <p>Constructor for AnyGetterPropertySerializer.</p>
     */
    public AnyGetterPropertySerializer() {
        super(null);
    }

    /**
     * {@inheritDoc}
     */
    public void serializePropertyName(JsonWriter writer, T bean, JsonSerializationContext ctx) {
        // no-op
    }

    /**
     * {@inheritDoc}
     * <p>
     * Serializes the property defined for this instance.
     */
    public void serialize(JsonWriter writer, T bean, JsonSerializationContext ctx) {
        Map map = getValue(bean, ctx);
        if (null != map) {
            ((MapJsonSerializer) getSerializer()).serializeValues(writer, map, ctx, getParameters());
        }
    }
}
