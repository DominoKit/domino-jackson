package org.dominokit.jacksonapt.processor;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, visible = true)
@JsonSubTypes({
		 @Type(value = PolymorphicChildClass.class, name = "childclass"),
		 @Type(value = PolymorphicChildClass2.class, name = "childclass2")})

public interface PolymorphicBaseInterface {

}
