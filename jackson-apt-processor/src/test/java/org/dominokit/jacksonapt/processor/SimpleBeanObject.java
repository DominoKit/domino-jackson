package org.dominokit.jacksonapt.processor;

public class SimpleBeanObject{
	public  Integer state = -1;
	
	public SimpleBeanObject() {
	}
	
	public SimpleBeanObject(int state) {
		this.state = state;
	}
	
	@Override
	public int hashCode() {
		return state.hashCode(); 
	}
	
	@Override
	public boolean equals(Object other) {
		return 
			other instanceof SimpleBeanObject
			&& state.equals(((SimpleBeanObject)other).state);
	}
}

