package io.devpl.toolkit.dto;

import lombok.Data;

import java.util.List;

/**
 * 定义生成代码时每次可能发生变化的一些配置项目
 */
@Data
public class GenSetting {

    /**
     * 需要生成的文件类型
     */
    private List<String> choosedOutputFiles;

    /**
     * 文件存在时是否覆盖
     */
    private boolean override;

    /**
     * 注释的作者
     */
    private String author;

    /**
     * 功能模块名
     */
    private String moduleName;

    /**
     * 基础包名，一般是公司域名反过来 + 项目名称
     */
    private String basePackageName;

    /**
     * 临时模板参数，由用户自行输入的临时参数，用于控制生成文件的可选部分
     */
    private List<String> choosedControllerMethods;

    /**
     * 目标项目根目录
     */
    private String rootPath;

    private String connectionName;
    private String databaseName;
}
