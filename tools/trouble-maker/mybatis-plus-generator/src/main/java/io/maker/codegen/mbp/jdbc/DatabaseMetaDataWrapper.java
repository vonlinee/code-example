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
package io.maker.codegen.mbp.jdbc;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author nieqiurong 2021/2/8.
 * @since 3.5.0
 */
public class DatabaseMetaDataWrapper {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseMetaDataWrapper.class);

    private final DatabaseMetaData databaseMetaData;

    //TODO 暂时只支持一种
    private final String catalog;

    //TODO 暂时只支持一种
    private final String schema;

    public DatabaseMetaDataWrapper(Connection connection) {
        try {
            this.databaseMetaData = connection.getMetaData();
            this.catalog = connection.getCatalog();
            this.schema = connection.getSchema();
        } catch (SQLException e) {
            throw new RuntimeException("获取元数据错误:", e);
        }
    }

    public Map<String, ColumnsInfo> getColumnsInfo(String tableNamePattern) throws SQLException {
        return getColumnsInfo(this.catalog, this.schema, tableNamePattern);
    }

    /**
     * 获取表字段信息
     *
     * @return 表字段信息 (小写字段名->字段信息)
     */
    public Map<String, ColumnsInfo> getColumnsInfo(String catalog, String schema, String tableName) throws SQLException {
        Set<String> primaryKeys = new HashSet<>();
        try (ResultSet primaryKeysResultSet = databaseMetaData.getPrimaryKeys(catalog, schema, tableName)) {
            while (primaryKeysResultSet.next()) {
                String columnName = primaryKeysResultSet.getString("COLUMN_NAME");
                primaryKeys.add(columnName);
            }
            if (primaryKeys.size() > 1) {
                logger.warn("当前表:{}，存在多主键情况！", tableName);
            }
        } catch (SQLException e) {
            throw new RuntimeException("读取表主键信息:" + tableName + "错误:", e);
        }

        Map<String, ColumnsInfo> columnsInfoMap = new HashMap<>();
        try (ResultSet resultSet = databaseMetaData.getColumns(catalog, schema, tableName, "%")) {
            while (resultSet.next()) {
                ColumnsInfo columnsInfo = new ColumnsInfo();
                String name = resultSet.getString("COLUMN_NAME");
                columnsInfo.name = name;
                columnsInfo.primaryKey = primaryKeys.contains(name);
                columnsInfo.jdbcType = JdbcType.forCode(resultSet.getInt("DATA_TYPE"));
                columnsInfo.length = resultSet.getInt("COLUMN_SIZE");
                columnsInfo.scale = resultSet.getInt("DECIMAL_DIGITS");
                columnsInfo.remarks = formatComment(resultSet.getString("REMARKS"));
                columnsInfo.defaultValue = resultSet.getString("COLUMN_DEF");
                columnsInfo.nullable = resultSet.getInt("NULLABLE") == DatabaseMetaData.columnNullable;
                columnsInfo.autoIncrement = "YES".equals(resultSet.getString("IS_AUTOINCREMENT"));
                columnsInfoMap.put(name.toLowerCase(), columnsInfo);
            }
            return Collections.unmodifiableMap(columnsInfoMap);
        } catch (SQLException e) {
            throw new RuntimeException("读取表字段信息:" + tableName + "错误:", e);
        }
    }

    public String formatComment(String comment) {
        return StringUtils.isBlank(comment) ? StringPool.EMPTY : comment.replaceAll("\r\n", "\t");
    }

    public Table getTableInfo(String tableName) {
        return getTableInfo(this.catalog, this.schema, tableName);
    }

    public Table getTableInfo(String catalog, String schema, String tableName) {
        Table table = new Table();
        //TODO 后面要根据表是否为试图来查询，后面重构表查询策略。
        try (ResultSet resultSet = databaseMetaData.getTables(catalog, schema, tableName, new String[]{"TABLE", "VIEW"})) {
            table.name = tableName;
            while (resultSet.next()) {
                table.remarks = formatComment(resultSet.getString("REMARKS"));
                table.tableType = resultSet.getString("TABLE_TYPE");
            }
        } catch (SQLException e) {
            throw new RuntimeException("读取表信息:" + tableName + "错误:", e);
        }
        return table;
    }

    public static class Table {

        private String name;

        private String remarks;

        private String tableType;

        public String getRemarks() {
            return remarks;
        }

        public String getTableType() {
            return tableType;
        }

        public String getName() {
            return name;
        }

        public boolean isView() {
            return "VIEW".equals(tableType);
        }

    }

    public static class ColumnsInfo {

        private boolean primaryKey;

        private boolean autoIncrement;

        private String name;

        private int length;

        private boolean nullable;

        private String remarks;

        private String defaultValue;

        private int scale;

        private JdbcType jdbcType;

        public String getName() {
            return name;
        }

        public int getLength() {
            return length;
        }

        public boolean isNullable() {
            return nullable;
        }

        public String getRemarks() {
            return remarks;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        public int getScale() {
            return scale;
        }

        public JdbcType getJdbcType() {
            return jdbcType;
        }

        public boolean isPrimaryKey() {
            return primaryKey;
        }

        public boolean isAutoIncrement() {
            return autoIncrement;
        }

    }
}
