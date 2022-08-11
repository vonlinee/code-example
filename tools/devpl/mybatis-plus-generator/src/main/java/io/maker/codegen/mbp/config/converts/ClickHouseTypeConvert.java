package io.maker.codegen.mbp.config.converts;

import static io.maker.codegen.mbp.config.converts.TypeConverts.contains;
import static io.maker.codegen.mbp.config.converts.TypeConverts.containsAny;
import static io.maker.codegen.mbp.config.rules.DbColumnType.*;

import org.jetbrains.annotations.NotNull;

import io.maker.codegen.mbp.config.GlobalConfig;
import io.maker.codegen.mbp.config.ITypeConvert;
import io.maker.codegen.mbp.config.rules.DbColumnType;
import io.maker.codegen.mbp.config.rules.IColumnType;

/**
 * ClickHouse 字段类型转换
 *
 * @author urzeye
 * @date 2021年9月12日
 */
public class ClickHouseTypeConvert implements ITypeConvert {

    public static final ClickHouseTypeConvert INSTANCE = new ClickHouseTypeConvert();

    static final String[] INTEGER_TYPE = new String[]{
        "intervalyear", "intervalquarter", "intervalmonth", "intervalweek",
        "intervalday", "intervalhour", "intervalminute", "intervalsecond",
        "uint16", "uint8", "int16", "int8", "int32"
    };

    static final String[] BIGINTEGER_TYPE = new String[]{
        "uint256", "uint128", "uint64", "int256", "int128"
    };

    static final String[] BIGDECIMAL_TYPE = new String[]{
        "decimal32", "decimal64", "decimal128", "decimal256", "decimal"
    };

    static final String[] LONG_TYPE = new String[]{
        "int64", "uint32"
    };

    static final String[] STRING_TYPE = new String[]{
        "uuid", "char", "varchar", "text", "tinytext", "longtext", "blob", "tinyblob", "mediumblob", "longblob",
        "enum8", "enum16", "ipv4", "ipv6", "string", "fixedstring", "nothing", "nested", "tuple", "aggregatefunction", "unknown"
    };


    @Override
    public IColumnType processTypeConvert(@NotNull GlobalConfig globalConfig, @NotNull String fieldType) {
        return TypeConverts.use(fieldType)
            .test(containsAny(INTEGER_TYPE).then(INTEGER))
            .test(containsAny(BIGINTEGER_TYPE).then(BIG_INTEGER))
            .test(containsAny(BIGDECIMAL_TYPE).then(BIG_DECIMAL))
            .test(containsAny(LONG_TYPE).then(LONG))
            .test(contains("float32").then(FLOAT))
            .test(contains("float64").then(DOUBLE))
            .test(contains("map").then(MAP))
            .test(contains("array").then(OBJECT))
            .test(containsAny("date", "datetime", "datetime64").then(t -> toDateType(globalConfig, fieldType)))
            .test(containsAny(STRING_TYPE).then(STRING))
            .or(STRING);
    }

    /**
     * 转换为日期类型
     *
     * @param config 配置信息
     * @param type   类型
     * @return 返回对应的列类型
     */
    public static IColumnType toDateType(GlobalConfig config, String type) {
        switch (config.getDateType()) {
            case SQL_PACK:
                if ("date".equals(type)) {
                    return DbColumnType.DATE_SQL;
                }
                return DbColumnType.TIMESTAMP;
            case TIME_PACK:
                if ("date".equals(type)) {
                    return DbColumnType.LOCAL_DATE;
                }
                return DbColumnType.LOCAL_DATE_TIME;
            default:
                return DbColumnType.DATE;
        }
    }

}