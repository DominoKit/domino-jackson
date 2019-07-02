package org.dominokit.jacksonapt.processor;

import java.util.List;

public class PolymorphicGenericClass<T> {
	public T data;
	public List<? extends T> dataList;
	public List<? extends SecondPolymorphicBaseClass> dataListSecond;
	
}
