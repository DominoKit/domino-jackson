package org.dominokit.jacksonapt.deser.map.key;

import org.dominokit.jacksonapt.exception.JsonDeserializationException;
import org.gwtproject.i18n.shared.DateTimeFormat;

import java.util.Date;

import static org.gwtproject.i18n.shared.DateTimeFormat.PredefinedFormat;
import static org.gwtproject.i18n.shared.DateTimeFormat.getFormat;

/**
 * <p>JsDateKeyParser class.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public class JsDateKeyParser<D extends Date> implements DateKeyParser<D> {

    private static final DateTimeFormat ISO_8601_FORMAT = getFormat(PredefinedFormat.ISO_8601);

    private static final DateTimeFormat RFC_2822_FORMAT = getFormat(PredefinedFormat.RFC_2822);

    /** {@inheritDoc} */
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
