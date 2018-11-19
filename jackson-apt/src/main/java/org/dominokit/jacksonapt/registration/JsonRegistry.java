package org.dominokit.jacksonapt.registration;

import org.dominokit.jacksonapt.ObjectMapper;
import org.dominokit.jacksonapt.ObjectReader;
import org.dominokit.jacksonapt.ObjectWriter;

/**
 * <p>JsonRegistry interface.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public interface JsonRegistry {

    /**
     * <p>getMapper.</p>
     *
     * @param type a {@link java.lang.Class} object.
     * @param <T> a T object.
     * @return a {@link org.dominokit.jacksonapt.ObjectMapper} object.
     */
    <T> ObjectMapper<T> getMapper(Type type);

    /**
     * <p>getReader.</p>
     *
     * @param type a {@link java.lang.Class} object.
     * @param <T> a T object.
     * @return a {@link org.dominokit.jacksonapt.ObjectReader} object.
     */
    <T> ObjectReader<T> getReader(Type type);

    /**
     * <p>getWriter.</p>
     *
     * @param type a {@link java.lang.Class} object.
     * @param <T> a T object.
     * @return a {@link org.dominokit.jacksonapt.ObjectWriter} object.
     */
    <T> ObjectWriter<T> getWriter(Type type);
}
