package org.dominokit.jacksonapt;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import org.dominokit.jacksonapt.exception.JsonDeserializationException;
import org.dominokit.jacksonapt.stream.JsonReader;

/**
 * <p>JsonDeserializationContext interface.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public interface JsonDeserializationContext extends JsonMappingContext {
    /**
     * <p>isFailOnUnknownProperties.</p>
     *
     * @return a boolean.
     */
    boolean isFailOnUnknownProperties();

    /**
     * <p>isUnwrapRootValue.</p>
     *
     * @return a boolean.
     */
    boolean isUnwrapRootValue();

    /**
     * <p>isAcceptSingleValueAsArray.</p>
     *
     * @return a boolean.
     */
    boolean isAcceptSingleValueAsArray();

    /**
     * <p>isUseSafeEval.</p>
     *
     * @return a boolean.
     */
    boolean isUseSafeEval();

    /**
     * <p>isReadUnknownEnumValuesAsNull.</p>
     *
     * @return a boolean.
     */
    boolean isReadUnknownEnumValuesAsNull();

    /**
     * <p>isUseBrowserTimezone.</p>
     *
     * @return a boolean.
     */
    boolean isUseBrowserTimezone();

    /**
     * <p>newJsonReader.</p>
     *
     * @param input a {@link java.lang.String} object.
     * @return a {@link org.dominokit.jacksonapt.stream.JsonReader} object.
     */
    JsonReader newJsonReader(String input);

    /**
     * <p>traceError.</p>
     *
     * @param message a {@link java.lang.String} object.
     * @return a {@link org.dominokit.jacksonapt.exception.JsonDeserializationException} object.
     */
    JsonDeserializationException traceError(String message);

    /**
     * <p>traceError.</p>
     *
     * @param message a {@link java.lang.String} object.
     * @param reader a {@link org.dominokit.jacksonapt.stream.JsonReader} object.
     * @return a {@link org.dominokit.jacksonapt.exception.JsonDeserializationException} object.
     */
    JsonDeserializationException traceError(String message, JsonReader reader);

    /**
     * <p>traceError.</p>
     *
     * @param cause a {@link java.lang.RuntimeException} object.
     * @return a {@link java.lang.RuntimeException} object.
     */
    RuntimeException traceError(RuntimeException cause);

    /**
     * <p>traceError.</p>
     *
     * @param cause a {@link java.lang.RuntimeException} object.
     * @param reader a {@link org.dominokit.jacksonapt.stream.JsonReader} object.
     * @return a {@link java.lang.RuntimeException} object.
     */
    RuntimeException traceError(RuntimeException cause, JsonReader reader);

    /**
     * <p>addObjectId.</p>
     *
     * @param id a {@link com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey} object.
     * @param instance a {@link java.lang.Object} object.
     */
    void addObjectId(ObjectIdGenerator.IdKey id, Object instance);

    /**
     * <p>getObjectWithId.</p>
     *
     * @param id a {@link com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey} object.
     * @return a {@link java.lang.Object} object.
     */
    Object getObjectWithId(ObjectIdGenerator.IdKey id);

    /**
     * <p>defaultParameters.</p>
     *
     * @return a {@link org.dominokit.jacksonapt.JsonDeserializerParameters} object.
     */
    JsonDeserializerParameters defaultParameters();
}
