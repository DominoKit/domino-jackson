package org.dominokit.jacksonapt.processor;

import javax.lang.model.type.TypeMirror;
import java.util.Optional;

public class BeanIdentityInfo {

    private final String propertyName;

    private final boolean idABeanProperty;

    private final boolean alwaysAsId;

    private final TypeMirror generator;

    private final Optional<TypeMirror> scope;

    private final Optional<TypeMirror> type;

    BeanIdentityInfo(String propertyName, boolean alwaysAsId, TypeMirror generator, Optional<TypeMirror> scope ) {
        this.propertyName = propertyName;
        this.alwaysAsId = alwaysAsId;
        this.generator = generator;
        this.scope = scope;
        this.idABeanProperty = true;
        this.type = Optional.empty();
    }

    BeanIdentityInfo( String propertyName, boolean alwaysAsId, TypeMirror generator, Optional<TypeMirror> scope,
                      Optional<TypeMirror> type ) {
        this.propertyName = propertyName;
        this.alwaysAsId = alwaysAsId;
        this.generator = generator;
        this.scope = scope;
        this.idABeanProperty = false;
        this.type = type;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public boolean isIdABeanProperty() {
        return idABeanProperty;
    }

    public boolean isAlwaysAsId() {
        return alwaysAsId;
    }

    public TypeMirror getGenerator() {
        return generator;
    }

    public Optional<TypeMirror> getScope() {
        return scope;
    }

    public Optional<TypeMirror> getType() {
        return type;
    }
}
