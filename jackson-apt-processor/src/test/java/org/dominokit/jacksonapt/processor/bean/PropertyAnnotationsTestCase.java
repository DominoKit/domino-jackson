package org.dominokit.jacksonapt.processor.bean;


import org.junit.Assert;
import org.junit.Test;

public class PropertyAnnotationsTestCase {

    @Test
    public void testIgnoredPropertyShouldNotBeSerialized(){
        String json = TestBeanWithIgnore_MapperImpl.INSTANCE.write(new TestBeanWithIgnore(10, "ahmad", "Amman - Jordan", "propxValue", "propyValue"));
        Assert.assertEquals("{\"name\":\"ahmad\",\"address\":\"Amman - Jordan\",\"propertyx\":\"propxValue\"}", json);
    }

    @Test
    public void testCustomPropertyNamesSerialization(){
        String json = TestBeanWithCustomPropertiesNames_MapperImpl.INSTANCE.write(new TestBeanWithCustomPropertiesNames(10, "ahmad", "Amman - Jordan"));
        Assert.assertEquals("{\"person-name\":\"ahmad\",\"location\":\"Amman - Jordan\"}", json);
    }

    @Test
    public void testIgnoredPropertyShouldNotBeDeserialized(){
        String json = "{\"name\":\"ahmad\",\"address\":\"Amman - Jordan\", \"propertyx\":\"propxValue\"}";
        TestBeanWithIgnore bean = TestBeanWithIgnore_MapperImpl.INSTANCE.read(json);
        Assert.assertNull(bean.getId());
        Assert.assertNull(bean.getPersonGender());
        Assert.assertNull(bean.getPropertyy());
        Assert.assertEquals("ahmad", bean.getName());
        Assert.assertEquals("Amman - Jordan", bean.getAddress());
        Assert.assertEquals("propxValue", bean.getPropertyx());


        String jsonContainsIgnoredProperty="{\"id\":10,\"gender\":\"male\",\"name\":\"ahmad\",\"address\":\"Amman - Jordan\", \"propertyx\":\"propxValue\", \"propertyy\":\"propyValue\"}";
        TestBeanWithIgnore anotherBean = TestBeanWithIgnore_MapperImpl.INSTANCE.read(jsonContainsIgnoredProperty);
        Assert.assertNull(anotherBean.getId());
        Assert.assertNull(anotherBean.getPersonGender());
        Assert.assertNull(anotherBean.getPropertyy());
        Assert.assertEquals("ahmad", anotherBean.getName());
        Assert.assertEquals("Amman - Jordan", anotherBean.getAddress());
        Assert.assertEquals("propxValue", anotherBean.getPropertyx());
    }


    @Test
    public void testCustomPropertyNamesDeserialization(){
        String json = "{\"person-name\":\"ahmad\",\"location\":\"Amman - Jordan\"}";
        TestBeanWithCustomPropertiesNames bean = TestBeanWithCustomPropertiesNames_MapperImpl.INSTANCE.read(json);
        Assert.assertNull(bean.getId());
        Assert.assertEquals("ahmad", bean.getName());
        Assert.assertEquals("Amman - Jordan", bean.getAddress());


        String jsonContainsIgnoredProperty="{\"ID\":10,\"person-name\":\"ahmad\",\"location\":\"Amman - Jordan\"}";
        TestBeanWithCustomPropertiesNames anotherBean = TestBeanWithCustomPropertiesNames_MapperImpl.INSTANCE.read(jsonContainsIgnoredProperty);
        Assert.assertNull(anotherBean.getId());
        Assert.assertEquals("ahmad", anotherBean.getName());
        Assert.assertEquals("Amman - Jordan", anotherBean.getAddress());
    }

    @Test
    public void testIgnoreUnknown(){
        String json = "{\"id\": 10,\"name\":\"Ahmad\",\"location\":\"Amman - Jordan\"}";
        Student bean = Student_MapperImpl.INSTANCE.read(json);
        Assert.assertEquals(10, bean.getId());
        Assert.assertEquals("Ahmad", bean.getName());
    }

}
