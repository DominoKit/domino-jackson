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
package org.dominokit.jacksonapt.processor.identity;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class IdentityInfoTest {

  @Test(expected = StackOverflowError.class)
  public void testNonGenerator() {
    NonGeneratorBean nonGeneratorBean = new NonGeneratorBean();
    nonGeneratorBean.setId(1);
    nonGeneratorBean.setName("name1");

    NoneChild noneChild = new NoneChild();
    noneChild.setId(2);
    noneChild.setCycle(nonGeneratorBean);

    nonGeneratorBean.setNoneChild(noneChild);

    NonGeneratorBean_MapperImpl.INSTANCE.write(nonGeneratorBean);
  }

  @Test
  public void testIntegerSequenceGenerator() {
    IntegerSequenceGeneratorBean bean = new IntegerSequenceGeneratorBean();
    bean.setId(1);
    bean.setName("name1");

    IntegerSequenceChild child = new IntegerSequenceChild();
    child.setId(2);
    child.setCycle(bean);

    bean.setChild(child);
    String json = IntegerSequenceGeneratorBean_MapperImpl.INSTANCE.write(bean);
    IntegerSequenceGeneratorBean parsed =
        IntegerSequenceGeneratorBean_MapperImpl.INSTANCE.read(json);

    assertThat(json)
        .isEqualTo("{\"@id\":1,\"id\":1,\"name\":\"name1\",\"child\":{\"id\":2,\"cycle\":1}}");
    assertThat(parsed.getId()).isEqualTo(1);
    assertThat(parsed.getName()).isEqualTo("name1");
    assertThat(parsed.getChild().getId()).isEqualTo(2);
    assertThat(parsed.getChild().getCycle()).isEqualTo(parsed);
  }

  @Test
  public void testUUIDGenerator() {
    UUIDGeneratorBean bean = new UUIDGeneratorBean();
    bean.setId(1);
    bean.setName("name1");

    UUIDChild child = new UUIDChild();
    child.setId(2);
    child.setCycle(bean);

    bean.setChild(child);
    String json = UUIDGeneratorBean_MapperImpl.INSTANCE.write(bean);
    UUIDGeneratorBean parsed = UUIDGeneratorBean_MapperImpl.INSTANCE.read(json);

    assertThat(parsed.getId()).isEqualTo(1);
    assertThat(parsed.getName()).isEqualTo("name1");
    assertThat(parsed.getChild().getId()).isEqualTo(2);
    assertThat(parsed.getChild().getCycle()).isEqualTo(parsed);
  }

  @Test
  public void testStringIdGenerator() {
    StringIdGeneratorBean bean = new StringIdGeneratorBean();
    bean.setId(1);
    bean.setName("name1");

    StringIdChild child = new StringIdChild();
    child.setId(2);
    child.setCycle(bean);

    bean.setChild(child);
    String json = StringIdGeneratorBean_MapperImpl.INSTANCE.write(bean);
    StringIdGeneratorBean parsed = StringIdGeneratorBean_MapperImpl.INSTANCE.read(json);

    assertThat(parsed.getId()).isEqualTo(1);
    assertThat(parsed.getName()).isEqualTo("name1");
    assertThat(parsed.getChild().getId()).isEqualTo(2);
    assertThat(parsed.getChild().getCycle()).isEqualTo(parsed);
  }

  @Test
  public void testPropertyIdGenerator() {
    PropertyIdGeneratorBean bean = new PropertyIdGeneratorBean();
    bean.setId(20);
    bean.setName("name1");

    PropertyIdChild child = new PropertyIdChild();
    child.setId(2);
    child.setCycle(bean);

    bean.setChild(child);
    String json = PropertyIdGeneratorBean_MapperImpl.INSTANCE.write(bean);
    PropertyIdGeneratorBean parsed = PropertyIdGeneratorBean_MapperImpl.INSTANCE.read(json);

    assertThat(json).isEqualTo("{\"id\":20,\"name\":\"name1\",\"child\":{\"id\":2,\"cycle\":20}}");
    assertThat(parsed.getId()).isEqualTo(20);
    assertThat(parsed.getName()).isEqualTo("name1");
    assertThat(parsed.getChild().getId()).isEqualTo(2);
    assertThat(parsed.getChild().getCycle()).isEqualTo(parsed);
  }
}
