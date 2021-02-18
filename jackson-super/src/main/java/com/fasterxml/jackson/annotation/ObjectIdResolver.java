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
package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey;

/**
 * Definition of API used for resolving actual Java object from Object Identifiers (as annotated
 * using {@link JsonIdentityInfo}).
 *
 * @since 2.4
 */
public interface ObjectIdResolver {
  /**
   * Method called when a POJO is deserialized and has an Object Identifier. Method exists so that
   * implementation can keep track of existing object in JSON stream that could be useful for
   * further resolution.
   *
   * @param id The Object Identifer
   * @param pojo The POJO associated to that Identifier
   */
  void bindItem(IdKey id, Object pojo);

  /**
   * Method called when deserialization encounters the given Object Identifier and requires the POJO
   * associated with it.
   *
   * @param id The Object Identifier
   * @return The POJO, or null if unable to resolve.
   */
  Object resolveId(IdKey id);

  /**
   * Factory method called to create a new instance to use for deserialization: needed since
   * resolvers may have state (a pool of objects).
   *
   * <p>Note that actual type of 'context' is <code>
   * com.fasterxml.jackson.databind.DeserializationContext</code>, but can not be declared here as
   * type itself (as well as call to this object) comes from databind package.
   *
   * @param context Deserialization context object used (of type <code>
   *     com.fasterxml.jackson.databind.DeserializationContext</code> ; may be needed by more
   *     complex resolvers to access contextual information such as configuration.
   * @return {@link ObjectIdResolver}
   */
  ObjectIdResolver newForDeserialization(Object context);

  /**
   * Method called to check whether this resolver instance can be used for Object Ids of specific
   * resolver type; determination is based by passing a configured "blueprint" (prototype) instance;
   * from which the actual instances are created (using {@link #newForDeserialization}).
   *
   * @param resolverType {@link ObjectIdResolver}
   * @return True if this instance can be used as-is; false if not
   */
  boolean canUseFor(ObjectIdResolver resolverType);
}
