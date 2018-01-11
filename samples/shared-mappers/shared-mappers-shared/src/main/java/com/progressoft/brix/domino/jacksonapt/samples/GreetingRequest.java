package com.progressoft.brix.domino.jacksonapt.samples;

import com.progressoft.brix.domino.jacksonapt.annotation.JSONMapper;

@JSONMapper
public class GreetingRequest {

    public static GreetingRequest_MapperImpl MAPPER=new GreetingRequest_MapperImpl();

    public String name;
    public int age;
    public Double random;
}
