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
package org.dominokit.jackson;

import org.dominokit.jackson.stream.impl.DefaultJsonWriter;

/**
 * An implementation of {@link JacksonContext.ValueStringifier} that works in the JVM, this class
 * will be stripped out during GWT/J2CL compilation To see the browser implementation please check
 * {@link JsJacksonContext#stringifier()}
 */
@GwtIncompatible
public class ServerValueStringifier implements JacksonContext.ValueStringifier {
  /** {@inheritDoc} */
  @Override
  public String stringify(String value) {
    StringBuilder out = new StringBuilder();
    out.append("\"");
    DefaultJsonWriter.encodeString(value, out);
    out.append("\"");
    return out.toString();
  }
}
