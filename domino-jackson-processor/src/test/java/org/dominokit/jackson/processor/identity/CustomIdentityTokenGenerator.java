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
package org.dominokit.jackson.processor.identity;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;

public class CustomIdentityTokenGenerator extends ObjectIdGenerator<IdentityToken> {
  private final Class<?> scope;

  public CustomIdentityTokenGenerator() {
    this(Object.class);
  }

  private CustomIdentityTokenGenerator(Class<?> scope) {
    this.scope = scope;
  }

  @Override
  public Class<?> getScope() {
    return scope;
  }

  @Override
  public boolean canUseFor(ObjectIdGenerator<?> generator) {
    return generator.getClass() == getClass() && generator.getScope() == scope;
  }

  @Override
  public ObjectIdGenerator<IdentityToken> forScope(Class<?> scope) {
    return this.scope == scope ? this : new CustomIdentityTokenGenerator(scope);
  }

  @Override
  public ObjectIdGenerator<IdentityToken> newForSerialization(Object context) {
    return new CustomIdentityTokenGenerator(scope);
  }

  @Override
  public ObjectIdGenerator.IdKey key(Object key) {
    return key == null ? null : new ObjectIdGenerator.IdKey(getClass(), scope, key);
  }

  @Override
  public IdentityToken generateId(Object forPojo) {
    IdentityCustomBean bean = (IdentityCustomBean) forPojo;
    return new IdentityToken("node-" + bean.getName());
  }
}
