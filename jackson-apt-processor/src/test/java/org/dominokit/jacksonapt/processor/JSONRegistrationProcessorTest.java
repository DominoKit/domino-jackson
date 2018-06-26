package org.dominokit.jacksonapt.processor;

import org.dominokit.jacksonapt.ObjectMapper;
import org.dominokit.jacksonapt.ObjectReader;
import org.dominokit.jacksonapt.ObjectWriter;
import org.dominokit.jacksonapt.annotation.JSONMapper;
import org.dominokit.jacksonapt.annotation.JSONReader;
import org.dominokit.jacksonapt.annotation.JSONWriter;
import org.dominokit.jacksonapt.registration.JsonRegistry;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

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
        assertNotNull(testJsonRegistry.getMapper(Person.class));
        assertNotNull(testJsonRegistry.getReader(Person.class));
        assertNotNull(testJsonRegistry.getWriter(Person.class));
    }
}
