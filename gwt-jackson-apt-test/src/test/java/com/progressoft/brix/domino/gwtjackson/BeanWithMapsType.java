/*
 * Copyright 2017 Nicolas Morel
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

public class BeanWithMapsType {

    public AbstractMap<String, Integer> abstractMap;

    public EnumMap<AnEnum, Integer> enumMap;

    public HashMap<String, Integer> hashMap;

    public IdentityHashMap<String, Integer> identityHashMap;

    public LinkedHashMap<String, Integer> linkedHashMap;

    public Map<String, Integer> map;

    public SortedMap<String, Integer> sortedMap;

    public TreeMap<String, Integer> treeMap;

}