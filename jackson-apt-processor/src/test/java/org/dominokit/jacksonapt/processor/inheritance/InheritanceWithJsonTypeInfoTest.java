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
package org.dominokit.jacksonapt.processor.inheritance;

import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;

public class InheritanceWithJsonTypeInfoTest {

  @Test
  public void serializerTest() {

    DataInput dataInput = new DataInput("1", "diPostfix", "diName", "diDType");
    DataOutput dataOutput = new DataOutput("2", "doPostfix", "doName");

    dataInput.setGenericField(new DataInput("1X", "diPostfixX", "diNameX", "diDTypeX"));

    DataList dataList = new DataList();
    ArrayList<Data<?>> ioSpecification = new ArrayList<>();
    ioSpecification.add(dataInput);
    ioSpecification.add(dataOutput);
    dataList.setIoSpecification(ioSpecification);

    String result = DataList_MapperImpl.INSTANCE.write(dataList);
    Assert.assertEquals(
        "{\"ioSpecification\":[{\"@type\":\"dataInput\",\"id\":\"1_diPostfix\",\"dtype\":\"diDType\",\"itemSubjectRef\":\"1_diPostfixItem\",\"name\":\"diName\",\"genericField\":{\"id\":\"1X_diPostfixX\",\"dtype\":\"diDTypeX\",\"itemSubjectRef\":\"1X_diPostfixXItem\",\"name\":\"diNameX\",\"genericField\":null}},{\"@type\":\"dataOutput\",\"id\":\"2_doPostfix\",\"dtype\":null,\"itemSubjectRef\":\"2_doPostfixItem\",\"name\":\"doName\",\"genericField\":null}]}",
        result);
  }

  @Test
  public void deserializerTest() {

    String json =
        "{\"ioSpecification\":[{\"@type\":\"dataInput\",\"id\":\"1_diPostfix\",\"dtype\":\"diDType\",\"itemSubjectRef\":\"1_diPostfixItem\",\"name\":\"diName\",\"genericField\":{\"id\":\"1X_diPostfixX\",\"dtype\":\"diDTypeX\",\"itemSubjectRef\":\"1X_diPostfixXItem\",\"name\":\"diNameX\",\"genericField\":null}},{\"@type\":\"dataOutput\",\"id\":\"2_doPostfix\",\"dtype\":null,\"itemSubjectRef\":\"2_doPostfixItem\",\"name\":\"doName\",\"genericField\":null}]}";
    DataList result = DataList_MapperImpl.INSTANCE.read(json);

    DataInput dataInput = new DataInput("1", "diPostfix", "diName", "diDType");
    DataOutput dataOutput = new DataOutput("2", "doPostfix", "doName");
    DataInput genericField = new DataInput("1X", "diPostfixX", "diNameX", "diDTypeX");

    Assert.assertEquals(result.getIoSpecification().size(), 2);
    Assert.assertTrue(result.getIoSpecification().get(0) instanceof DataInput);
    Assert.assertTrue(result.getIoSpecification().get(1) instanceof DataOutput);
    Assert.assertEquals(dataInput, result.getIoSpecification().get(0));
    Assert.assertEquals(genericField, result.getIoSpecification().get(0).getGenericField());
    Assert.assertEquals(dataOutput, result.getIoSpecification().get(1));
    Assert.assertNull(result.getIoSpecification().get(1).getGenericField());
  }
}
