package org.dominokit.jacksonapt.processor;

import java.util.Map;

import javax.lang.model.type.TypeMirror;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

public class SubTypesInfo {
	private final As as;
	private final String propertyName;
	private final Map<String, TypeMirror> subTypes;
	
	public SubTypesInfo(As as, String propertyName, Map<String, TypeMirror> subTypes) {
		this.as = as;
		this.propertyName = propertyName;
		this.subTypes = subTypes;
				
	}
	
	public static SubTypesInfo emtpy() {
		return new SubTypesInfo(null, null, null); 
	}
	
	public boolean hasSubTypes() {
		return subTypes != null && !subTypes.isEmpty();
	}
	
	public As getAs() {
		return as;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public Map<String, TypeMirror> getSubTypes() {
		return subTypes;
	}
}