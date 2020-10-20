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
@JSONMapper
public class MyBean3 {

    private MyBean1.Bean bean1;
    private MyBean2.Bean bean2;

    public MyBean1.Bean getBean1() {
        return bean1;
    }

    public void setBean1(MyBean1.Bean bean1) {
        this.bean1 = bean1;
    }

    public MyBean2.Bean getBean2() {
        return bean2;
    }

    public void setBean2(MyBean2.Bean bean2) {
        this.bean2 = bean2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MyBean3)) {
            return false;
        }

        MyBean3 myBean3 = (MyBean3) o;

        if (getBean1() != null ? !getBean1().equals(myBean3.getBean1()) : myBean3.getBean1() != null) {
            return false;
        }
        return getBean2() != null ? getBean2().equals(myBean3.getBean2()) : myBean3.getBean2() == null;
    }

    @Override
    public int hashCode() {
        int result = getBean1() != null ? getBean1().hashCode() : 0;
        result = 31 * result + (getBean2() != null ? getBean2().hashCode() : 0);
        return result;
    }
}
