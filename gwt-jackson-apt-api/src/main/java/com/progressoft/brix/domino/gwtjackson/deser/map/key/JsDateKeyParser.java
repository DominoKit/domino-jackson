package com.progressoft.brix.domino.gwtjackson.deser.map.key;

import com.progressoft.brix.domino.gwtjackson.exception.JsonDeserializationException;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;

import java.util.Date;

public class JsDateKeyParser<D extends Date> implements DateKeyParser<D> {

    private static final DateTimeFormat ISO_8601_FORMAT = DateTimeFormat.getFormat(PredefinedFormat.ISO_8601);

    private static final DateTimeFormat RFC_2822_FORMAT = DateTimeFormat.getFormat(PredefinedFormat.RFC_2822);

    @Override
    public D parse(String keyValue, DateDeserializer<D> deserializer) {
        try {
            return deserializer.deserializeDate(ISO_8601_FORMAT.parse(keyValue));
        } catch (IllegalArgumentException e) {
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
        } catch (IllegalArgumentException e) {
            // can happen if it's not the correct format
        }

        throw new JsonDeserializationException("Cannot parse the keyValue '" + keyValue + "' as a date");
    }
}
