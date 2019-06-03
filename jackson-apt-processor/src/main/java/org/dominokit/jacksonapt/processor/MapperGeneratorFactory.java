package org.dominokit.jacksonapt.processor;


import java.io.IOException;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;


public class MapperGeneratorFactory implements MapperGenerator{
	@Override
	public void generate(Element element) throws IOException {
		TypeMirror beanType = AbstractMapperGenerator.getElementType(element);
		// TODO get rid of CollectionMapperGenerator!
		// All needed code to distinguish between a collection 
		// and a bean is within makeNewDeserializerMethod() and
		// makeNewSerializerMethod() of 
		if (Type.isCollection(beanType) || Type.isMap(beanType)) {
			new CollectionMapperGenerator().generate(element);;
		} else
			new BeanMapperGenerator().generate(element);
	}
	
	public void generateReader(Element element) throws IOException {
		TypeMirror beanType = AbstractMapperGenerator.getElementType(element);
		if (Type.isCollection(beanType)) {
			new CollectionReaderGenerator().generate(element);;
		} else
			new BeanReaderGenerator().generate(element);
	}
	
	public void generateWriter(Element element) throws IOException {
		TypeMirror beanType = AbstractMapperGenerator.getElementType(element);
		if (Type.isCollection(beanType)) {
			new CollectionWriterGenerator().generate(element);;
		} else
			new BeanWriterGenerator().generate(element);
	}
}
