package org.dominokit.jacksonapt.processor;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class AccessorsFilter {

    protected Types typeUtils;

    public AccessorsFilter(Types typeUtils) {
        this.typeUtils = typeUtils;
    }

    protected Set<String> getAccessors(TypeMirror beanType) {
        TypeElement element = (TypeElement) typeUtils.asElement(beanType);
        TypeMirror superclass = element.getSuperclass();
        if (superclass.getKind().equals(TypeKind.NONE)) {
            return new HashSet<>();
        }

        Set<String> collect = ((TypeElement) ObjectMapperProcessor.typeUtils.asElement(beanType)).getEnclosedElements().stream()
                .filter(e -> ElementKind.METHOD.equals(e.getKind()) &&
                        !e.getModifiers().contains(Modifier.STATIC) &&
                        e.getModifiers().contains(Modifier.PUBLIC))
                .map(e -> e.getSimpleName().toString()).collect(Collectors.toSet());
        collect.addAll(getAccessors(superclass));
        return collect;
    }

    protected String getPropertyName(Element field){
        JsonProperty annotation = field.getAnnotation(JsonProperty.class);
        if(isNull(annotation) || JsonProperty.USE_DEFAULT_NAME.equals(annotation.value())){
            return field.getSimpleName().toString();
        }else{
            return annotation.value();
        }
    }
}
