/*
 * Copyright 2020 Red Hat, Inc. and/or its affiliates.
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

package org.dominokit.jacksonapt.processor.inheritance;

public class DataInput extends Data<DataInput> {

    public DataInput() {

    }

    public DataInput(String id, String postfix, String name, String dtype) {
        this(id, postfix, name);
        this.dtype = dtype;
    }

    public DataInput(String id, String postfix, String name) {
        this.id = id + "_" + postfix;
        this.itemSubjectRef = id + "_" + postfix + "Item";
        this.name = name;
    }
}
