/*
 * Copyright © 2019 Dominokit
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
package org.dominokit.jacksonapt.samples.basic;

import com.google.gwt.core.client.EntryPoint;
import elemental2.dom.DomGlobal;
import org.dominokit.jacksonapt.ObjectMapper;
import org.dominokit.jacksonapt.annotation.JSONMapper;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class App implements EntryPoint {

    @JSONMapper
    public interface SampleMapper extends ObjectMapper<SimpleBean> {
        SampleMapper INSTANCE = new App_SampleMapperImpl();
    }

    public void onModuleLoad() {

        String input = "{" +
                "\"string\":\"toto\"," +
                "\"bytePrimitive\":34," +
                "\"byteBoxed\":87," +
                "\"shortPrimitive\":12," +
                "\"shortBoxed\":15," +
                "\"intPrimitive\":234," +
                "\"intBoxed\":456," +
                "\"longPrimitive\":-9223372036854775808," +
                "\"longBoxed\":\"9223372036854775807\"," +
                "\"doublePrimitive\":126.23," +
                "\"doubleBoxed\":1256.98," +
                "\"floatPrimitive\":12.89," +
                "\"floatBoxed\":67.3," +
                "\"booleanPrimitive\":true," +
                "\"booleanBoxed\":\"false\"," +
                "\"charPrimitive\":231," +
                "\"charBoxed\":232," +
                "\"bigInteger\":123456789098765432345678987654," +
                "\"bigDecimal\":\"12345678987654.456789\"," +
                "\"enumProperty\":\"B\"," +
                "\"date\":1345304756543," +
                "\"sqlDate\":\"2012-08-18\"," +
                "\"sqlTime\":\"15:45:56\"," +
                "\"sqlTimestamp\":1345304756546," +
                "\"stringArray\":[\"Hello\",null,\"World\",\"!\"]," +
                "\"enumArray\":[\"A\",null,\"C\",\"D\"]," +
                "\"booleanPrimitiveArray\":[true, null, false, 1, 0]," +
                "\"bytePrimitiveArray\":\"SGVsbG8=\"," +
                "\"characterPrimitiveArray\":\"çou\"," +
                "\"doublePrimitiveArray\":[45.789,null,5.1024]," +
                "\"floatPrimitiveArray\":[null]," +
                "\"integerPrimitiveArray\":[4,5,6,null,7,8]," +
                "\"longPrimitiveArray\":[9223372036854775807,null,-9223372036854775808]," +
                "\"shortPrimitiveArray\":[9,null,7,8,15]," +
                "\"stringArray2d\":[[\"Jean\",\"Dujardin\"],[\"Omar\",\"Sy\"],[\"toto\",null]]," +
                "\"enumArray2d\":[[\"A\",\"B\"],[\"C\",\"D\"],[\"B\",null]]," +
                "\"booleanPrimitiveArray2d\":[[true,false],[false,false]]," +
                "\"bytePrimitiveArray2d\":[\"SGVsbG8=\",\"V29ybGQ=\"]," +
                "\"characterPrimitiveArray2d\":[\"ço\",\"ab\"]," +
                "\"doublePrimitiveArray2d\":[[45.789,5.1024]]," +
                "\"floatPrimitiveArray2d\":[[]]," +
                "\"integerPrimitiveArray2d\":[[1,2,3],[4,5,6],[7,8,9]]," +
                "\"longPrimitiveArray2d\":[[9223372036854775807],[-9223372036854775808]]," +
                "\"shortPrimitiveArray2d\":[[9,7,8,15]]," +
                "\"voidProperty\":null" +
                "}";

        SimpleBean simpleBean = SampleMapper.INSTANCE.read(input);
        DomGlobal.console.log(simpleBean.toString());
        DomGlobal.console.log(SampleMapper.INSTANCE.write(simpleBean));

    }
}
