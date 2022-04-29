package io.maker.codegen.mbp.query;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import io.maker.codegen.mbp.config.GlobalConfig;
import io.maker.codegen.mbp.config.StrategyConfig;
import io.maker.codegen.mbp.config.builder.ConfigBuilder;
import io.maker.codegen.mbp.config.builder.Entity;
import io.maker.codegen.mbp.config.po.TableField;
import io.maker.codegen.mbp.config.po.TableInfo;
import io.maker.codegen.mbp.config.querys.DbQueryDecorator;
import io.maker.codegen.mbp.config.rules.IColumnType;
import io.maker.codegen.mbp.jdbc.DatabaseMetaDataWrapper;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 后面切换到元数据获取表与字段会移除这里
 *
 * @author nieqiurong 2021/1/6.
 * @since 3.5.0
 */
public class DefaultDatabaseQuery extends AbstractDatabaseQuery {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDatabaseQuery.class);

    private final StrategyConfig strategyConfig;

    private final GlobalConfig globalConfig;

    private final DbQueryDecorator dbQuery;

    private final DatabaseMetaDataWrapper databaseMetaDataWrapper;

    public DefaultDatabaseQuery(@NotNull ConfigBuilder configBuilder) {
        super(configBuilder);
        this.strategyConfig = configBuilder.getStrategyConfig();
        this.dbQuery = new DbQueryDecorator(dataSourceConfig, strategyConfig);
        this.globalConfig = configBuilder.getGlobalConfig();
        this.databaseMetaDataWrapper = new DatabaseMetaDataWrapper(dbQuery.getConnection());
    }

    @NotNull
    @Override
    public List<TableInfo> queryTables() {
        boolean isInclude = strategyConfig.getInclude().size() > 0;
        boolean isExclude = strategyConfig.getExclude().size() > 0;
        //所有的表信息
        List<TableInfo> tableList = new ArrayList<>();

        //需要反向生成或排除的表信息
        List<TableInfo> includeTableList = new ArrayList<>();
        List<TableInfo> excludeTableList = new ArrayList<>();
        try {
            dbQuery.execute(dbQuery.tablesSql(), result -> {
                String tableName = result.getStringResult(dbQuery.tableName());
                if (StringUtils.isNotBlank(tableName)) {
                    DatabaseMetaDataWrapper.Table table = databaseMetaDataWrapper.getTableInfo(tableName);
                    TableInfo tableInfo = new TableInfo(this.configBuilder, tableName);
                    // 跳过视图
                    if (!(strategyConfig.isSkipView() && table.isView())) {
                        tableInfo.setComment(table.getRemarks());
                        if (isInclude && strategyConfig.matchIncludeTable(tableName)) {
                            includeTableList.add(tableInfo);
                        } else if (isExclude && strategyConfig.matchExcludeTable(tableName)) {
                            excludeTableList.add(tableInfo);
                        }
                        tableList.add(tableInfo);
                    }
                }
            });
            if (isExclude || isInclude) {
                Map<String, String> notExistTables = new HashSet<>(isExclude ? strategyConfig.getExclude() : strategyConfig.getInclude())
                    .stream()
                    .filter(s -> !ConfigBuilder.matcherRegTable(s))
                    .collect(Collectors.toMap(String::toLowerCase, s -> s, (o, n) -> n));
                // 将已经存在的表移除，获取配置中数据库不存在的表
                for (TableInfo tabInfo : tableList) {
                    if (notExistTables.isEmpty()) {
                        break;
                    }
                    //解决可能大小写不敏感的情况导致无法移除掉
                    notExistTables.remove(tabInfo.getName().toLowerCase());
                }
                if (notExistTables.size() > 0) {
                    LOGGER.warn("表[{}]在数据库中不存在！！！", String.join(StringPool.COMMA, notExistTables.values()));
                }
                // 需要反向生成的表信息
                if (isExclude) {
                    tableList.removeAll(excludeTableList);
                } else {
                    tableList.clear();
                    tableList.addAll(includeTableList);
                }
            }
            // 性能优化，只处理需执行表字段 https://github.com/baomidou/mybatis-plus/issues/219
            tableList.forEach(this::convertTableFields);
            return tableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // 数据库操作完成,释放连接对象
            dbQuery.closeConnection();
        }
    }

    private void convertTableFields(@NotNull TableInfo tableInfo) {
        String tableName = tableInfo.getName();
        try {
            final Map<String, DatabaseMetaDataWrapper.ColumnsInfo> columnsMetaInfoMap = new HashMap<>();
            //TODO 增加元数据信息获取,后面查询表字段要改成这个.
            Map<String, DatabaseMetaDataWrapper.ColumnsInfo> columnsInfoMap =
                databaseMetaDataWrapper.getColumnsInfo(tableName);
            if (columnsInfoMap != null && !columnsInfoMap.isEmpty()) {
                columnsMetaInfoMap.putAll(columnsInfoMap);
            }
            String tableFieldsSql = dbQuery.tableFieldsSql(tableName);
            Entity entity = strategyConfig.entity();
            dbQuery.execute(tableFieldsSql, result -> {
                String columnName = result.getStringResult(dbQuery.fieldName());
                TableField field = new TableField(this.configBuilder, columnName);
                // 避免多重主键设置，目前只取第一个找到ID，并放到list中的索引为0的位置
                DatabaseMetaDataWrapper.ColumnsInfo columnsInfo = columnsMetaInfoMap.get(columnName.toLowerCase());
                // 处理ID
                if (columnsInfo.isPrimaryKey()) {
                    field.primaryKey(columnsInfo.isAutoIncrement());
                    tableInfo.setHavePrimaryKey(true);
                    if (field.isKeyIdentityFlag() && entity.getIdType() != null) {
                        LOGGER.warn("当前表[{}]的主键为自增主键，会导致全局主键的ID类型设置失效!", tableName);
                    }
                }
                field.setColumnName(columnName)
                    .setType(result.getStringResult(dbQuery.fieldType()))
                    .setComment(columnsInfo.getRemarks())
                    .setCustomMap(dbQuery.getCustomFields(result.getResultSet()));
                String propertyName = entity.getNameConvert().propertyNameConvert(field);
                IColumnType columnType = dataSourceConfig.getTypeConvert().processTypeConvert(globalConfig, field);
                field.setPropertyName(propertyName, columnType);
                field.setMetaInfo(new TableField.MetaInfo(columnsInfo));
                tableInfo.addField(field);
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tableInfo.processTable();
    }

}
