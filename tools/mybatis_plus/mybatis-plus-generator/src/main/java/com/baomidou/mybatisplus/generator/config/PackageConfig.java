/*
 * Copyright (c) 2011-2020, hubin (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.baomidou.mybatisplus.generator.config;


import java.util.Map;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 跟包相关的配置项
 *
 * @author YangHu, tangguo, hubin
 * @since 2016-08-30
 */

@Data
@Accessors(chain = true)
public class PackageConfig {

    /**
     * 父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
     */
    private String parent = "com.baomidou";

    /**
     * 父包模块名。
     */
    private String moduleName = null;

    /**
     * Entity包名
     */
    private String entity = "entity";
    
    /**
     * graphql包名
     */
    private String graphql = "graphql";
    
    private String graphql1 = "graphql1";
    
    private String graphqlQuery = "resolver.query";
    private String graphqlMutation = "resolver.mutation";

    /**
     * Excel包名
     */
    private String excel = "excel";
    
    /**
     * Service包名
     */
    private String service = "service";

    /**
     * Service Impl包名
     */
    private String serviceImpl = "service.impl";
    
    /**
     * Model包名
     */
    private String model = "model";
    
    /**
     * Mapper包名
     */
    private String mapper = "mapper";

    /**
     * Mapper XML包名
     */
    private String xml = "mapper.xml";

    /**
     * Controller包名
     */
    private String controller = "controller";

    /**
     * 路径配置信息
     */
    private Map<String, String> pathInfo;

    public String getParent() {
        if (StringUtils.isNotEmpty(moduleName)) {
            return parent + StringPool.DOT + moduleName;
        }
        return parent;
    }

}
