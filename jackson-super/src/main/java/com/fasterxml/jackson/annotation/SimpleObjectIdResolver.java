package com.fasterxml.jackson.annotation;

import com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple implementation of {@link com.fasterxml.jackson.annotation.ObjectIdResolver}
 *
 * @author Pascal Gélinas
 * @version $Id: $Id
 */
public class SimpleObjectIdResolver implements ObjectIdResolver {
    protected Map<IdKey,Object> _items;

    /**
     * <p>Constructor for SimpleObjectIdResolver.</p>
     */
    public SimpleObjectIdResolver() { }

    /** {@inheritDoc} */
    @Override
    public void bindItem(IdKey id, Object ob)
    {
        if (_items == null) {
            _items = new HashMap<IdKey,Object>();
        } else if (_items.containsKey(id)) {
            throw new IllegalStateException("Already had POJO for id (" + id.key.getClass().getName() + ") [" + id
                    + "]");
        }
        _items.put(id, ob);
    }

    /** {@inheritDoc} */
    @Override
    public Object resolveId(IdKey id) {
        return (_items == null) ? null : _items.get(id);
    }

    /** {@inheritDoc} */
    @Override
    public boolean canUseFor(ObjectIdResolver resolverType) {
        return resolverType.getClass() == getClass();
    }

    /** {@inheritDoc} */
    @Override
    public ObjectIdResolver newForDeserialization(Object context) {
        // 19-Dec-2014, tatu: Important: must re-create without existing mapping; otherwise bindings leak
        //    (and worse, cause unnecessary memory retention)
        return new SimpleObjectIdResolver();
    }
}
