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

import java.util.Objects;
import org.dominokit.jackson.annotation.JSONMapper;

@JSONMapper
public class CollectionHolder {

  private PlainNameList names;
  private PlainNameMap codes;
  private CustomNameList customNames;
  private CustomNameMap customCodes;

  public PlainNameList getNames() {
    return names;
  }

  public void setNames(PlainNameList names) {
    this.names = names;
  }

  public PlainNameMap getCodes() {
    return codes;
  }

  public void setCodes(PlainNameMap codes) {
    this.codes = codes;
  }

  public CustomNameList getCustomNames() {
    return customNames;
  }

  public void setCustomNames(CustomNameList customNames) {
    this.customNames = customNames;
  }

  public CustomNameMap getCustomCodes() {
    return customCodes;
  }

  public void setCustomCodes(CustomNameMap customCodes) {
    this.customCodes = customCodes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CollectionHolder that = (CollectionHolder) o;
    return Objects.equals(names, that.names)
        && Objects.equals(codes, that.codes)
        && Objects.equals(customNames, that.customNames)
        && Objects.equals(customCodes, that.customCodes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(names, codes, customNames, customCodes);
  }
}
