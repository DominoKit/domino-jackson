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
package org.dominokit.jackson.processor;

import static com.google.common.truth.Truth.assertThat;

import java.util.Date;
import org.dominokit.jackson.processor.bean.*;
import org.junit.Test;

public class JsonCreatorTest {

  @Test
  public void simple_json_creator() {
    CreatorBean creatorBean = new CreatorBean(1, new Date());
    String json = CreatorBean_MapperImpl.INSTANCE.write(creatorBean);
    CreatorBean result = CreatorBean_MapperImpl.INSTANCE.read(json);

    assertThat(result).isEqualTo(creatorBean);
  }

  @Test
  public void composition_json_creator() {
    CreatorParent creatorParent = new CreatorParent(1, "parentName", new CreatorChild("childName"));
    String json = CreatorParent_MapperImpl.INSTANCE.write(creatorParent);
    CreatorParent result = CreatorParent_MapperImpl.INSTANCE.read(json);

    assertThat(result).isEqualTo(creatorParent);
  }
}
