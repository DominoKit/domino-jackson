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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import org.dominokit.jacksonapt.GwtIncompatible;
import org.dominokit.jacksonapt.exception.JsonDeserializationException;

/** DefaultDateKeyParser class. */
@GwtIncompatible
public class DefaultDateKeyParser<D extends Date> implements DateKeyParser<D> {

  private static final DateTimeFormatter ISO_8601_FORMAT = DateTimeFormatter.ISO_DATE_TIME;
  private static final SimpleDateFormat RFC_2822_FORMAT =
      new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");

  /** {@inheritDoc} */
  @Override
  public D parse(String keyValue, DateDeserializer<D> deserializer) {
    try {
      return deserializer.deserializeDate(Date.from(Instant.from(ISO_8601_FORMAT.parse(keyValue))));
    } catch (IllegalArgumentException | DateTimeParseException e) {
      // can happen if it's not the correct format
    }

    // maybe it's in milliseconds
    try {
      return deserializer.deserializeMillis(Long.parseLong(keyValue));
    } catch (NumberFormatException e) {
      // can happen if the keyValue is string-based like an ISO-8601 format
    }

    // or in RFC-2822
    try {
      return deserializer.deserializeDate(RFC_2822_FORMAT.parse(keyValue));
    } catch (IllegalArgumentException | ParseException e) {
      // can happen if it's not the correct format
    }

    throw new JsonDeserializationException(
        "Cannot parse the keyValue '" + keyValue + "' as a date");
  }
}
