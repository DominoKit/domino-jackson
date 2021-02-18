/*
 * Copyright 2017 Nicolas Morel
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

package org.dominokit.jacksonapt.server;

import java.util.Date;
import org.dominokit.jacksonapt.*;
import org.dominokit.jacksonapt.shared.AbstractTester;
import org.dominokit.jacksonapt.shared.ObjectMapperTester;
import org.dominokit.jacksonapt.shared.ObjectReaderTester;
import org.dominokit.jacksonapt.shared.ObjectWriterTester;

/** @author Nicolas Morel */
public abstract class BaseJacksonTestCase {

  @SuppressWarnings("deprecation")
  protected long getUTCTime(
      int year, int month, int day, int hour, int minute, int second, int milli) {
    return AbstractTester.getUTCTime(year, month, day, hour, minute, second, milli);
  }

  protected Date getUTCDate(
      int year, int month, int day, int hour, int minute, int second, int milli) {
    return AbstractTester.getUTCDate(year, month, day, hour, minute, second, milli);
  }

  protected <T> ObjectMapperTester<T> createMapper(final ObjectMapper<T> mapper) {
    return createMapper(
        mapper, newDefaultDeserializationContext(), newDefaultSerializationContext());
  }

  protected <T> ObjectMapperTester<T> createMapper(
      final ObjectMapper<T> mapper,
      final JsonDeserializationContext deserCtx,
      final JsonSerializationContext serCtx) {
    return new ObjectMapperTester<T>() {
      @Override
      public T read(String input) {
        return mapper.read(input, deserCtx);
      }

      @Override
      public String write(T input) {
        return mapper.write(input, serCtx);
      }
    };
  }

  protected <T> ObjectReaderTester<T> createReader(final ObjectReader<T> reader) {
    return createReader(reader, newDefaultDeserializationContext());
  }

  protected <T> ObjectReaderTester<T> createReader(
      final ObjectReader<T> reader, final JsonDeserializationContext ctx) {
    return input -> reader.read(input, ctx);
  }

  protected <T> ObjectWriterTester<T> createWriter(final ObjectWriter<T> writer) {
    return createWriter(writer, newDefaultSerializationContext());
  }

  protected <T> ObjectWriterTester<T> createWriter(
      final ObjectWriter<T> writer, final JsonSerializationContext serCtx) {
    return input -> writer.write(input, serCtx);
  }

  protected abstract JsonDeserializationContext newDefaultDeserializationContext();

  protected abstract JsonSerializationContext newDefaultSerializationContext();
}
