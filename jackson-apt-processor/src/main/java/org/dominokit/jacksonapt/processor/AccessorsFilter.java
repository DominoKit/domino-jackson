package org.dominokit.jacksonapt.processor;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AccessorsFilter {

    private Types typeUtils;

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
}
