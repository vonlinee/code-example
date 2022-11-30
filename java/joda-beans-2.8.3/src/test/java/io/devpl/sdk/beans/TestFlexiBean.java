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
package io.devpl.sdk.beans;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import io.devpl.sdk.beans.impl.flexi.FlexiBean;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test FlexiBean.
 */
public class TestFlexiBean {

    @Test
    public void test_serialization() throws Exception {
        FlexiBean test = new FlexiBean();
        test.put("name", "Etienne");
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(test);
        }

        try (ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray())) {
            try (ObjectInputStream ois = new ObjectInputStream(bais)) {
                Object obj = ois.readObject();
                assertEquals(test, obj);
            }
        }
    }

    @SuppressWarnings("unlikely-arg-type")
    @Test
    public void test_equalsHashCode() {
        FlexiBean a1 = new FlexiBean();
        FlexiBean a2 = new FlexiBean();
        FlexiBean b = new FlexiBean();
        
        a1.set("first", "A");
        a2.set("first", "A");
        b.set("first", "B");

        assertTrue(a1.equals(a1));
        assertTrue(a1.equals(a2));
        assertTrue(a2.equals(a1));
        assertTrue(a2.equals(a2));
        assertEquals(a1.hashCode(), a2.hashCode());

        assertFalse(a1.equals(b));
        assertFalse(b.equals(a1));

        assertFalse(b.equals("Weird type"));
        assertFalse(b.equals(null));
    }

    @Test
    public void test_propertyDefine_propertyRemove() {
        FlexiBean flexi = new FlexiBean();
        assertEquals(flexi.propertyNames().size(), 0);
        flexi.defineProperty("name", String.class);
        assertEquals(flexi.propertyNames().size(), 1);
        Property<Object> prop = flexi.getProperty("name");
        assertEquals(prop.name(), "name");
        assertNull(prop.get());
        flexi.removeProperty("name");
        assertEquals(flexi.propertyNames().size(), 0);
    }

    @Test
    public void test_metaBean() {
        FlexiBean flexi = new FlexiBean();
        DynamicMetaBean meta = flexi.metaBean();
        assertEquals(meta.metaPropertyCount(), 0);
        
        meta.metaPropertyDefine("name", String.class);
        assertEquals(meta.metaPropertyCount(), 1);
        MetaProperty<Object> prop = meta.metaProperty("name");
        assertEquals(prop.name(), "name");
        assertNull(prop.get(flexi));
        
        meta.metaPropertyDefine("name", String.class);
        assertEquals(meta.metaPropertyCount(), 1);
        
        MetaProperty<Object> prop2 = meta.metaProperty("address");
        assertNotNull(prop2);
        assertEquals(meta.metaPropertyCount(), 1);  // meta-property object created but data not changed
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_invalidProperty() {
        FlexiBean flexi = new FlexiBean();
        flexi.defineProperty("bad-name", String.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_append_invalidProperty() {
        FlexiBean flexi = new FlexiBean();
        flexi.append("bad-name", "a");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_set_invalidProperty() {
        FlexiBean flexi = new FlexiBean();
        flexi.set("bad-name", "a");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_put_invalidProperty() {
        FlexiBean flexi = new FlexiBean();
        flexi.put("bad-name", "a");
    }

}
