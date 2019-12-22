package org.dominokit.jacksonapt.processor;

import org.dominokit.jacksonapt.processor.bean.FieldJsonIncludeSample;
import org.dominokit.jacksonapt.processor.bean.FieldJsonIncludeSample_MapperImpl;
import org.dominokit.jacksonapt.processor.bean.TypeJsonIncludeSample;
import org.dominokit.jacksonapt.processor.bean.TypeJsonIncludeSample_MapperImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JsonIncludeTest {

    @Test
    public void type_json_include() {
        TypeJsonIncludeSample sample = new TypeJsonIncludeSample();
        sample.setId("idValue");
        sample.setName("nameValue");
        String json = TypeJsonIncludeSample_MapperImpl.INSTANCE.write(sample);

        assertEquals("{\"id\":\"idValue\",\"name\":\"nameValue\"}", json);

        TypeJsonIncludeSample sample1 = new TypeJsonIncludeSample();
        sample1.setId(null);
        sample1.setName("nameValue");
        String json1 = TypeJsonIncludeSample_MapperImpl.INSTANCE.write(sample1);

        assertEquals("{\"name\":\"nameValue\"}", json1);
    }

    @Test
    public void field_json_include() {
        FieldJsonIncludeSample sample = new FieldJsonIncludeSample();
        sample.setName("nameValue");
        String json = FieldJsonIncludeSample_MapperImpl.INSTANCE.write(sample);

        assertEquals("{\"name\":\"nameValue\"}", json);

        FieldJsonIncludeSample sample1 = new FieldJsonIncludeSample();
        sample1.setName(null);
        String json1 = FieldJsonIncludeSample_MapperImpl.INSTANCE.write(sample1);

        assertEquals("{\"name\":null}", json1);
    }
}
