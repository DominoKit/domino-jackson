/*
 * Copyright 2014 Nicolas Morel
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

package org.dominokit.jacksonapt.ser.bean;

import java.util.Map;
import org.dominokit.jacksonapt.JsonSerializationContext;
import org.dominokit.jacksonapt.ser.map.MapJsonSerializer;
import org.dominokit.jacksonapt.stream.JsonWriter;

/**
 * Serializes a bean's property
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public abstract class AnyGetterPropertySerializer<T> extends BeanPropertySerializer<T, Map> {

  /**
   * newSerializer
   *
   * @return a {@link org.dominokit.jacksonapt.ser.map.MapJsonSerializer} object.
   */
  protected abstract MapJsonSerializer newSerializer();

  /** Constructor for AnyGetterPropertySerializer. */
  public AnyGetterPropertySerializer() {
    super(null);
  }

  /** {@inheritDoc} */
  public void serializePropertyName(JsonWriter writer, T bean, JsonSerializationContext ctx) {
    // no-op
  }

  /**
   * {@inheritDoc}
   *
   * <p>Serializes the property defined for this instance.
   */
  public void serialize(JsonWriter writer, T bean, JsonSerializationContext ctx) {
    Map map = getValue(bean, ctx);
    if (null != map) {
      ((MapJsonSerializer) getSerializer()).serializeValues(writer, map, ctx, getParameters());
    }
  }
}
