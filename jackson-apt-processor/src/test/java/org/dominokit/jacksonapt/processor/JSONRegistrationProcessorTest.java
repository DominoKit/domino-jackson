package org.dominokit.jacksonapt.processor;

import org.dominokit.jacksonapt.ObjectMapper;
import org.dominokit.jacksonapt.ObjectReader;
import org.dominokit.jacksonapt.ObjectWriter;
import org.dominokit.jacksonapt.annotation.JSONMapper;
import org.dominokit.jacksonapt.annotation.JSONReader;
import org.dominokit.jacksonapt.annotation.JSONWriter;
import org.dominokit.jacksonapt.registration.JsonRegistry;
import org.dominokit.jacksonapt.registration.Type;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;

public class JSONRegistrationProcessorTest {

    @JSONMapper
    public interface PersonMapper extends ObjectMapper<Person> {
    }

    @JSONReader
    public interface PersonReader extends ObjectReader<Person> {
    }

    @JSONWriter
    public interface PersonWriter extends ObjectWriter<Person> {
    }

    @Test
    public void whenCompile_thenShouldRegisterMappersReadersAndWritersInTheirOwnRegistry() {
        JsonRegistry testJsonRegistry = new TestJsonRegistry();
        assertNotNull(testJsonRegistry.getMapper(Type.of(Person.class)));
        assertNotNull(testJsonRegistry.getReader(Type.of(Person.class)));
        assertNotNull(testJsonRegistry.getWriter(Type.of(Person.class)));
        assertNotNull(testJsonRegistry.getMapper(Type.of(List.class).typeParam(Type.of(Map.class).typeParam(Type.of(String.class)).typeParam(Type.of(SimpleBeanObject.class)))));
    }
}
