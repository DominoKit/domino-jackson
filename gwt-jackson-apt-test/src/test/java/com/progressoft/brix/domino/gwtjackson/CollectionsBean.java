/*
 * Copyright 2017 Ahmad Bawaneh
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

import java.util.*;

public class CollectionsBean {

    public AbstractCollection<Integer> intabstractCollection;
    public AbstractCollection<Double> doubleabstractCollection;
    public AbstractCollection<String> stringabstractCollection;
    public AbstractCollection<AnEnum> enmumabstractCollection;
    public AbstractCollection<Date> dateabstractCollection;

    public AbstractList<AbstractCollection<Integer>> abstractList;

    public AbstractQueue<String> abstractQueue;

    public AbstractSequentialList<AbstractQueue<String>> abstractSequentialList;

    public AbstractSet<Collection<String>> abstractSet;

    public ArrayList<HashSet<String>> arrayList;

    public Collection<Iterable<String>> collection;

    public EnumSet<AnEnum> enumSet;

    public HashSet<LinkedHashSet<String>> hashSet;

    public Iterable<EnumSet<AnEnum>> iterable;

    public LinkedHashSet<List<String>> linkedHashSet;

    public LinkedList<PriorityQueue<String>> linkedList;

    public List<PriorityQueue<String>> list;

    public PriorityQueue<Set<String>> priorityQueue;

    public Queue<List<String>> queue;

    public Set<TreeSet<String>> set;

    public SortedSet<Stack<String>> sortedSet;

    public Stack<Vector<String>> stack;

    public TreeSet<Stack<String>> treeSet;

    public Vector<Vector<String>> vector;

    public List<Set<String>> listSet;
}
