package org.dominokit.jacksonapt.processor;

import java.io.IOException;

import javax.lang.model.element.Element;

public interface MapperGenerator {
	void generate(Element element) throws IOException;
}
