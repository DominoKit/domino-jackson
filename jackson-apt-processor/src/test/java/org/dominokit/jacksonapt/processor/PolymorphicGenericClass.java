package org.dominokit.jacksonapt.processor;

import java.util.List;
import java.util.Map;

public class PolymorphicGenericClass<T> {
	public T data;
	public List<? extends T> dataList;
	public List<? extends SecondPolymorphicBaseClass> dataListSecond;
	public Map<Integer, ? extends T> dataMap; 
	
}
