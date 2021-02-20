/*
 * Copyright 2017 Nicolas Morel
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

package org.dominokit.jackson.server;

import org.dominokit.jackson.DefaultJsonDeserializationContext;
import org.dominokit.jackson.DefaultJsonSerializationContext;
import org.dominokit.jackson.JsonDeserializationContext;
import org.dominokit.jackson.JsonSerializationContext;

/** @author Nicolas Morel */
public abstract class ServerJacksonTestCase extends BaseJacksonTestCase {

  @Override
  protected JsonDeserializationContext newDefaultDeserializationContext() {
    return DefaultJsonDeserializationContext.builder().build();
  }

  @Override
  protected JsonSerializationContext newDefaultSerializationContext() {
    return DefaultJsonSerializationContext.builder().build();
  }
}
