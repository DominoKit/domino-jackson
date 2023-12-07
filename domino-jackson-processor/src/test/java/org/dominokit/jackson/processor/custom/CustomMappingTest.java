/*
 * Copyright © 2019 Dominokit
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
package org.dominokit.jackson.processor.custom;

import org.junit.Assert;
import org.junit.Test;

public class CustomMappingTest {

  @Test
  public void testCustomMapping() {
    Employee customBean = new Employee();
    customBean.setId(10);
    customBean.setName("custom");
    customBean.setTitle("custom title");

    Person person = new Person();
    person.setId(5);
    person.setName("Jackson");
    person.setTitle("Bean");

    TestBean testBean = new TestBean();
    testBean.setId(1);
    testBean.setName("test bean");
    testBean.setBean(customBean);
    testBean.setPerson(person);

    String result =
        "{\"id\":1,\"name\":\"test bean\",\"bean\":\"10,custom,custom title\",\"person\":\"5,Jackson,Bean\"}";
    Assert.assertEquals(result, TestBean_MapperImpl.INSTANCE.write(testBean));
    Assert.assertEquals(testBean, TestBean_MapperImpl.INSTANCE.read(result));
  }
}
