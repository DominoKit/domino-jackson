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
package org.dominokit.jacksonapt.processor.pkg;

import java.util.List;
import org.dominokit.jacksonapt.annotation.JSONMapper;
import org.dominokit.jacksonapt.processor.pkg.one.ExtensionElement;

@JSONMapper
public class Tested {

  private List<ExtensionElement> beans;

  private Import anImport;

  public List<ExtensionElement> getBeans() {
    return beans;
  }

  public void setBeans(List<ExtensionElement> beans) {
    this.beans = beans;
  }

  public Import getAnImport() {
    return anImport;
  }

  public void setAnImport(Import anImport) {
    this.anImport = anImport;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Tested)) {
      return false;
    }

    Tested tested = (Tested) o;

    if (getBeans() != null ? !getBeans().equals(tested.getBeans()) : tested.getBeans() != null) {
      return false;
    }
    return getAnImport() != null
        ? getAnImport().equals(tested.getAnImport())
        : tested.getAnImport() == null;
  }

  @Override
  public int hashCode() {
    int result = getBeans() != null ? getBeans().hashCode() : 0;
    result = 31 * result + (getAnImport() != null ? getAnImport().hashCode() : 0);
    return result;
  }
}
