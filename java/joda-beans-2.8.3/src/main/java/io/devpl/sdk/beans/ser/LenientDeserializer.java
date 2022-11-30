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

import java.util.NoSuchElementException;

import io.devpl.sdk.beans.MetaBean;
import io.devpl.sdk.beans.MetaProperty;

/**
 * Lenient deserializer that ignores unknown properties.
 */
class LenientDeserializer extends DefaultDeserializer {

    /**
     * Singleton.
     */
    public static final SerDeserializer INSTANCE = new LenientDeserializer();

    /**
     * Creates an instance.
     */
    protected LenientDeserializer() {
    }

    //-----------------------------------------------------------------------
    @Override
    public MetaProperty<?> findMetaProperty(Class<?> beanType, MetaBean metaBean, String propertyName) {
        // dynamic beans force code by exception
        try {
            return metaBean.metaProperty(propertyName);
        } catch (NoSuchElementException ex) {
            return null;
        }
    }

}
