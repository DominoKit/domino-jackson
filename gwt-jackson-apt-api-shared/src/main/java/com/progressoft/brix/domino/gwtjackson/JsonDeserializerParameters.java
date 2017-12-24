package com.progressoft.brix.domino.gwtjackson;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.progressoft.brix.domino.gwtjackson.deser.bean.IdentityDeserializationInfo;
import com.progressoft.brix.domino.gwtjackson.deser.bean.TypeDeserializationInfo;

import java.util.Set;

public interface JsonDeserializerParameters {
    String getPattern();

    JsonDeserializerParameters setPattern(String pattern);

    JsonFormat.Shape getShape();

    JsonDeserializerParameters setShape(JsonFormat.Shape shape);

    String getLocale();

    JsonDeserializerParameters setLocale(String locale);

    Set<String> getIgnoredProperties();

    JsonDeserializerParameters addIgnoredProperty(String ignoredProperty);

    boolean isIgnoreUnknown();

    JsonDeserializerParameters setIgnoreUnknown(boolean ignoreUnknown);

    IdentityDeserializationInfo getIdentityInfo();

    JsonDeserializerParameters setIdentityInfo(IdentityDeserializationInfo identityInfo);

    TypeDeserializationInfo getTypeInfo();

    JsonDeserializerParameters setTypeInfo(TypeDeserializationInfo typeInfo);
}
