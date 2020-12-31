/*
 * Copyright 2013 Nicolas Morel
 * Copyright 2020 Stanislav Spiridonov
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

package org.dominokit.jacksonapt.client.deser.array;

import org.dominokit.jacksonapt.JsonDeserializer;
import org.dominokit.jacksonapt.client.deser.AbstractJsonDeserializerTest;
import org.dominokit.jacksonapt.deser.array.cast.PrimitiveDoubleArrayJsonDeserializer;

/**
 * @author Stanislav Spiridonov
 */
@SuppressWarnings("nls")
public class DoubleArrayJsonDeserializerTest extends AbstractJsonDeserializerTest<double[]> {

	private static final double DELTA = 0.00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001;

    @Override
    protected JsonDeserializer<double[]> createDeserializer() {
        return PrimitiveDoubleArrayJsonDeserializer.getInstance();
    }

	@Override
    public void testDeserializeValue() {
		double[] orig = new double[] { -100, -0.5365623643346, 0.1, 536562364, 100.1 };
		double[] deserialize = deserialize("[-100.0,-0.5365623643346,0.1,536562364,100.1]");
        assertEquals(orig[0], deserialize[0], DELTA);
        assertEquals(orig[1], deserialize[1], DELTA);
        assertEquals(orig[2], deserialize[2], DELTA);
        assertEquals(orig[3], deserialize[3], DELTA);
        assertEquals(orig[4], deserialize[4], DELTA);
    }

    public void testDeserializeValueEmptyArray() {
        double[] deserialize = deserialize("[]");
        assertTrue(deserialize.length == 0);
    }
}
