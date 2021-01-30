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
package org.dominokit.jacksonapt.deser.map.key;

import java.util.Date;

/**
 * DateDeserializer interface.
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public interface DateDeserializer<D extends Date> {

  /**
   * deserializeMillis
   *
   * @param millis a long.
   * @return a D object.
   */
  D deserializeMillis(long millis);

  /**
   * deserializeDate
   *
   * @param date a {@link java.util.Date} object.
   * @return a D object.
   */
  D deserializeDate(Date date);
}
