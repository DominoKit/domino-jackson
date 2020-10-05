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

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Objects;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DataInput.class, name = "dataInput"),
        @JsonSubTypes.Type(value = DataOutput.class, name = "dataOutput"),
})
public abstract class Data<T extends Data> {

    protected String id;

    protected String dtype;

    protected String itemSubjectRef;

    protected String name;

    protected T genericField;

    public String getDtype() {
        return dtype;
    }

    public T setDtype(String dtype) {
        this.dtype = dtype;
        return (T) this;
    }

    public String getId() {
        return id;
    }

    public T setId(String id) {
        this.id = id;
        return (T) this;
    }

    public String getItemSubjectRef() {
        return itemSubjectRef;
    }

    public T setItemSubjectRef(String itemSubjectRef) {
        this.itemSubjectRef = itemSubjectRef;
        return (T) this;
    }

    public String getName() {
        return name;
    }

    public T setName(String name) {
        this.name = name;
        return (T) this;
    }

    public T getGenericField() {
        return genericField;
    }

    public void setGenericField(T genericField) {
        this.genericField = genericField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Data<?> data = (Data<?>) o;
        return Objects.equals(id, data.id) &&
                Objects.equals(dtype, data.dtype) &&
                Objects.equals(itemSubjectRef, data.itemSubjectRef) &&
                Objects.equals(name, data.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dtype, itemSubjectRef, name);
    }
}
