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

import java.lang.annotation.*;

/**
 * This annotation is used to mark a class or a method as GWT incompatible, which will result in
 * stripping out the code annotated during GWT/J2CL compilation
 */
@Retention(RetentionPolicy.CLASS)
@Target({
  ElementType.TYPE, ElementType.METHOD,
  ElementType.CONSTRUCTOR, ElementType.FIELD
})
@Documented
public @interface GwtIncompatible {
  String value() default "";
}
