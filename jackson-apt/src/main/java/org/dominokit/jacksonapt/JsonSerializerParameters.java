package org.dominokit.jacksonapt;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.dominokit.jacksonapt.ser.bean.IdentitySerializationInfo;
import org.dominokit.jacksonapt.ser.bean.TypeSerializationInfo;

import java.util.Set;

/**
 * <p>JsonSerializerParameters interface.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public interface JsonSerializerParameters {
    /**
     * <p>getPattern.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getPattern();

    /**
     * <p>setPattern.</p>
     *
     * @param pattern a {@link java.lang.String} object.
     * @return a {@link org.dominokit.jacksonapt.JsonSerializerParameters} object.
     */
    JsonSerializerParameters setPattern(String pattern);

    /**
     * <p>getShape.</p>
     *
     * @return a {@link com.fasterxml.jackson.annotation.JsonFormat.Shape} object.
     */
    JsonFormat.Shape getShape();

    /**
     * <p>setShape.</p>
     *
     * @param shape a {@link com.fasterxml.jackson.annotation.JsonFormat.Shape} object.
     * @return a {@link org.dominokit.jacksonapt.JsonSerializerParameters} object.
     */
    JsonSerializerParameters setShape(JsonFormat.Shape shape);

    /**
     * <p>getLocale.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getLocale();

    /**
     * <p>setLocale.</p>
     *
     * @param locale a {@link java.lang.String} object.
     * @return a {@link org.dominokit.jacksonapt.JsonSerializerParameters} object.
     */
    JsonSerializerParameters setLocale(String locale);

    /**
     * <p>getTimezone.</p>
     *
     * @return a {@link java.lang.Object} object.
     */
    Object getTimezone();

    /**
     * <p>setTimezone.</p>
     *
     * @param timezone a {@link java.lang.Object} object.
     * @return a {@link org.dominokit.jacksonapt.JsonSerializerParameters} object.
     */
    JsonSerializerParameters setTimezone(Object timezone);

    /**
     * <p>getIgnoredProperties.</p>
     *
     * @return a {@link java.util.Set} object.
     */
    Set<String> getIgnoredProperties();

    /**
     * <p>addIgnoredProperty.</p>
     *
     * @param ignoredProperty a {@link java.lang.String} object.
     * @return a {@link org.dominokit.jacksonapt.JsonSerializerParameters} object.
     */
    JsonSerializerParameters addIgnoredProperty(String ignoredProperty);

    /**
     * <p>getInclude.</p>
     *
     * @return a {@link com.fasterxml.jackson.annotation.JsonInclude.Include} object.
     */
    JsonInclude.Include getInclude();

    /**
     * <p>setInclude.</p>
     *
     * @param include a {@link com.fasterxml.jackson.annotation.JsonInclude.Include} object.
     * @return a {@link org.dominokit.jacksonapt.JsonSerializerParameters} object.
     */
    JsonSerializerParameters setInclude(JsonInclude.Include include);

    /**
     * <p>getIdentityInfo.</p>
     *
     * @return a {@link org.dominokit.jacksonapt.ser.bean.IdentitySerializationInfo} object.
     */
    IdentitySerializationInfo getIdentityInfo();

    /**
     * <p>setIdentityInfo.</p>
     *
     * @param identityInfo a {@link org.dominokit.jacksonapt.ser.bean.IdentitySerializationInfo} object.
     * @return a {@link org.dominokit.jacksonapt.JsonSerializerParameters} object.
     */
    JsonSerializerParameters setIdentityInfo(IdentitySerializationInfo identityInfo);

    /**
     * <p>getTypeInfo.</p>
     *
     * @return a {@link org.dominokit.jacksonapt.ser.bean.TypeSerializationInfo} object.
     */
    TypeSerializationInfo getTypeInfo();

    /**
     * <p>setTypeInfo.</p>
     *
     * @param typeInfo a {@link org.dominokit.jacksonapt.ser.bean.TypeSerializationInfo} object.
     * @return a {@link org.dominokit.jacksonapt.JsonSerializerParameters} object.
     */
    JsonSerializerParameters setTypeInfo(TypeSerializationInfo typeInfo);

    /**
     * <p>isUnwrapped.</p>
     *
     * @return a boolean.
     */
    boolean isUnwrapped();

    /**
     * <p>setUnwrapped.</p>
     *
     * @param unwrapped a boolean.
     * @return a {@link org.dominokit.jacksonapt.JsonSerializerParameters} object.
     */
    JsonSerializerParameters setUnwrapped(boolean unwrapped);
}
