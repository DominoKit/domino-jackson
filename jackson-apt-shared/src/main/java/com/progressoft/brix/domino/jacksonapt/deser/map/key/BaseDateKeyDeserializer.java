/*
 * Copyright 2013 Nicolas Morel
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

package com.progressoft.brix.domino.jacksonapt.deser.map.key;

import com.progressoft.brix.domino.jacksonapt.JacksonContextProvider;
import com.progressoft.brix.domino.jacksonapt.JsonDeserializationContext;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Base implementation of {@link KeyDeserializer} for dates. It uses both ISO-8601 and RFC-2822 for string-based key and milliseconds
 * for number-based key.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public abstract class BaseDateKeyDeserializer<D extends Date> extends KeyDeserializer<D> implements DateDeserializer<D> {

    /**
     * Default implementation of {@link BaseDateKeyDeserializer} for {@link Date}
     */
    public static final class DateKeyDeserializer extends BaseDateKeyDeserializer<Date> {

        private static final DateKeyDeserializer INSTANCE = new DateKeyDeserializer();

        /**
         * @return an instance of {@link DateKeyDeserializer}
         */
        public static DateKeyDeserializer getInstance() {
            return INSTANCE;
        }

        private DateKeyDeserializer() {
        }

        @Override
        public Date deserializeMillis(long millis) {
            return new Date(millis);
        }

        @Override
        public Date deserializeDate(Date date) {
            return date;
        }
    }

    /**
     * Default implementation of {@link BaseDateKeyDeserializer} for {@link java.sql.Date}
     */
    public static final class SqlDateKeyDeserializer extends BaseDateKeyDeserializer<java.sql.Date> {

        private static final SqlDateKeyDeserializer INSTANCE = new SqlDateKeyDeserializer();

        /**
         * @return an instance of {@link SqlDateKeyDeserializer}
         */
        public static SqlDateKeyDeserializer getInstance() {
            return INSTANCE;
        }

        private SqlDateKeyDeserializer() {
        }

        @Override
        public java.sql.Date deserializeMillis(long millis) {
            return new java.sql.Date(millis);
        }

        @Override
        public java.sql.Date deserializeDate(Date date) {
            return deserializeMillis(date.getTime());
        }
    }

    /**
     * Default implementation of {@link BaseDateKeyDeserializer} for {@link Time}
     */
    public static final class SqlTimeKeyDeserializer extends BaseDateKeyDeserializer<Time> {

        private static final SqlTimeKeyDeserializer INSTANCE = new SqlTimeKeyDeserializer();

        /**
         * @return an instance of {@link SqlTimeKeyDeserializer}
         */
        public static SqlTimeKeyDeserializer getInstance() {
            return INSTANCE;
        }

        private SqlTimeKeyDeserializer() {
        }

        @Override
        public Time deserializeMillis(long millis) {
            return new Time(millis);
        }

        @Override
        public Time deserializeDate(Date date) {
            return deserializeMillis(date.getTime());
        }
    }

    /**
     * Default implementation of {@link BaseDateKeyDeserializer} for {@link Timestamp}
     */
    public static final class SqlTimestampKeyDeserializer extends BaseDateKeyDeserializer<Timestamp> {

        private static final SqlTimestampKeyDeserializer INSTANCE = new SqlTimestampKeyDeserializer();

        /**
         * @return an instance of {@link SqlTimestampKeyDeserializer}
         */
        public static SqlTimestampKeyDeserializer getInstance() {
            return INSTANCE;
        }

        private SqlTimestampKeyDeserializer() {
        }

        @Override
        public Timestamp deserializeMillis(long millis) {
            return new Timestamp(millis);
        }

        @Override
        public Timestamp deserializeDate(Date date) {
            return deserializeMillis(date.getTime());
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected D doDeserialize(String key, JsonDeserializationContext ctx) {
        return ((DateKeyParser<D>)JacksonContextProvider.get().dateFormat().makeDateKeyParser()).parse(key, this);
    }
}
