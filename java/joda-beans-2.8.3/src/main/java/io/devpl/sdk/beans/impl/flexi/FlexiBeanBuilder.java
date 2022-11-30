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
package io.devpl.sdk.beans.impl.flexi;

import io.devpl.sdk.beans.BeanBuilder;
import io.devpl.sdk.beans.MetaProperty;

/**
 * Implementation of a meta-bean for {@code FlexiBean}.
 */
class FlexiBeanBuilder implements BeanBuilder<FlexiBean> {

    /**
     * The bean itself.
     */
    private final FlexiBean bean;

    /**
     * Creates the meta-bean.
     * 
     * @param bean  the underlying bean, not null
     */
    FlexiBeanBuilder(FlexiBean bean) {
        this.bean = bean;
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
        // lenient getter
        return bean.get(propertyName);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <P> P get(MetaProperty<P> metaProperty) {
        // this approach allows meta-property from one bean to be used with another
        return (P) bean.get(metaProperty.name());
    }

    //-----------------------------------------------------------------------
    @Override
    public FlexiBeanBuilder set(String propertyName, Object value) {
        bean.put(propertyName, value);
        return this;
    }

    @Override
    public FlexiBeanBuilder set(MetaProperty<?> metaProperty, Object value) {
        // this approach allows meta-property from one bean to be used with another
        bean.put(metaProperty.name(), value);
        return this;
    }

    @Override
    public FlexiBean build() {
        return bean;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
        return "FlexiBeanBuilder";
    }

}
