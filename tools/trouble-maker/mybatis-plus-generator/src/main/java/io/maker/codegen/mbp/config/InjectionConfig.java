package io.maker.codegen.mbp.config;

import org.jetbrains.annotations.NotNull;

import io.maker.codegen.mbp.config.po.TableInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * 注入配置
 *
 * @author hubin
 * @since 2016-12-07
 */
public class InjectionConfig {

    /**
     * 输出文件之前消费者
     */
    private BiConsumer<TableInfo, Map<String, Object>> beforeOutputFileBiConsumer;

    /**
     * 自定义配置 Map 对象
     */
    private Map<String, Object> customMap = new HashMap<>();

    /**
     * 自定义模板文件，key为文件名称，value为模板路径
     */
    private Map<String, String> customFile = new HashMap<>();

    /**
     * 是否覆盖已有文件（默认 false）
     *
     * @since 3.5.2
     */
    private boolean fileOverride;

    public void beforeOutputFile(TableInfo tableInfo, Map<String, Object> objectMap) {
        if (!customMap.isEmpty()) {
            objectMap.putAll(customMap);
        }
        if (null != beforeOutputFileBiConsumer) {
            beforeOutputFileBiConsumer.accept(tableInfo, objectMap);
        }
    }

    @NotNull
    public Map<String, Object> getCustomMap() {
        return customMap;
    }

    @NotNull
    public Map<String, String> getCustomFile() {
        return customFile;
    }

    public boolean isFileOverride() {
        return fileOverride;
    }

    /**
     * 构建者
     */
    public static class Builder implements IConfigBuilder<InjectionConfig> {

        private final InjectionConfig injectionConfig;

        public Builder() {
            this.injectionConfig = new InjectionConfig();
        }

        /**
         * 输出文件之前消费者
         *
         * @param biConsumer 消费者
         * @return this
         */
        public Builder beforeOutputFile(@NotNull BiConsumer<TableInfo, Map<String, Object>> biConsumer) {
            this.injectionConfig.beforeOutputFileBiConsumer = biConsumer;
            return this;
        }

        /**
         * 自定义配置 Map 对象
         *
         * @param customMap Map 对象
         * @return this
         */
        public Builder customMap(@NotNull Map<String, Object> customMap) {
            this.injectionConfig.customMap = customMap;
            return this;
        }

        /**
         * 自定义配置模板文件
         *
         * @param customFile key为文件名称，value为文件路径
         * @return this
         */
        public Builder customFile(@NotNull Map<String, String> customFile) {
            this.injectionConfig.customFile = customFile;
            return this;
        }

        /**
         * 覆盖已有文件
         */
        public Builder fileOverride() {
            this.injectionConfig.fileOverride = true;
            return this;
        }

        @Override
        public InjectionConfig build() {
            return this.injectionConfig;
        }
    }
}