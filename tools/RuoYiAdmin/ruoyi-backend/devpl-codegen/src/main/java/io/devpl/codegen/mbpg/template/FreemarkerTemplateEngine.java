package io.devpl.codegen.mbpg.template;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import io.devpl.codegen.mbpg.config.ConstVal;
import io.devpl.codegen.mbpg.config.builder.ConfigBuilder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;

/**
 * Freemarker 模板引擎实现文件输出
 */
public class FreemarkerTemplateEngine extends AbstractTemplateEngine {
    private Configuration configuration;

    @Override
    public @NotNull FreemarkerTemplateEngine init(@NotNull ConfigBuilder configBuilder) {
        configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        configuration.setDefaultEncoding(ConstVal.UTF8);
        configuration.setClassForTemplateLoading(FreemarkerTemplateEngine.class, StringPool.SLASH);
        return this;
    }

    @Override
    public void writer(@NotNull Map<String, Object> objectMap, @NotNull String templatePath, @NotNull File outputFile) throws Exception {
        Template template = configuration.getTemplate(templatePath);
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
            template.process(objectMap, new OutputStreamWriter(fileOutputStream, ConstVal.UTF8));
        }
        LOGGER.debug("模板:" + templatePath + ";  文件:" + outputFile);
    }

    @Override
    public @NotNull String templateFilePath(@NotNull String filePath) {
        return filePath + ".ftl";
    }
}
