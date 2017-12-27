package com.progressoft.brix.domino.gwtjackson;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.progressoft.brix.domino.gwtjackson.exception.JsonSerializationException;
import com.progressoft.brix.domino.gwtjackson.ser.bean.ObjectIdSerializer;
import com.progressoft.brix.domino.gwtjackson.stream.JsonWriter;

public interface JsonSerializationContext extends JsonMappingContext {
    boolean isSerializeNulls();

    boolean isWriteDatesAsTimestamps();

    boolean isWriteDateKeysAsTimestamps();

    boolean isWrapRootValue();

    boolean isWriteCharArraysAsJsonArrays();

    boolean isWriteNullMapValues();

    boolean isWriteEmptyJsonArrays();

    boolean isOrderMapEntriesByKeys();

    boolean isWriteSingleElemArraysUnwrapped();

    JsonWriter newJsonWriter();

    JsonSerializationException traceError(Object value, String message);

    JsonSerializationException traceError(Object value, String message, JsonWriter writer);

    RuntimeException traceError(Object value, RuntimeException cause);

    RuntimeException traceError(Object value, RuntimeException cause, JsonWriter writer);

    void addObjectId(Object object, ObjectIdSerializer<?> id);

    ObjectIdSerializer<?> getObjectId(Object object);

    @SuppressWarnings("UnusedDeclaration")
    void addGenerator(ObjectIdGenerator<?> generator);

    @SuppressWarnings({"UnusedDeclaration", "unchecked"})
    <T> ObjectIdGenerator<T> findObjectIdGenerator(ObjectIdGenerator<T> gen);


    JsonSerializerParameters defaultParameters();
}
