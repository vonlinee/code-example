package io.devpl.codegen.jdbc.meta;

import io.devpl.codegen.jdbc.JdbcType;
import io.devpl.codegen.mbpg.config.DataSourceConfig;
import io.devpl.codegen.utils.StringPool;
import io.devpl.sdk.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * 数据库数据元包装类
 */
public class DatabaseMetaDataWrapper {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseMetaDataWrapper.class);

    private final DatabaseMetaData databaseMetaData;

    // TODO 暂时只支持一种
    private final String catalog;

    // TODO 暂时只支持一种
    private final String schema;

    public DatabaseMetaDataWrapper(DataSourceConfig dataSourceConfig) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            this.databaseMetaData = connection.getMetaData();
            this.catalog = connection.getCatalog();
            if (dataSourceConfig.getSchemaName() == null) {
                this.schema = connection.getSchema();
            } else {
                this.schema = dataSourceConfig.getSchemaName();
            }
            if (schema == null) {
                logger.warn("schema is null, {}", dataSourceConfig.getUrl());
            }
        } catch (SQLException e) {
            throw new RuntimeException("获取元数据错误:", e);
        }
    }

    public Map<String, Column> getColumnsInfo(String tableNamePattern, boolean queryPrimaryKey) {
        return getColumnsInfo(this.catalog, this.schema, tableNamePattern, queryPrimaryKey);
    }

    /**
     * 获取表字段信息
     * @return 表字段信息 (小写字段名->字段信息)
     */
    public Map<String, Column> getColumnsInfo(String catalog, String schema, String tableName, boolean queryPrimaryKey) {
        Set<String> primaryKeys = new HashSet<>();
        if (queryPrimaryKey) {
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
        }
        Map<String, Column> columnsInfoMap = new LinkedHashMap<>();
        try (ResultSet resultSet = databaseMetaData.getColumns(catalog, schema, tableName, "%")) {
            while (resultSet.next()) {
                Column column = new Column();
                String name = resultSet.getString("COLUMN_NAME");
                column.name = name;
                column.primaryKey = primaryKeys.contains(name);
                column.jdbcType = JdbcType.forCode(resultSet.getInt("DATA_TYPE"));
                column.length = resultSet.getInt("COLUMN_SIZE");
                column.scale = resultSet.getInt("DECIMAL_DIGITS");
                column.remarks = formatComment(resultSet.getString("REMARKS"));
                column.defaultValue = resultSet.getString("COLUMN_DEF");
                column.nullable = resultSet.getInt("NULLABLE") == DatabaseMetaData.columnNullable;
                try {
                    column.autoIncrement = "YES".equals(resultSet.getString("IS_AUTOINCREMENT"));
                } catch (SQLException sqlException) {
                    logger.warn("获取IS_AUTOINCREMENT出现异常:", sqlException);
                    // TODO 目前测试在oracle旧驱动下存在问题，降级成false.
                }
                columnsInfoMap.put(name.toLowerCase(), column);
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

    public List<Table> getTables(String tableNamePattern, String[] types) {
        return getTables(this.catalog, this.schema, tableNamePattern, types);
    }

    public List<Table> getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types) {
        List<Table> tables = new ArrayList<>();
        try (ResultSet resultSet = databaseMetaData.getTables(catalog, schemaPattern, tableNamePattern, types)) {
            Table table;
            while (resultSet.next()) {
                table = new Table();
                table.name = resultSet.getString("TABLE_NAME");
                table.remarks = formatComment(resultSet.getString("REMARKS"));
                table.tableType = resultSet.getString("TABLE_TYPE");
                tables.add(table);
            }
        } catch (SQLException e) {
            throw new RuntimeException("读取数据库表信息出现错误", e);
        }
        return tables;
    }

    public Table getTableInfo(String catalog, String schema, String tableName) {
        Table table = new Table();
        // TODO 后面要根据表是否为视图来查询，后面重构表查询策略。
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

    public static class Column {

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
