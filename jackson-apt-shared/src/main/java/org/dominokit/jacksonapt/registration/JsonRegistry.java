package org.dominokit.jacksonapt.registration;

import org.dominokit.jacksonapt.ObjectMapper;
import org.dominokit.jacksonapt.ObjectReader;
import org.dominokit.jacksonapt.ObjectWriter;

public interface JsonRegistry {

    <T> ObjectMapper<T> getMapper(Class<T> type);

    <T> ObjectReader<T> getReader(Class<T> type);

    <T> ObjectWriter<T> getWriter(Class<T> type);
}
