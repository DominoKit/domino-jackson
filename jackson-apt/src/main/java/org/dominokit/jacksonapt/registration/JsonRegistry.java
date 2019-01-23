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
    <T> ObjectMapper<T> getMapper(Class<T> type);

    /**
     * <p>getReader.</p>
     *
     * @param type a {@link java.lang.Class} object.
     * @param <T> a T object.
     * @return a {@link org.dominokit.jacksonapt.ObjectReader} object.
     */
    <T> ObjectReader<? extends T> getReader(Class<? extends T> type);

    /**
     * <p>getWriter.</p>
     *
     * @param type a {@link java.lang.Class} object.
     * @param <T> a T object.
     * @return a {@link org.dominokit.jacksonapt.ObjectWriter} object.
     */
    <T> ObjectWriter<? super T> getWriter(Class<? super T> type);
}
