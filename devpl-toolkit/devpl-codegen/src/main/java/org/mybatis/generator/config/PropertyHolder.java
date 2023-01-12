/*
 *    Copyright 2006-2022 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.generator.config;

import org.mybatis.generator.internal.util.StringUtils;

import java.util.Properties;

/**
 * 自带一个property属性 选择继承还是组合
 */
public abstract class PropertyHolder {
    private final Properties properties;

    protected PropertyHolder() {
        super();
        properties = new Properties();
    }

    public void addProperty(String name, String value) {
        properties.setProperty(name, value);
    }

    public String getProperty(String name) {
        return properties.getProperty(name);
    }

    public Properties getProperties() {
        return properties;
    }

    public boolean getBoolean(String key) {
        final String property = properties.getProperty(key);
        if ("true".equals(property)) {
            return true;
        }
        if ("false".equals(property)) {
            return false;
        }
        throw new RuntimeException("key = " + key + ", value = " + property);
    }
}