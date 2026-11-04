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
package org.dominokit.jackson.processor.subtype;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CollectionSubclassMapperTest {

  @Test
  public void plainSubclassesRoundTripAsJsonContainersOfTheSameClass() {
    PlainNameList names = new PlainNameList();
    names.add("a");
    names.add("b");
    PlainNameMap codes = new PlainNameMap();
    codes.put("k", "v");

    CollectionHolder bean = new CollectionHolder();
    bean.setNames(names);
    bean.setCodes(codes);
    bean.setCustomNames(new CustomNameList());
    bean.setCustomCodes(new CustomNameMap());

    String json = CollectionHolder_MapperImpl.INSTANCE.write(bean);
    CollectionHolder result = CollectionHolder_MapperImpl.INSTANCE.read(json);

    assertEquals(bean, result);
    assertEquals(PlainNameList.class, result.getNames().getClass());
    assertEquals(PlainNameMap.class, result.getCodes().getClass());
  }

  @Test
  public void customSubclassesUseCustomMappers() {
    CustomNameList names = new CustomNameList();
    names.add("a");
    names.add("b");
    CustomNameMap codes = new CustomNameMap();
    codes.put("k", "v");

    CollectionHolder bean = new CollectionHolder();
    bean.setCustomNames(names);
    bean.setCustomCodes(codes);

    String json = CollectionHolder_MapperImpl.INSTANCE.write(bean);

    assertEquals(
        "{\"names\":null,\"codes\":null,\"customNames\":\"names:a|b\",\"customCodes\":\"codes:k=v\"}",
        json);
    assertEquals(bean, CollectionHolder_MapperImpl.INSTANCE.read(json));
    assertEquals(
        CustomNameList.class,
        CollectionHolder_MapperImpl.INSTANCE.read(json).getCustomNames().getClass());
    assertEquals(
        CustomNameMap.class,
        CollectionHolder_MapperImpl.INSTANCE.read(json).getCustomCodes().getClass());
  }
}
