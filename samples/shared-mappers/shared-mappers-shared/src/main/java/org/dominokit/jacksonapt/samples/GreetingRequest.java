package org.dominokit.jacksonapt.samples;

import org.dominokit.jacksonapt.annotation.JSONMapper;

@JSONMapper
public class GreetingRequest {

    public static GreetingRequest_MapperImpl MAPPER=new GreetingRequest_MapperImpl();

    public String name;
    public int age;
    public Double random;
}
