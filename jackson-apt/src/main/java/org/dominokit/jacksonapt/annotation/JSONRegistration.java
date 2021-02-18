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
package org.dominokit.jacksonapt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * This will mark a package as a target to generate an implementation of {@link
 * org.dominokit.jacksonapt.registration.JsonRegistry} the generated implementation will list all
 * mappers, Readers, Writers and will provide methods to get those classes at runtime by providing a
 * {@link org.dominokit.jacksonapt.registration.TypeToken}
 */
@Target(ElementType.PACKAGE)
public @interface JSONRegistration {

  /**
   * The name prefix to be used for the json registry class generated from this annotation, the
   * final class name will use the name returned from this method and appends <b>JsonRegistry</b> to
   * it.
   *
   * @return {@link java.lang.String}
   */
  String value();
}
