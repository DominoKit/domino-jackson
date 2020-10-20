/*
 * Copyright 2020 Ahmad Bawaneh
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

package org.dominokit.jacksonapt.processor.inner;

import org.dominokit.jacksonapt.annotation.JSONMapper;

/**
 * @author Dmitrii Tikhomirov
 * Created by treblereel 10/20/20
 */
public class MyBean1 {

    @JSONMapper
    public static class Bean {

        private String myBean1Value;

        public String getMyBean1Value() {
            return myBean1Value;
        }

        public void setMyBean1Value(String myBean1Value) {
            this.myBean1Value = myBean1Value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Bean)) {
                return false;
            }

            Bean bean = (Bean) o;

            return getMyBean1Value() != null ? getMyBean1Value().equals(bean.getMyBean1Value()) : bean.getMyBean1Value() == null;
        }

        @Override
        public int hashCode() {
            return getMyBean1Value() != null ? getMyBean1Value().hashCode() : 0;
        }
    }


}
