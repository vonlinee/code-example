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
package io.devpl.sdk.beans.impl.light;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

import io.devpl.sdk.beans.Bean;
import io.devpl.sdk.beans.MetaBean;
import io.devpl.sdk.beans.Property;
import io.devpl.sdk.beans.impl.StandardMetaProperty;
import io.devpl.sdk.beans.impl.StandardProperty;

/**
 * An immutable meta-property based on a getter interface.
 * @param <P> the type of the property content
 * @deprecated Replaced by method handles
 */
@Deprecated
abstract class AbstractLightMetaProperty<P> extends StandardMetaProperty<P> {

    /**
     * The meta-bean.
     */
    private final MetaBean metaBean;
    /**
     * The type of the property.
     */
    private final Class<P> propertyType;
    /**
     * The type of the property.
     */
    private final Type propertyGenericType;
    /**
     * The annotations.
     */
    private final List<Annotation> annotations;
    /**
     * The index of the property in the constructor.
     */
    private final int constructorIndex;

    //-----------------------------------------------------------------------

    /**
     * Creates an instance.
     * @param metaBean            the meta bean, not null
     * @param propertyName        the property name, not empty
     * @param propertyType        the property type
     * @param propertyGenericType the property generic type
     * @param annotations         the annotations
     * @param constructorIndex    the index of the property in the constructor
     */
    AbstractLightMetaProperty(
            MetaBean metaBean,
            String propertyName,
            Class<P> propertyType,
            Type propertyGenericType,
            List<Annotation> annotations,
            int constructorIndex) {
        super(propertyName);
        this.metaBean = metaBean;
        this.propertyType = propertyType;
        this.propertyGenericType = propertyGenericType;
        this.annotations = annotations;
        this.constructorIndex = constructorIndex;
    }

    //-----------------------------------------------------------------------
    @Override
    public Property<P> createProperty(Bean bean) {
        return StandardProperty.of(bean, this);
    }

    @Override
    public MetaBean metaBean() {
        return metaBean;
    }

    @Override
    public Class<?> declaringType() {
        return metaBean.beanType();
    }

    @Override
    public Class<P> propertyType() {
        return propertyType;
    }

    @Override
    public Type propertyGenericType() {
        return propertyGenericType;
    }

    @Override
    public List<Annotation> annotations() {
        return annotations;
    }

    //-----------------------------------------------------------------------
    int getConstructorIndex() {
        return constructorIndex;
    }

}
