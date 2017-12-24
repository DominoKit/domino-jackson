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

package com.progressoft.brix.domino.gwtjackson.deser.bean;

import com.progressoft.brix.domino.gwtjackson.JsonDeserializerParameters;
import com.progressoft.brix.domino.gwtjackson.JsonDeserializationContext;
import com.progressoft.brix.domino.gwtjackson.deser.BaseNumberJsonDeserializer.NumberJsonDeserializer;
import com.progressoft.brix.domino.gwtjackson.deser.BooleanJsonDeserializer;
import com.progressoft.brix.domino.gwtjackson.deser.StringJsonDeserializer;
import com.progressoft.brix.domino.gwtjackson.deser.collection.ArrayListJsonDeserializer;
import com.progressoft.brix.domino.gwtjackson.deser.map.LinkedHashMapJsonDeserializer;
import com.progressoft.brix.domino.gwtjackson.deser.map.key.StringKeyDeserializer;
import com.progressoft.brix.domino.gwtjackson.stream.JsonReader;

/**
 * <p>Abstract AbstractObjectBeanJsonDeserializer class.</p>
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
@SuppressWarnings("UnusedDeclaration")
public abstract class AbstractObjectBeanJsonDeserializer extends AbstractBeanJsonDeserializer<Object> {

    private ArrayListJsonDeserializer<Object> listJsonDeserializer;

    private LinkedHashMapJsonDeserializer<String, Object> mapJsonDeserializer;

    /** {@inheritDoc} */
    @Override
    protected boolean canDeserialize() {
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public Object deserializeWrapped(JsonReader reader, JsonDeserializationContext ctx, JsonDeserializerParameters params,
                                     IdentityDeserializationInfo identityInfo, TypeDeserializationInfo typeInfo, String typeInformation ) {
        switch ( reader.peek() ) {
            case NUMBER:
                return NumberJsonDeserializer.getInstance().doDeserialize( reader, ctx, params );
            case STRING:
                return StringJsonDeserializer.getInstance().doDeserialize( reader, ctx, params );
            case BOOLEAN:
                return BooleanJsonDeserializer.getInstance().doDeserialize( reader, ctx, params );
            case BEGIN_ARRAY:
                if ( null == listJsonDeserializer ) {
                    listJsonDeserializer = ArrayListJsonDeserializer.newInstance( this );
                }
                return listJsonDeserializer.doDeserialize( reader, ctx, params );
            case BEGIN_OBJECT:
                if ( null == mapJsonDeserializer ) {
                    mapJsonDeserializer = LinkedHashMapJsonDeserializer.newInstance( StringKeyDeserializer.getInstance(), this );
                }
                return mapJsonDeserializer.doDeserialize( reader, ctx, params );
            case NULL:
                reader.nextNull();
                return null;
            default:
                throw ctx.traceError( "Unexpected token " + reader.peek() + " for java.lang.Object deserialization", reader );
        }
    }

}
