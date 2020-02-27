package org.dominokit.jacksonapt.processor;

import org.dominokit.jacksonapt.annotation.JSONMapper;
import org.dominokit.jacksonapt.annotation.JSONReader;
import org.dominokit.jacksonapt.annotation.JSONWriter;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>Abstract AbstractMapperProcessor class.</p>
 *
 * @author vegegoku
 * @version $Id: $Id
 */
public abstract class AbstractMapperProcessor extends AbstractProcessor {

    /** Constant <code>messager</code> */
    public static Messager messager;
    /** Constant <code>typeUtils</code> */
    public static Types typeUtils;
    /** Constant <code>filer</code> */
    public static Filer filer;
    /** Constant <code>elementUtils</code> */
    public static Elements elementUtils;

    public static ProcessingEnvironment environment;
    protected Set<? extends Element> mappers;
    protected Set<? extends Element> readers;
    protected Set<? extends Element> writers;

    /** {@inheritDoc} */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        environment = processingEnv;
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
    }

    /** {@inheritDoc} */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    	if (roundEnv.processingOver()) {
    		TypeRegistry.resetTypeRegistry();
    		return false;
    	}
    	
    	mappers = roundEnv.getElementsAnnotatedWith(JSONMapper.class);
        readers = roundEnv.getElementsAnnotatedWith(JSONReader.class);
        writers = roundEnv.getElementsAnnotatedWith(JSONWriter.class);
        return doProcess(annotations, roundEnv);
    }

    /**
     * <p>doProcess.</p>
     *
     * @param annotations a {@link java.util.Set} object.
     * @param roundEnv a {@link javax.annotation.processing.RoundEnvironment} object.
     * @return a boolean.
     */
    protected abstract boolean doProcess(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv);

    /**
     * <p>handleError.</p>
     *
     * @param e a {@link java.lang.Exception} object.
     */
    protected void handleError(Exception e) {
        StringWriter out = new StringWriter();
        e.printStackTrace(new PrintWriter(out));
        messager.printMessage(Diagnostic.Kind.ERROR, "error while creating source file " + out.getBuffer().toString());
    }

    /** {@inheritDoc} */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return supportedAnnotations().stream()
                .map(Class::getCanonicalName).collect(Collectors.toSet());
    }

    /** {@inheritDoc} */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * <p>supportedAnnotations.</p>
     *
     * @return a {@link java.util.List} object.
     */
    protected abstract List<Class<?>> supportedAnnotations();
}
