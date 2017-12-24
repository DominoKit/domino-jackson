/*
 * Copyright 2014 Nicolas Morel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.progressoft.brix.domino.gwtjackson;

import java.util.Arrays;

/**
 * @author Nicolas Morel
 */
public final class PropertyNamingTester extends AbstractTester {

    public static final PropertyNamingTester INSTANCE = new PropertyNamingTester();
    private final String jsonString = "{\"HTML\":[\"html\"],\"HTMLParser\":\"htmlparser\",\"simpleName\":\"simple\"}";

    private PropertyNamingTester() {

    }

    public void testValue( ObjectMapperTester<PropertyNamingBean> mapper ) {

        PropertyNamingBean bean = new PropertyNamingBean();
        bean.setSimpleName( "simple" );
        bean.setHTML( Arrays.asList("html") );
        bean.setHTMLParser( "htmlparser" );

        String json = mapper.write( bean );
        assertEquals(jsonString, json );

        bean = mapper.read( json );
        assertEquals( "simple", bean.getSimpleName() );
        assertEquals( Arrays.asList("html"), bean.getHTML() );
        assertEquals( "htmlparser", bean.getHTMLParser() );
    }

    public void testRead( ObjectMapperTester<PropertyNamingBean> mapper ) {

        PropertyNamingBean bean = mapper.read( jsonString );

        assertEquals(bean.getHTMLParser(), "htmlparser");
        assertEquals(bean.getSimpleName(), "simple");
        assertNotNull(bean.getHTML());
        assertEquals(bean.getHTML().size(), 1);
        assertTrue(bean.getHTML().contains("html"));

    }
}
