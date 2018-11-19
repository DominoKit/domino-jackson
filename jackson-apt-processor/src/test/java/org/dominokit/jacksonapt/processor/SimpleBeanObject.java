package org.dominokit.jacksonapt.processor;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dominokit.jacksonapt.ObjectMapper;
import org.dominokit.jacksonapt.annotation.JSONMapper;

public class SimpleBeanObject{
	@JSONMapper
	interface CollectionMapper extends ObjectMapper<List<Map<String, SimpleBeanObject>>> {
	}

	@JSONMapper
	interface MapMapper extends ObjectMapper<Map<String, SimpleBeanObject>> {
	}
	
	static CollectionMapper MAPPER = new SimpleBeanObject_CollectionMapperImpl();
	
	
	public  Integer d;
	public SimpleBeanObject() {
		this.d = 5;
	}
	
}
