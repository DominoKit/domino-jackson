package org.dominokit.jacksonapt;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import org.dominokit.jacksonapt.exception.JsonSerializationException;
import org.dominokit.jacksonapt.ser.bean.ObjectIdSerializer;
import org.dominokit.jacksonapt.stream.JsonWriter;

/**
 * <p>JsonSerializationContext interface.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public interface JsonSerializationContext extends JsonMappingContext {
    /**
     * <p>isSerializeNulls.</p>
     *
     * @return a boolean.
     */
    boolean isSerializeNulls();

    /**
     * <p>isWriteDatesAsTimestamps.</p>
     *
     * @return a boolean.
     */
    boolean isWriteDatesAsTimestamps();

    /**
     * <p>isWriteDateKeysAsTimestamps.</p>
     *
     * @return a boolean.
     */
    boolean isWriteDateKeysAsTimestamps();

    /**
     * <p>isWrapRootValue.</p>
     *
     * @return a boolean.
     */
    boolean isWrapRootValue();

    /**
     * <p>isWriteCharArraysAsJsonArrays.</p>
     *
     * @return a boolean.
     */
    boolean isWriteCharArraysAsJsonArrays();

    /**
     * <p>isWriteNullMapValues.</p>
     *
     * @return a boolean.
     */
    boolean isWriteNullMapValues();

    /**
     * <p>isWriteEmptyJsonArrays.</p>
     *
     * @return a boolean.
     */
    boolean isWriteEmptyJsonArrays();

    /**
     * <p>isOrderMapEntriesByKeys.</p>
     *
     * @return a boolean.
     */
    boolean isOrderMapEntriesByKeys();

    /**
     * <p>isWriteSingleElemArraysUnwrapped.</p>
     *
     * @return a boolean.
     */
    boolean isWriteSingleElemArraysUnwrapped();

    /**
     * <p>newJsonWriter.</p>
     *
     * @return a {@link org.dominokit.jacksonapt.stream.JsonWriter} object.
     */
    JsonWriter newJsonWriter();

    /**
     * <p>traceError.</p>
     *
     * @param value a {@link java.lang.Object} object.
     * @param message a {@link java.lang.String} object.
     * @return a {@link org.dominokit.jacksonapt.exception.JsonSerializationException} object.
     */
    JsonSerializationException traceError(Object value, String message);

    /**
     * <p>traceError.</p>
     *
     * @param value a {@link java.lang.Object} object.
     * @param message a {@link java.lang.String} object.
     * @param writer a {@link org.dominokit.jacksonapt.stream.JsonWriter} object.
     * @return a {@link org.dominokit.jacksonapt.exception.JsonSerializationException} object.
     */
    JsonSerializationException traceError(Object value, String message, JsonWriter writer);

    /**
     * <p>traceError.</p>
     *
     * @param value a {@link java.lang.Object} object.
     * @param cause a {@link java.lang.RuntimeException} object.
     * @return a {@link java.lang.RuntimeException} object.
     */
    RuntimeException traceError(Object value, RuntimeException cause);

    /**
     * <p>traceError.</p>
     *
     * @param value a {@link java.lang.Object} object.
     * @param cause a {@link java.lang.RuntimeException} object.
     * @param writer a {@link org.dominokit.jacksonapt.stream.JsonWriter} object.
     * @return a {@link java.lang.RuntimeException} object.
     */
    RuntimeException traceError(Object value, RuntimeException cause, JsonWriter writer);

    /**
     * <p>addObjectId.</p>
     *
     * @param object a {@link java.lang.Object} object.
     * @param id a {@link org.dominokit.jacksonapt.ser.bean.ObjectIdSerializer} object.
     */
    void addObjectId(Object object, ObjectIdSerializer<?> id);

    /**
     * <p>getObjectId.</p>
     *
     * @param object a {@link java.lang.Object} object.
     * @return a {@link org.dominokit.jacksonapt.ser.bean.ObjectIdSerializer} object.
     */
    ObjectIdSerializer<?> getObjectId(Object object);

    /**
     * <p>addGenerator.</p>
     *
     * @param generator a {@link com.fasterxml.jackson.annotation.ObjectIdGenerator} object.
     */
    @SuppressWarnings("UnusedDeclaration")
    void addGenerator(ObjectIdGenerator<?> generator);

    /**
     * <p>findObjectIdGenerator.</p>
     *
     * @param gen a {@link com.fasterxml.jackson.annotation.ObjectIdGenerator} object.
     * @param <T> a T object.
     * @return a {@link com.fasterxml.jackson.annotation.ObjectIdGenerator} object.
     */
    @SuppressWarnings({"UnusedDeclaration", "unchecked"})
    <T> ObjectIdGenerator<T> findObjectIdGenerator(ObjectIdGenerator<T> gen);


    /**
     * <p>defaultParameters.</p>
     *
     * @return a {@link org.dominokit.jacksonapt.JsonSerializerParameters} object.
     */
    JsonSerializerParameters defaultParameters();
}
