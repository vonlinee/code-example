/*
 * Copyright (c) 2011-2021, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.devpl.codegen.mbpg.config.converts;

import io.devpl.codegen.mbpg.config.GlobalConfig;
import io.devpl.codegen.mbpg.config.ITypeConvert;
import io.devpl.codegen.mbpg.config.rules.DateType;
import io.devpl.codegen.mbpg.config.rules.DbColumnType;
import io.devpl.codegen.mbpg.config.rules.IColumnType;

/**
 * KingbaseES 字段类型转换
 *
 * @author kingbase, hanchunlin
 * @since 2019-10-12
 */
public class OscarTypeConvert implements ITypeConvert {
    public static final OscarTypeConvert INSTANCE = new OscarTypeConvert();

    /**
     * @param globalConfig 全局配置
     * @param fieldType    字段类型
     * @return 返回对应的字段类型
     */
    @Override
    public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
        return TypeConverts.use(fieldType)
            .test(TypeConverts
                    .containsAny("CHARACTER", "char", "varchar", "text", "character varying").then(DbColumnType.STRING))
            .test(TypeConverts.containsAny("bigint", "int8").then(DbColumnType.LONG))
            .test(TypeConverts
                    .containsAny("int", "int1", "int2", "int3", "int4", "tinyint", "integer").then(DbColumnType.INTEGER))
            .test(TypeConverts.containsAny("date", "time", "timestamp").then(p -> toDateType(globalConfig, p)))
            .test(TypeConverts.containsAny("bit", "boolean").then(DbColumnType.BOOLEAN))
            .test(TypeConverts.containsAny("decimal", "numeric", "number").then(DbColumnType.BIG_DECIMAL))
            .test(TypeConverts.contains("clob").then(DbColumnType.CLOB))
            .test(TypeConverts.contains("blob").then(DbColumnType.BYTE_ARRAY))
            .test(TypeConverts.contains("float").then(DbColumnType.FLOAT))
            .test(TypeConverts.containsAny("double", "real", "float4", "float8").then(DbColumnType.DOUBLE))
            .or(DbColumnType.STRING);
    }

    /**
     * 转换为日期类型
     *
     * @param config 配置信息
     * @param type   类型
     * @return 返回对应的列类型
     */
    private IColumnType toDateType(GlobalConfig config, String type) {
        DateType dateType = config.getDateType();
        if (dateType == DateType.SQL_PACK) {
            switch (type) {
                case "date":
                    return DbColumnType.DATE_SQL;
                case "time":
                    return DbColumnType.TIME;
                default:
                    return DbColumnType.TIMESTAMP;
            }
        } else if (dateType == DateType.TIME_PACK) {
            switch (type) {
                case "date":
                    return DbColumnType.LOCAL_DATE;
                case "time":
                    return DbColumnType.LOCAL_TIME;
                default:
                    return DbColumnType.LOCAL_DATE_TIME;
            }
        }
        return DbColumnType.DATE;
    }

}
