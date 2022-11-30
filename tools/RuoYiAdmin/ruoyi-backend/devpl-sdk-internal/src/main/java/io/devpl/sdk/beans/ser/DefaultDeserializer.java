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
package io.devpl.sdk.beans.ser;

import io.devpl.sdk.beans.BeanBuilder;
import io.devpl.sdk.beans.MetaBean;
import io.devpl.sdk.beans.MetaProperty;

/**
 * Default deserializer that expects the input to match the current classpath beans.
 * <p>
 * This uses the standard {@code MetaBean}, {@code MetaProperty} and  {@code BeanBuilder}.
 */
public class DefaultDeserializer implements SerDeserializer {

    /**
     * Singleton.
     */
    public static final SerDeserializer INSTANCE = new DefaultDeserializer();

    /**
     * Creates an instance.
     */
    protected DefaultDeserializer() {
    }

    //-----------------------------------------------------------------------
    @Override
    public MetaBean findMetaBean(Class<?> beanType) {
        return MetaBean.of(beanType);
    }

    @Override
    public BeanBuilder<?> createBuilder(Class<?> beanType, MetaBean metaBean) {
        return metaBean.builder();
    }

    @Override
    public MetaProperty<?> findMetaProperty(Class<?> beanType, MetaBean metaBean, String propertyName) {
        return metaBean.metaProperty(propertyName);
    }

    @Override
    public void setValue(BeanBuilder<?> builder, MetaProperty<?> metaProp, Object value) {
        builder.set(metaProp, value);
    }

    @Override
    public Object build(Class<?> beanType, BeanBuilder<?> builder) {
        return builder.build();
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
