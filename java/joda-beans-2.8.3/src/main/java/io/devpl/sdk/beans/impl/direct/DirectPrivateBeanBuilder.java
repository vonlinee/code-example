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
package io.devpl.sdk.beans.impl.direct;

import io.devpl.sdk.beans.Bean;
import io.devpl.sdk.beans.BeanBuilder;
import io.devpl.sdk.beans.MetaProperty;
import io.devpl.sdk.beans.test.JodaBeanTests;

/**
 * A builder implementation designed for use by the code generator.
 * <p>
 * This implementation is intended to have fields generated in the subclass.
 * 
 * @param <T> the bean type
 */
public abstract class DirectPrivateBeanBuilder<T extends Bean>
        implements BeanBuilder<T> {

    /**
     * Constructs the builder.
     */
    protected DirectPrivateBeanBuilder() {
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
        throw new UnsupportedOperationException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <P> P get(MetaProperty<P> metaProperty) {
        return (P) get(metaProperty.name());
    }

    //-----------------------------------------------------------------------
    @Override
    public BeanBuilder<T> set(MetaProperty<?> metaProperty, Object value) {
        try {
            set(metaProperty.name(), value);
            return this;
        } catch (RuntimeException ex) {
            if (value == JodaBeanTests.TEST_COVERAGE_STRING) {
                return this;
            }
            throw ex;
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a string that summarises the builder.
     * 
     * @return a summary string, not null
     */
    @Override
    public String toString() {
        return "BeanBuilder";
    }

}
