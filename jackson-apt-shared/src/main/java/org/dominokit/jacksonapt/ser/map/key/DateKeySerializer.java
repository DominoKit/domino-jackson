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

package org.dominokit.jacksonapt.ser.map.key;

import org.dominokit.jacksonapt.JacksonContextProvider;
import org.dominokit.jacksonapt.JsonSerializationContext;

import java.util.Date;

/**
 * Default implementation of {@link KeySerializer} for dates.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class DateKeySerializer<D extends Date> extends KeySerializer<D> {

    private static final DateKeySerializer INSTANCE = new DateKeySerializer();

    /**
     * <p>getInstance</p>
     *
     * @return an instance of {@link DateKeySerializer}
     */
    public static DateKeySerializer getInstance() {
        return INSTANCE;
    }

    private DateKeySerializer() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean mustBeEscaped(JsonSerializationContext ctx) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String doSerialize(Date value, JsonSerializationContext ctx) {
        if (ctx.isWriteDateKeysAsTimestamps()) {
            return Long.toString(value.getTime());
        } else {
            return JacksonContextProvider.get().dateFormat().format(value);
        }
    }
}
