/*
 * Copyright 2020 Ahmad Bawaneh
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

package org.dominokit.jacksonapt.processor.pkg;

import java.util.ArrayList;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SimpleBeanTest {

    private final Tested_MapperImpl mapper = new Tested_MapperImpl();
    private static final String JSON = "{\"beans\":[{\"@type\":\"import\",\"name\":\"BBB\"}],\"anImport\":{\"value\":\"AAA\"}}";

    @Test
    public void test() {
        Tested tested = new Tested();
        tested.setBeans(new ArrayList<>());

        tested.setAnImport(new Import("AAA"));
        tested.getBeans().add(new org.dominokit.jacksonapt.processor.pkg.one.Import("BBB"));

        assertEquals(JSON, mapper.write(tested));
    }

}
