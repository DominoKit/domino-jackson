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
import org.dominokit.jacksonapt.deser.array.cast.PrimitiveIntegerArrayJsonDeserializer;

/**
 * @author Stanislav Spiridonov
 */
@SuppressWarnings("nls")
public class IntegerArrayJsonDeserializerTest extends AbstractJsonDeserializerTest<int[]> {

	@Override
	protected JsonDeserializer<int[]> createDeserializer() {
		return PrimitiveIntegerArrayJsonDeserializer.getInstance();
	}

	@Override
	public void testDeserializeValue() {
		int[] orig = new int[] { -100, -536562364, 0, 536562364, 100 };
		int[] deserialize = deserialize("[-100, -536562364, 0, 536562364, 100]");
		assertEquals(orig[0], deserialize[0]);
		assertEquals(orig[1], deserialize[1]);
		assertEquals(orig[2], deserialize[2]);
		assertEquals(orig[3], deserialize[3]);
		assertEquals(orig[4], deserialize[4]);
	}

	public void testDeserializeValueEmptyArray() {
		int[] deserialize = deserialize("[]");
		assertTrue(deserialize.length == 0);
	}
}
