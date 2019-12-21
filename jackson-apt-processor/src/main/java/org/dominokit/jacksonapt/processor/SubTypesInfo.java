package org.dominokit.jacksonapt.processor;

import java.util.Collections;
import java.util.Map;

import javax.lang.model.type.TypeMirror;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

public class SubTypesInfo {
	private final As include;
	private final String propertyName;
	private final Map<String, TypeMirror> subTypes;
	
	public SubTypesInfo(As include, String propertyName, Map<String, TypeMirror> subTypes) {
		this.include = include;
		this.propertyName = propertyName;
		this.subTypes = subTypes;
				
	}
	
	public static SubTypesInfo emtpy() {
		return new SubTypesInfo(null, null, Collections.emptyMap()); 
	}
	
	public boolean hasSubTypes() {
		return subTypes != null && !subTypes.isEmpty();
	}
	
	public As getInclude() {
		return include;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public Map<String, TypeMirror> getSubTypes() {
		return subTypes;
	}
}
