package org.dominokit.jacksonapt.processor.bean;


import org.junit.Assert;
import org.junit.Test;

public class PropertyAnnotationsTestCase {

    @Test
    public void testIgnoredPropertyShouldNotBeSerialized(){
        String json = TestBeanWithIgnore_MapperImpl.INSTANCE.write(new TestBeanWithIgnore(10, "ahmad", "Amman - Jordan"));
        Assert.assertEquals("{\"name\":\"ahmad\",\"address\":\"Amman - Jordan\"}", json);
    }

    @Test
    public void testCustomPropertyNamesSerialization(){
        String json = TestBeanWithCustomPropertiesNames_MapperImpl.INSTANCE.write(new TestBeanWithCustomPropertiesNames(10, "ahmad", "Amman - Jordan"));
        Assert.assertEquals("{\"person-name\":\"ahmad\",\"location\":\"Amman - Jordan\"}", json);
    }

    @Test
    public void testIgnoredPropertyShouldNotBeDeserialized(){
        String json = "{\"name\":\"ahmad\",\"address\":\"Amman - Jordan\"}";
        TestBeanWithIgnore bean = TestBeanWithIgnore_MapperImpl.INSTANCE.read(json);
        Assert.assertNull(bean.getId());
        Assert.assertNull(bean.getPersonGender());
        Assert.assertEquals("ahmad", bean.getName());
        Assert.assertEquals("Amman - Jordan", bean.getAddress());


        String jsonContainsIgnoredProperty="{\"id\":10,\"gender\":\"male\",\"name\":\"ahmad\",\"address\":\"Amman - Jordan\"}";
        TestBeanWithIgnore anotherBean = TestBeanWithIgnore_MapperImpl.INSTANCE.read(jsonContainsIgnoredProperty);
        Assert.assertNull(anotherBean.getId());
        Assert.assertNull(anotherBean.getPersonGender());
        Assert.assertEquals("ahmad", anotherBean.getName());
        Assert.assertEquals("Amman - Jordan", anotherBean.getAddress());
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



}
