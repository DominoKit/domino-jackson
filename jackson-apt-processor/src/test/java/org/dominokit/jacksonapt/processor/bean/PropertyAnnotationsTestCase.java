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
package org.dominokit.jacksonapt.processor.bean;

import org.junit.Assert;
import org.junit.Test;

public class PropertyAnnotationsTestCase {

  @Test
  public void testIgnoredPropertyShouldNotBeSerialized() {
    String json =
        TestBeanWithIgnore_MapperImpl.INSTANCE.write(
            new TestBeanWithIgnore(10, "ahmad", "Amman - Jordan", "propxValue", "propyValue"));
    Assert.assertEquals(
        "{\"name\":\"ahmad\",\"address\":\"Amman - Jordan\",\"propertyx\":\"propxValue\"}", json);
  }

  @Test
  public void testCustomPropertyNamesSerialization() {
    String json =
        TestBeanWithCustomPropertiesNames_MapperImpl.INSTANCE.write(
            new TestBeanWithCustomPropertiesNames(10, "ahmad", "Amman - Jordan"));
    Assert.assertEquals("{\"person-name\":\"ahmad\",\"location\":\"Amman - Jordan\"}", json);
  }

  @Test
  public void testIgnoredPropertyShouldNotBeDeserialized() {
    String json =
        "{\"name\":\"ahmad\",\"address\":\"Amman - Jordan\", \"propertyx\":\"propxValue\"}";
    TestBeanWithIgnore bean = TestBeanWithIgnore_MapperImpl.INSTANCE.read(json);
    Assert.assertNull(bean.getId());
    Assert.assertNull(bean.getPersonGender());
    Assert.assertNull(bean.getPropertyy());
    Assert.assertEquals("ahmad", bean.getName());
    Assert.assertEquals("Amman - Jordan", bean.getAddress());
    Assert.assertEquals("propxValue", bean.getPropertyx());

    String jsonContainsIgnoredProperty =
        "{\"id\":10,\"gender\":\"male\",\"name\":\"ahmad\",\"address\":\"Amman - Jordan\", \"propertyx\":\"propxValue\", \"propertyy\":\"propyValue\"}";
    TestBeanWithIgnore anotherBean =
        TestBeanWithIgnore_MapperImpl.INSTANCE.read(jsonContainsIgnoredProperty);
    Assert.assertNull(anotherBean.getId());
    Assert.assertNull(anotherBean.getPersonGender());
    Assert.assertNull(anotherBean.getPropertyy());
    Assert.assertEquals("ahmad", anotherBean.getName());
    Assert.assertEquals("Amman - Jordan", anotherBean.getAddress());
    Assert.assertEquals("propxValue", anotherBean.getPropertyx());
  }

  @Test
  public void testCustomPropertyNamesDeserialization() {
    String json = "{\"person-name\":\"ahmad\",\"location\":\"Amman - Jordan\"}";
    TestBeanWithCustomPropertiesNames bean =
        TestBeanWithCustomPropertiesNames_MapperImpl.INSTANCE.read(json);
    Assert.assertNull(bean.getId());
    Assert.assertEquals("ahmad", bean.getName());
    Assert.assertEquals("Amman - Jordan", bean.getAddress());

    String jsonContainsIgnoredProperty =
        "{\"ID\":10,\"person-name\":\"ahmad\",\"location\":\"Amman - Jordan\"}";
    TestBeanWithCustomPropertiesNames anotherBean =
        TestBeanWithCustomPropertiesNames_MapperImpl.INSTANCE.read(jsonContainsIgnoredProperty);
    Assert.assertNull(anotherBean.getId());
    Assert.assertEquals("ahmad", anotherBean.getName());
    Assert.assertEquals("Amman - Jordan", anotherBean.getAddress());
  }

  @Test
  public void testIgnoreUnknown() {
    String json = "{\"id\": 10,\"name\":\"Ahmad\",\"location\":\"Amman - Jordan\"}";
    Student bean = Student_MapperImpl.INSTANCE.read(json);
    Assert.assertEquals(10, bean.getId());
    Assert.assertEquals("Ahmad", bean.getName());
  }
}
