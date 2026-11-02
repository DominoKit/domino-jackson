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
package org.dominokit.jackson.processor.identity;

import org.dominokit.jackson.JsonSerializationContext;
import org.dominokit.jackson.JsonSerializer;
import org.dominokit.jackson.JsonSerializerParameters;
import org.dominokit.jackson.stream.JsonWriter;

/** Default generated-mapper stand-in used to make the pre-fix branch observable. */
public class IdentityTokenBeanJsonSerializerImpl extends JsonSerializer<IdentityToken> {
  private static final IdentityTokenBeanJsonSerializerImpl INSTANCE =
      new IdentityTokenBeanJsonSerializerImpl();

  public static IdentityTokenBeanJsonSerializerImpl getInstance() {
    return INSTANCE;
  }

  @Override
  protected void doSerialize(
      JsonWriter writer,
      IdentityToken value,
      JsonSerializationContext ctx,
      JsonSerializerParameters params) {
    writer.beginObject();
    writer.name("value");
    writer.value(value.getValue());
    writer.endObject();
  }
}
