package org.dominokit.jacksonapt;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.dominokit.jacksonapt.deser.bean.IdentityDeserializationInfo;
import org.dominokit.jacksonapt.deser.bean.TypeDeserializationInfo;

import java.util.Set;

/**
 * <p>JsonDeserializerParameters interface.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public interface JsonDeserializerParameters {
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
     * @return a {@link org.dominokit.jacksonapt.JsonDeserializerParameters} object.
     */
    JsonDeserializerParameters setPattern(String pattern);

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
     * @return a {@link org.dominokit.jacksonapt.JsonDeserializerParameters} object.
     */
    JsonDeserializerParameters setShape(JsonFormat.Shape shape);

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
     * @return a {@link org.dominokit.jacksonapt.JsonDeserializerParameters} object.
     */
    JsonDeserializerParameters setLocale(String locale);

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
     * @return a {@link org.dominokit.jacksonapt.JsonDeserializerParameters} object.
     */
    JsonDeserializerParameters addIgnoredProperty(String ignoredProperty);

    /**
     * <p>isIgnoreUnknown.</p>
     *
     * @return a boolean.
     */
    boolean isIgnoreUnknown();

    /**
     * <p>setIgnoreUnknown.</p>
     *
     * @param ignoreUnknown a boolean.
     * @return a {@link org.dominokit.jacksonapt.JsonDeserializerParameters} object.
     */
    JsonDeserializerParameters setIgnoreUnknown(boolean ignoreUnknown);

    /**
     * <p>getIdentityInfo.</p>
     *
     * @return a {@link org.dominokit.jacksonapt.deser.bean.IdentityDeserializationInfo} object.
     */
    IdentityDeserializationInfo getIdentityInfo();

    /**
     * <p>setIdentityInfo.</p>
     *
     * @param identityInfo a {@link org.dominokit.jacksonapt.deser.bean.IdentityDeserializationInfo} object.
     * @return a {@link org.dominokit.jacksonapt.JsonDeserializerParameters} object.
     */
    JsonDeserializerParameters setIdentityInfo(IdentityDeserializationInfo identityInfo);

    /**
     * <p>getTypeInfo.</p>
     *
     * @return a {@link org.dominokit.jacksonapt.deser.bean.TypeDeserializationInfo} object.
     */
    TypeDeserializationInfo getTypeInfo();

    /**
     * <p>setTypeInfo.</p>
     *
     * @param typeInfo a {@link org.dominokit.jacksonapt.deser.bean.TypeDeserializationInfo} object.
     * @return a {@link org.dominokit.jacksonapt.JsonDeserializerParameters} object.
     */
    JsonDeserializerParameters setTypeInfo(TypeDeserializationInfo typeInfo);
}
