package org.dominokit.jacksonapt.processor;

import java.util.List;

public class SimpleGenericBeanObject<T> {
	public int intField;
	public T typeField;
	public List<T>[] genericArr;
	public String str = "str";
	
	public SimpleGenericBeanObject() {
		this(0, null);
	}
	
	public SimpleGenericBeanObject(int intField, T typeField) {
		this.intField = intField;
		this.typeField = typeField;
	}
	
	public int getIntFueld() {
		return intField;
	}
	
	public T getTypeField() {
		return typeField;
	}
	
	public String getStr() {
		return str;
	}
	
	@Override
	public int hashCode() {
		return intField + 17*typeField.hashCode(); 
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object other) {
		return 
			other instanceof SimpleGenericBeanObject
			&& intField == ((SimpleGenericBeanObject)other).intField
			&& typeField.equals(((SimpleGenericBeanObject)other).typeField);
	}
}
