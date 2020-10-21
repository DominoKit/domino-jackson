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

package org.dominokit.jacksonapt.processor.inner;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Dmitrii Tikhomirov
 * Created by treblereel 10/20/20
 */
public class MyBeansTest {

    private final MyBean1_Bean_MapperImpl mapper = new MyBean1_Bean_MapperImpl();
    private final MyBean2_Bean_MapperImpl mapper2 = new MyBean2_Bean_MapperImpl();
    private final MyBean3_MapperImpl mapper3 = new MyBean3_MapperImpl();


    @Test
    public void test() {
        MyBean1.Bean tested1 = new MyBean1.Bean();
        tested1.setMyBean1Value("setMyBean1Value");

        MyBean2.Bean tested2 = new MyBean2.Bean();
        tested2.setMyBean2Value("setMyBean2Value");

        assertEquals(tested1, mapper.read(mapper.write(tested1)));
        assertEquals(tested2, mapper2.read(mapper2.write(tested2)));

        MyBean3 tested = new MyBean3();
        tested.setBean1(tested1);
        tested.setBean2(tested2);

        assertEquals(tested, mapper3.read(mapper3.write(tested)));

    }

}
