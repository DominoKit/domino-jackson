package com.progressoft.brix.domino.gwtjackson;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gwt.i18n.client.TimeZone;
import com.progressoft.brix.domino.gwtjackson.ser.bean.IdentitySerializationInfo;
import com.progressoft.brix.domino.gwtjackson.ser.bean.TypeSerializationInfo;

import java.util.Set;

public interface JsonSerializerParameters {
    String getPattern();

    JsonSerializerParameters setPattern(String pattern);

    JsonFormat.Shape getShape();

    JsonSerializerParameters setShape(JsonFormat.Shape shape);

    String getLocale();

    JsonSerializerParameters setLocale(String locale);

    TimeZone getTimezone();

    JsonSerializerParameters setTimezone(TimeZone timezone);

    Set<String> getIgnoredProperties();

    JsonSerializerParameters addIgnoredProperty(String ignoredProperty);

    JsonInclude.Include getInclude();

    JsonSerializerParameters setInclude(JsonInclude.Include include);

    IdentitySerializationInfo getIdentityInfo();

    JsonSerializerParameters setIdentityInfo(IdentitySerializationInfo identityInfo);

    TypeSerializationInfo getTypeInfo();

    JsonSerializerParameters setTypeInfo(TypeSerializationInfo typeInfo);

    boolean isUnwrapped();

    JsonSerializerParameters setUnwrapped(boolean unwrapped);
}
