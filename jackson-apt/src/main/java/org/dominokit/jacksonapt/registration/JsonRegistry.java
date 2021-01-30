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
package org.dominokit.jacksonapt.registration;

import org.dominokit.jacksonapt.ObjectMapper;
import org.dominokit.jacksonapt.ObjectReader;
import org.dominokit.jacksonapt.ObjectWriter;

/**
 * JsonRegistry interface.
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public interface JsonRegistry {

  /**
   * getMapper.
   *
   * @param type a {@link java.lang.Class} object.
   * @param <T> a T object.
   * @return a {@link org.dominokit.jacksonapt.ObjectMapper} object.
   */
  <T> ObjectMapper<T> getMapper(TypeToken<T> type);

  /**
   * getReader.
   *
   * @param type a {@link java.lang.Class} object.
   * @param <T> a T object.
   * @return a {@link org.dominokit.jacksonapt.ObjectReader} object.
   */
  <T> ObjectReader<T> getReader(TypeToken<T> type);

  /**
   * getWriter.
   *
   * @param type a {@link java.lang.Class} object.
   * @param <T> a T object.
   * @return a {@link org.dominokit.jacksonapt.ObjectWriter} object.
   */
  <T> ObjectWriter<T> getWriter(TypeToken<T> type);
}
