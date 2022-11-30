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
package io.devpl.sdk.beans.sample;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Test RWOnlyBean.
 */
public class TestRWOnlyBean extends RWOnlyBean {

    @Test
    public void test_ro() {
        assertNull(getRo());
        assertNull(ro().get());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_wo1() {
        setWo("woo");
        getProperty("wo").get();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_wo2() {
        setWo("woo");
        wo().get();
    }

    @Test
    public void test_manualGet() {
        assertEquals(getManualGet(), "goo");
        assertEquals(manualGet().get(), "goo");
    }

    @Test
    public void test_derived() {
        assertEquals(getDerived(), "drv");
        assertEquals(derived().get(), "drv");
    }

}
