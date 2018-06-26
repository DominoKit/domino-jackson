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

public abstract class AbstractMapperProcessor extends AbstractProcessor {

    public static Messager messager;
    public static Types typeUtils;
    public static Filer filer;
    public static Elements elementUtils;
    protected Set<? extends Element> mappers;
    protected Set<? extends Element> readers;
    protected Set<? extends Element> writers;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        mappers = roundEnv.getElementsAnnotatedWith(JSONMapper.class);
        readers = roundEnv.getElementsAnnotatedWith(JSONReader.class);
        writers = roundEnv.getElementsAnnotatedWith(JSONWriter.class);
        return doProcess(annotations, roundEnv);
    }

    protected abstract boolean doProcess(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv);

    protected void handleError(Exception e) {
        StringWriter out = new StringWriter();
        e.printStackTrace(new PrintWriter(out));
        messager.printMessage(Diagnostic.Kind.ERROR, "error while creating source file " + out.getBuffer().toString());
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return supportedAnnotations().stream()
                .map(Class::getCanonicalName).collect(Collectors.toSet());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    protected abstract List<Class> supportedAnnotations();
}
