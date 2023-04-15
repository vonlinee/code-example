package io.devpl.codegen.api;

/**
 * 名称转换接口类
 */
public interface INameConvert {

    /**
     * 执行实体名称转换
     * @param tableInfo 表信息对象
     * @return
     */
    String entityNameConvert(IntrospectedTable tableInfo);

    /**
     * 执行属性名称转换
     * @param fieldName 表字段对象，如果属性表字段命名不一致注意 convert 属性的设置
     * @return 字段名
     */
    String propertyNameConvert(String fieldName);
}
