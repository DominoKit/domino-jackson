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
package org.dominokit.jacksonapt;

import org.dominokit.jacksonapt.deser.array.cast.JsDoubleArrayReader;
import org.dominokit.jacksonapt.deser.array.cast.JsIntegerArrayReader;
import org.dominokit.jacksonapt.deser.array.cast.JsShortArrayReader;
import org.dominokit.jacksonapt.deser.array.cast.JsStringArrayReader;
import org.dominokit.jacksonapt.deser.bean.JsMapLike;
import org.dominokit.jacksonapt.stream.impl.JsIntegerStack;
import org.dominokit.jacksonapt.utils.JsDateFormat;

/** An implementation of {@link JacksonContext} that works in the browser */
public class JsJacksonContext implements JacksonContext {
  /** {@inheritDoc} */
  @Override
  public DateFormat dateFormat() {
    return new JsDateFormat();
  }

  /** {@inheritDoc} */
  @Override
  public IntegerStackFactory integerStackFactory() {
    return JsIntegerStack::new;
  }

  /** {@inheritDoc} */
  @Override
  public MapLikeFactory mapLikeFactory() {
    return JsMapLike::new;
  }

  /** {@inheritDoc} */
  @Override
  public ValueStringifier stringifier() {
    return JSON::stringify;
  }

  /** {@inheritDoc} */
  @Override
  public StringArrayReader stringArrayReader() {
    return new JsStringArrayReader();
  }

  /** {@inheritDoc} */
  @Override
  public ShortArrayReader shortArrayReader() {
    return new JsShortArrayReader();
  }

  /** {@inheritDoc} */
  @Override
  public IntegerArrayReader integerArrayReader() {
    return new JsIntegerArrayReader();
  }

  /** {@inheritDoc} */
  @Override
  public DoubleArrayReader doubleArrayReader() {
    return new JsDoubleArrayReader();
  }

  /** {@inheritDoc} */
  @Override
  public JsonSerializerParameters defaultSerializerParameters() {
    return GwtJacksonJsonSerializerParameters.DEFAULT;
  }

  /** {@inheritDoc} */
  @Override
  public JsonDeserializerParameters defaultDeserializerParameters() {
    return GwtJacksonJsonDeserializerParameters.DEFAULT;
  }

  /** {@inheritDoc} */
  @Override
  public JsonSerializerParameters newSerializerParameters() {
    return new GwtJacksonJsonSerializerParameters(defaultSerializerParameters());
  }

  /** {@inheritDoc} */
  @Override
  public JsonDeserializerParameters newDeserializerParameters() {
    return new GwtJacksonJsonDeserializerParameters(defaultDeserializerParameters());
  }
}
