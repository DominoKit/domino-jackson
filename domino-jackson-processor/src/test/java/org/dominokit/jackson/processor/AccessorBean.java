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

import org.dominokit.jackson.annotation.JSONMapper;

/**
 * To make sure some wierd accessors case works
 *
 * @see https://github.com/DominoKit/domino-jackson/issues/66
 */
@JSONMapper
public class AccessorBean {
  private int xY;

  public int getxY() {
    return xY;
  }

  public void setxY(int xY) {
    this.xY = xY;
  }
}
