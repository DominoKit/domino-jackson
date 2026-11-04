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
package org.dominokit.jackson.processor.subtype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Concrete {@link List} whose element type is declared only on the interface. The superclass is
 * {@link Object}, so the element type is not visible there.
 */
public class UserList implements List<NamedUser> {

  private final List<NamedUser> users = new ArrayList<>();

  @Override
  public int size() {
    return users.size();
  }

  @Override
  public boolean isEmpty() {
    return users.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return users.contains(o);
  }

  @Override
  public Iterator<NamedUser> iterator() {
    return users.iterator();
  }

  @Override
  public Object[] toArray() {
    return users.toArray();
  }

  @Override
  public <T> T[] toArray(T[] a) {
    return users.toArray(a);
  }

  @Override
  public boolean add(NamedUser user) {
    return users.add(user);
  }

  @Override
  public boolean remove(Object o) {
    return users.remove(o);
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return users.containsAll(c);
  }

  @Override
  public boolean addAll(Collection<? extends NamedUser> c) {
    return users.addAll(c);
  }

  @Override
  public boolean addAll(int index, Collection<? extends NamedUser> c) {
    return users.addAll(index, c);
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    return users.removeAll(c);
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    return users.retainAll(c);
  }

  @Override
  public void clear() {
    users.clear();
  }

  @Override
  public NamedUser get(int index) {
    return users.get(index);
  }

  @Override
  public NamedUser set(int index, NamedUser element) {
    return users.set(index, element);
  }

  @Override
  public void add(int index, NamedUser element) {
    users.add(index, element);
  }

  @Override
  public NamedUser remove(int index) {
    return users.remove(index);
  }

  @Override
  public int indexOf(Object o) {
    return users.indexOf(o);
  }

  @Override
  public int lastIndexOf(Object o) {
    return users.lastIndexOf(o);
  }

  @Override
  public ListIterator<NamedUser> listIterator() {
    return users.listIterator();
  }

  @Override
  public ListIterator<NamedUser> listIterator(int index) {
    return users.listIterator(index);
  }

  @Override
  public List<NamedUser> subList(int fromIndex, int toIndex) {
    return users.subList(fromIndex, toIndex);
  }
}
