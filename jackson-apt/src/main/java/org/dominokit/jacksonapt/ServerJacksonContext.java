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

import org.dominokit.jacksonapt.deser.array.cast.DefaultDoubleArrayReader;
import org.dominokit.jacksonapt.deser.array.cast.DefaultIntegerArrayReader;
import org.dominokit.jacksonapt.deser.array.cast.DefaultShortArrayReader;
import org.dominokit.jacksonapt.deser.array.cast.DefaultStringArrayReader;
import org.dominokit.jacksonapt.deser.bean.DefaultMapLike;
import org.dominokit.jacksonapt.stream.impl.DefaultIntegerStack;
import org.dominokit.jacksonapt.utils.DefaultDateFormat;

/**
 * ServerJacksonContext class.
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public class ServerJacksonContext extends JsJacksonContext {

  @GwtIncompatible
  private static final ValueStringifier VALUE_STRINGIFIER = new ServerValueStringifier();

  /** {@inheritDoc} */
  @GwtIncompatible
  @Override
  public DateFormat dateFormat() {
    return new DefaultDateFormat();
  }

  /** {@inheritDoc} */
  @GwtIncompatible
  @Override
  public IntegerStackFactory integerStackFactory() {
    return DefaultIntegerStack::new;
  }

  /** {@inheritDoc} */
  @GwtIncompatible
  @Override
  public MapLikeFactory mapLikeFactory() {
    return DefaultMapLike::new;
  }

  /** {@inheritDoc} */
  @GwtIncompatible
  @Override
  public ValueStringifier stringifier() {
    return VALUE_STRINGIFIER;
  }

  /** {@inheritDoc} */
  @GwtIncompatible
  @Override
  public StringArrayReader stringArrayReader() {
    return new DefaultStringArrayReader();
  }

  /** {@inheritDoc} */
  @GwtIncompatible
  @Override
  public ShortArrayReader shortArrayReader() {
    return new DefaultShortArrayReader();
  }

  /** {@inheritDoc} */
  @GwtIncompatible
  @Override
  public IntegerArrayReader integerArrayReader() {
    return new DefaultIntegerArrayReader();
  }

  /** {@inheritDoc} */
  @GwtIncompatible
  @Override
  public DoubleArrayReader doubleArrayReader() {
    return new DefaultDoubleArrayReader();
  }

  /** {@inheritDoc} */
  @GwtIncompatible
  @Override
  public JsonSerializerParameters defaultSerializerParameters() {
    return ServerJacksonJsonSerializerParameters.DEFAULT;
  }

  /** {@inheritDoc} */
  @GwtIncompatible
  @Override
  public JsonDeserializerParameters defaultDeserializerParameters() {
    return ServerJacksonJsonDeserializerParameters.DEFAULT;
  }

  /** {@inheritDoc} */
  @GwtIncompatible
  @Override
  public JsonSerializerParameters newSerializerParameters() {
    return new ServerJacksonJsonSerializerParameters();
  }

  /** {@inheritDoc} */
  @GwtIncompatible
  @Override
  public JsonDeserializerParameters newDeserializerParameters() {
    return new ServerJacksonJsonDeserializerParameters();
  }
}
