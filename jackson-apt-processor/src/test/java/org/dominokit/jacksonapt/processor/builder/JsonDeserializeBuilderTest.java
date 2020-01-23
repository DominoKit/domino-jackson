package org.dominokit.jacksonapt.processor.builder;

import org.dominokit.jacksonapt.ObjectMapper;
import org.dominokit.jacksonapt.annotation.JSONMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class JsonDeserializeBuilderTest {

    @JSONMapper
    interface SimpleBeanMapper extends ObjectMapper<SimpleBean> {
        SimpleBeanMapper MAPPER = new JsonDeserializeBuilderTest_SimpleBeanMapperImpl();
    }

    @Test
    public void test_builder_deserialization() {
        SimpleBeanBuilder beanBuilder = new SimpleBeanBuilder();
        SimpleBean simpleBean = beanBuilder.setId("id")
                .setName("name")
                .build();

        String write = SimpleBeanMapper.MAPPER.write(simpleBean);

        SimpleBean result = SimpleBeanMapper.MAPPER.read(write);

        assertEquals(result, simpleBean);
    }

    @Test
    public void test_builder_deserialization_with_configs() {
        SimpleBeanWithConfigs_MapperImpl mapper = new SimpleBeanWithConfigs_MapperImpl();

        SimpleBeanWithConfigs result = mapper.read("{\"id\":\"id\",\"name\":\"name\",\"address\":\"address\"}");

        assertEquals("id", result.getId());
        assertEquals("name", result.getName());
        assertNull(result.getAddress());
    }
}
