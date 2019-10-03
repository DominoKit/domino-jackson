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

package org.dominokit.jacksonapt.utils;

import org.dominokit.jacksonapt.GwtIncompatible;
import org.dominokit.jacksonapt.JacksonContext;
import org.dominokit.jacksonapt.JsonSerializerParameters;
import org.dominokit.jacksonapt.deser.map.key.DateKeyParser;
import org.dominokit.jacksonapt.deser.map.key.DefaultDateKeyParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>DefaultDateFormat class.</p>
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
@GwtIncompatible
public final class DefaultDateFormat implements JacksonContext.DateFormat {

    /**
     * Defines a commonly used date format that conforms
     * to ISO-8601 date formatting standard, when it includes basic undecorated
     * timezone definition
     */
    public static final DateTimeFormatter DATE_FORMAT_STR_ISO8601 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    /**
     * Same as 'regular' 8601, but handles 'Z' as an alias for "+0000"
     * (or "GMT")
     */
    public final static DateTimeFormatter DATE_FORMAT_STR_ISO8601_Z = DateTimeFormatter.ISO_ZONED_DATE_TIME;

    /**
     * ISO-8601 with just the Date part, no time
     */
    public final static DateTimeFormatter DATE_FORMAT_STR_PLAIN = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * This constant defines the date format specified by
     * RFC 1123 / RFC 822.
     */
    public final static DateTimeFormatter DATE_FORMAT_STR_RFC1123 = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss zzz");

    /**
     * UTC TimeZone
     */
    public static final ZoneId UTC_TIMEZONE = ZoneOffset.UTC;

    private static final Map<String, DateParser> CACHE_PARSERS = new HashMap<String, DateParser>();

    /**
     * <p>Constructor for DefaultDateFormat.</p>
     */
    public DefaultDateFormat() {
    }

    /**
     * {@inheritDoc}
     *
     * Format a date using {@link #DATE_FORMAT_STR_ISO8601} and {@link #UTC_TIMEZONE}
     */
    public String format(Date date) {
        return format(DefaultDateFormat.DATE_FORMAT_STR_ISO8601, DefaultDateFormat.UTC_TIMEZONE, date);
    }

    /**
     * {@inheritDoc}
     *
     * Format a date using {@link JsonSerializerParameters} or default values : {@link #DATE_FORMAT_STR_ISO8601} and {@link #UTC_TIMEZONE}
     */
    public String format(JsonSerializerParameters params, Date date) {
        DateTimeFormatter format;
        if (null == params.getPattern()) {
            format = DefaultDateFormat.DATE_FORMAT_STR_ISO8601;
        } else {
            format = DateTimeFormatter.ofPattern(params.getPattern());
        }

        ZoneId timeZone;
        if (null == params.getTimezone()) {
            timeZone = DefaultDateFormat.UTC_TIMEZONE;
        } else {
            timeZone = (ZoneId) params.getTimezone();
        }

        return format(format, timeZone, date);
    }

    /**
     * {@inheritDoc}
     *
     * Format a date using the {@link DateTimeFormatter} given in parameter and {@link #UTC_TIMEZONE}.
     */
    public String format(DateTimeFormatter format, Date date) {
        return format(format, UTC_TIMEZONE, date);
    }

    /**
     * {@inheritDoc}
     *
     * Format a date using {@link #DATE_FORMAT_STR_ISO8601} and {@link ZoneId} given in parameter
     */
    public String format(ZoneId timeZone, Date date) {
        return DefaultDateFormat.DATE_FORMAT_STR_ISO8601.withZone(timeZone).format(date.toInstant());
    }

    /**
     * Format a date using the {@link java.time.format.DateTimeFormatter} and {@link java.time.ZoneId} given in
     * parameters
     *
     * @param format   format to use
     * @param timeZone timezone to use
     * @param date     date to format
     * @return the formatted date
     */
    public String format(DateTimeFormatter format, ZoneId timeZone, Date date) {
        if(date instanceof java.sql.Date || date instanceof java.sql.Time)
            return format.withZone(timeZone).format(new Date(date.getTime()).toInstant());
        return format.withZone(timeZone).format(date.toInstant());
    }

