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
package org.dominokit.jacksonapt.processor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dominokit.jacksonapt.ObjectMapper;
import org.dominokit.jacksonapt.annotation.JSONMapper;
import org.junit.Test;

public class BoxedTypesMapperTest {
  @JSONMapper
  interface BoxedVoidMapper extends ObjectMapper<Void> {}

  static BoxedVoidMapper BOXEDVOIDMAPPER = new BoxedTypesMapperTest_BoxedVoidMapperImpl();

  @JSONMapper
  interface BoxedIntegerMapper extends ObjectMapper<Integer> {}

  static BoxedIntegerMapper BOXEDINTEGERMAPPER = new BoxedTypesMapperTest_BoxedIntegerMapperImpl();

  @JSONMapper
  interface ListOfMapMapper
      extends ObjectMapper<List<Map<Integer, SimpleGenericBeanObject<Double>>>> {}

  static ListOfMapMapper LISTOFMAPOFSIMPLEGENERICBEANOBJECTMAPPER =
      new BoxedTypesMapperTest_ListOfMapMapperImpl();

  @Test
  public void testBoxedTypesMapper() {
    String jsonStr = BOXEDVOIDMAPPER.write(null);
    assertEquals(null, BOXEDVOIDMAPPER.read(jsonStr));

    jsonStr = BOXEDINTEGERMAPPER.write(new Integer(20));
    assertEquals(new Integer(20), BOXEDINTEGERMAPPER.read(jsonStr));

    Map<Integer, SimpleGenericBeanObject<Double>> map = new HashMap<>();
    map.put(new Integer(15), new SimpleGenericBeanObject<Double>(222, 18.1));

    List<Map<Integer, SimpleGenericBeanObject<Double>>> list = new ArrayList<>();
    list.add(map);

    jsonStr = LISTOFMAPOFSIMPLEGENERICBEANOBJECTMAPPER.write(list);

    List<Map<Integer, SimpleGenericBeanObject<Double>>> deserializedList =
        LISTOFMAPOFSIMPLEGENERICBEANOBJECTMAPPER.read(jsonStr);
    assertEquals(1, deserializedList.size());
    assertNotNull(deserializedList.get(0).get(15));
    assertEquals(222, deserializedList.get(0).get(15).intField);
    assertEquals(Double.valueOf(18.1), deserializedList.get(0).get(15).typeField);
  }
}
