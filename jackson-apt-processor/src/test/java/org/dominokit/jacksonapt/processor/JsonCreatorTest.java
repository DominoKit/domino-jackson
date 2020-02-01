package org.dominokit.jacksonapt.processor;

import org.dominokit.jacksonapt.processor.bean.*;
import org.junit.Test;

import java.util.Date;

import static com.google.common.truth.Truth.assertThat;

public class JsonCreatorTest {

    @Test
    public void simple_json_creator() {
        CreatorBean creatorBean = new CreatorBean(1, new Date());
        String json = CreatorBean_MapperImpl.INSTANCE.write(creatorBean);
        CreatorBean result = CreatorBean_MapperImpl.INSTANCE.read(json);

        assertThat(result).isEqualTo(creatorBean);
    }

    @Test
    public void composition_json_creator() {
        CreatorParent creatorParent = new CreatorParent(1, "parentName", new CreatorChild("childName"));
        String json = CreatorParent_MapperImpl.INSTANCE.write(creatorParent);
        CreatorParent result = CreatorParent_MapperImpl.INSTANCE.read(json);

        assertThat(result).isEqualTo(creatorParent);
    }
}