    /**
     * Parse a date using {@link #DATE_FORMAT_STR_ISO8601} and the browser timezone.
     *
     * @param date date to parse
     * @return the parsed date
     */
    public Date parse(String date) {
        return parse(DefaultDateFormat.DATE_FORMAT_STR_ISO8601, date);
    }

    /**
     * {@inheritDoc}
     *
     * Parse a date using the pattern given in parameter or {@link #DATE_FORMAT_STR_ISO8601} and the browser timezone.
     */
    public Date parse(boolean useBrowserTimezone, String pattern, Boolean hasTz, String date) {
        if (null == pattern) {
            try {
                return parse(DefaultDateFormat.DATE_FORMAT_STR_ISO8601, date);
            }catch (DateTimeParseException e){
                return parse(DefaultDateFormat.DATE_FORMAT_STR_ISO8601_Z, date);
            }
        } else {
            String patternCacheKey = pattern + useBrowserTimezone;
            DateParser parser = CACHE_PARSERS.get(patternCacheKey);
            if (null == parser) {
                boolean patternHasTz = useBrowserTimezone || (hasTz == null ? hasTz(pattern) : hasTz.booleanValue());
                if (patternHasTz) {
                    parser = new DateParser(pattern);
                } else {
                    // the pattern does not have a timezone, we use the UTC timezone as reference
                    parser = new DateParserNoTz(pattern);
                }
                CACHE_PARSERS.put(patternCacheKey, parser);
            }
            return parser.parse(date);
        }
    }

    /**
     * Find if a pattern contains informations about the timezone.
     *
     * @param pattern pattern
     * @return true if the pattern contains informations about the timezone, false otherwise
     */
    private boolean hasTz(String pattern) {
        boolean inQuote = false;

        for (int i = 0; i < pattern.length(); i++) {
            char ch = pattern.charAt(i);

            // If inside quote, except two quote connected, just copy or exit.
            if (inQuote) {
                if (ch == '\'') {
                    if (i + 1 < pattern.length() && pattern.charAt(i + 1) == '\'') {
                        // Quote appeared twice continuously, interpret as one quote.
                        ++i;
                    } else {
                        inQuote = false;
                    }
                }
                continue;
            }

            // Outside quote now.
            if ("Zzv".indexOf(ch) >= 0) {
                return true;
            }

            // Two consecutive quotes is a quote literal, inside or outside of quotes.
            if (ch == '\'') {
                if (i + 1 < pattern.length() && pattern.charAt(i + 1) == '\'') {
                    i++;
                } else {
                    inQuote = true;
                }
            }
        }

        return false;
    }

    /**
     * Parse a date using the {@link java.time.format.DateTimeFormatter} given in
     * parameter and the browser timezone.
     *
     * @param format format to use
     * @param date   date to parse
     * @return the parsed date
     */
    public Date parse(DateTimeFormatter format, String date) {
        return Date.from(Instant.from(format.parse(date)));
    }

    /**
     * Parse a date using the {@link java.time.format.DateTimeFormatter} given in
     * parameter and the browser timezone.
     *
     * @param format format to use
     * @param date   date to parse
     * @return the parsed date
     */
    public Date parse(SimpleDateFormat format, String date) {
        try {
            return format.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /** {@inheritDoc} */
    @Override
    public <D extends Date> DateKeyParser<D> makeDateKeyParser() {
        return new DefaultDateKeyParser<>();
    }

    private class DateParser {

        protected final SimpleDateFormat dateTimeFormat;

        protected DateParser(String pattern) {
            this.dateTimeFormat = new SimpleDateFormat(pattern);
        }

        protected Date parse(String date) {
            return DefaultDateFormat.this.parse(dateTimeFormat, date);
        }
    }

    private class DateParserNoTz extends DateParser {

        protected DateParserNoTz(String pattern) {
            super(pattern + " Z");
        }

        @Override
        protected Date parse(String date) {
            return super.parse(date + " +0000");
        }
    }
}
