package org.dominokit.jacksonapt.processor;

import java.util.List;
import java.util.Map;

public class GenericChildOfGenericObject<P, R> extends SimpleGenericBeanObject<R> {
	public P subField;
	public Map<P, List<R>> genericMap;
	public List<Map<P, List<R>>> genericList;
	
}
