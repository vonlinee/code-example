/*
 *  Copyright 2001-present Stephen Colebourne
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.devpl.sdk.beans.impl.map;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.devpl.sdk.beans.BeanBuilder;
import io.devpl.sdk.beans.DynamicMetaBean;
import io.devpl.sdk.beans.MetaProperty;

/**
 * Implementation of a meta-bean for {@code MapBean}.
 */
class MapMetaBean implements DynamicMetaBean {

    /**
     * The bean itself.
     */
    private final BeanMap bean;

    /**
     * Creates the meta-bean.
     * 
     * @param bean  the underlying bean, not null
     */
    MapMetaBean(BeanMap bean) {
        this.bean = bean;
    }

    //-----------------------------------------------------------------------
    @Override
    public boolean isBuildable() {
        return true;
    }

    @Override
    public BeanBuilder<BeanMap> builder() {
        return new MapBeanBuilder(bean);
    }

    @Override
    public Class<BeanMap> beanType() {
        return BeanMap.class;
    }

    @Override
    public int metaPropertyCount() {
        return bean.size();
    }

    @Override
    public boolean metaPropertyExists(String name) {
        return bean.containsKey(name);
    }

    @Override
    public MetaProperty<Object> metaProperty(String name) {
        // do not check if exists
        return MapBeanMetaProperty.of(this, name);
    }

    @Override
    public Iterable<MetaProperty<?>> metaPropertyIterable() {
        return new Iterable<MetaProperty<?>>() {
            @Override
            public Iterator<MetaProperty<?>> iterator() {
                Iterator<String> it = bean.keySet().iterator();
                return new Iterator<MetaProperty<?>>() {
                    @Override
                    public boolean hasNext() {
                        return it.hasNext();
                    }
                    @Override
                    public MetaProperty<?> next() {
                        return MapBeanMetaProperty.of(MapMetaBean.this, it.next());
                    }
                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException("Unmodifiable");
                    }
                    
                };
            }
        };
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
        Map<String, MetaProperty<?>> map = new LinkedHashMap<>();
        for (String name : bean.keySet()) {
            map.put(name, MapBeanMetaProperty.of(this, name));
        }
        return Collections.unmodifiableMap(map);
    }

    //-----------------------------------------------------------------------
    @Override
    public void metaPropertyDefine(String propertyName, Class<?> propertyType) {
        bean.defineProperty(propertyName, propertyType);
    }

    @Override
    public void metaPropertyRemove(String propertyName) {
        bean.removeProperty(propertyName);
    }

    @Override
    public List<Annotation> annotations() {
        return Collections.emptyList();
    }

    /**
     * Returns a string that summarises the meta-bean.
     * 
     * @return a summary string, not null
     */
    @Override
    public String toString() {
        return "MetaBean:" + beanType().getSimpleName();
    }

}
