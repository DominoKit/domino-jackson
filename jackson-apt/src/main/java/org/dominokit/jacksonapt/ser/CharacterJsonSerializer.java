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

package org.dominokit.jacksonapt.ser;

import org.dominokit.jacksonapt.JsonSerializationContext;
import org.dominokit.jacksonapt.JsonSerializer;
import org.dominokit.jacksonapt.JsonSerializerParameters;
import org.dominokit.jacksonapt.stream.JsonWriter;

/**
 * Default {@link org.dominokit.jacksonapt.JsonSerializer} implementation for {@link java.lang.Character}.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class CharacterJsonSerializer extends JsonSerializer<Character> {

    private static final CharacterJsonSerializer INSTANCE = new CharacterJsonSerializer();

    /**
     * <p>getInstance</p>
     *
     * @return an instance of {@link org.dominokit.jacksonapt.ser.CharacterJsonSerializer}
     */
    public static CharacterJsonSerializer getInstance() {
        return INSTANCE;
    }

    private static char defaultValue;

    private CharacterJsonSerializer() {
    }

    /** {@inheritDoc} */
    @Override
    protected boolean isDefault(Character value) {
        return null == value || value == defaultValue;
    }

    /** {@inheritDoc} */
    @Override
    public void doSerialize(JsonWriter writer, Character value, JsonSerializationContext ctx, JsonSerializerParameters params) {
        writer.value(value.toString());
    }
}
