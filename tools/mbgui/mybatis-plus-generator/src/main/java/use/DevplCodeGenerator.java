package use;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.builder.Entity;
import com.baomidou.mybatisplus.generator.config.builder.Mapper;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * 基于模板的代码生成
 * mybatis-plus generator 3.5.2版本
 * 官方文档：<a href="https://github.com/baomidou/generator">...</a>
 */
public class DevplCodeGenerator {

    private static final String author = "Author";
    /**
     * 输出根目录
     */
    private static final String outputRootDir = "D:\\Temp";
    // 父包配置
    private static final String parentPackage = "org.exmaple.test";
    // 要生成的表名列表
    private static final List<String> tableNamesToBeGenerated = new ArrayList<>();

    // 在此处填写要生成的表名
    private static void tableNamesToBeGenerated() {
        tableNamesToBeGenerated.add("user");
    }

    public static void main(String[] args) throws IOException {
        tableNamesToBeGenerated();
        URL resource = Thread.currentThread().getContextClassLoader().getResource("jdbc.properties");
        if (resource == null) {
            throw new RuntimeException("数据库连接配置文件[resources/jdbc.properties]不存在");
        }
        Properties properties = new Properties();
        try (InputStream inputStream = resource.openStream()) {
            properties.load(inputStream);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        String url = properties.getProperty("jdbc.url");
        String username = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");
        System.out.println(url);
        FastAutoGenerator.create(url, username, password).globalConfig(builder -> {
                    builder.author(author); // 设置作者名 默认值:作者
                    builder.fileOverride();
                    // .enableSwagger() // 开启 swagger 模式
                    // .enableSpringdoc()  // 开启 springdoc 模式  @Schema注解
                    builder.dateType(DateType.TIME_PACK);  // 时间策略
                    builder.commentDate("yyyy-MM-dd HH:mm:ss");// 注释日期 默认值: yyyy-MM-dd
                    builder.outputDir(outputRootDir); // 指定输出根目录 默认值: windows:D:// linux or mac : /tmp
                }).packageConfig(builder -> {
                    // 包配置(PackageConfig)
                    builder.parent(parentPackage); // 设置父包名
                    builder.moduleName(""); // 设置父包模块名
                    builder.entity("");  // Entity 包名
                    builder.service("");  // Service 包名

                    Map<OutputFile, String> pathInfoMap = new HashMap<>();
                    pathInfoMap.put(OutputFile.parent, outputRootDir);
                    pathInfoMap.put(OutputFile.xml, outputRootDir + "/mapping");
                    pathInfoMap.put(OutputFile.entity, outputRootDir + "/entity");
                    pathInfoMap.put(OutputFile.service, outputRootDir + "/service");
                    pathInfoMap.put(OutputFile.serviceImpl, outputRootDir + "/service/impl");
                    builder.pathInfo(pathInfoMap); // 设置mapperXml生成路径
                }).injectionConfig(builder -> {
                    // 每个表生成之前都会调用beforeOutputFile方法
                    // 参数1:tableInfo，表信息
                    // 参数2:所有的模板参数
                    builder.beforeOutputFile((tableInfo, objectMap) -> {
                        System.out.println(tableInfo);
                        objectMap.entrySet().forEach(entry -> {
                            System.out.println(entry.getKey() + " " + entry.getValue());
                        });
                    });

                }).strategyConfig(builder -> {
                    builder.enableSkipView(); // 开启大写命名
                    // builder.enableSchema(); // 启用 schema
                    builder.addTablePrefix("");
                    builder.addTableSuffix("");
                    builder.addInclude(tableNamesToBeGenerated); // 设置需要生成的表名

                    // Controller
                    builder.controllerBuilder().enableFileOverride();

                    // Service策略配置
                    builder.serviceBuilder().enableFileOverride();

                    // Entity策略配置
                    Entity.Builder entityBuilder = builder.entityBuilder();
                    entityBuilder.enableFileOverride();  // 文件覆盖
                    entityBuilder.enableLombok();  // 使用Lombok
                    entityBuilder.enableTableFieldAnnotation(); // 字段添加TableField注解
                    entityBuilder.mapperBuilder();
                    entityBuilder.enableFileOverride();
                    // Mapper配置
                    Mapper.Builder mapperBuilder = entityBuilder.mapperBuilder();
                    mapperBuilder.enableBaseResultMap();
                    mapperBuilder.enableFileOverride();
                    mapperBuilder.enableBaseResultMap(); // 生成默认的ResultMap标签
                }).templateConfig(templateConfig -> {
                    // 禁用模板
                    templateConfig.disable(TemplateType.ENTITY);
                    templateConfig.disable(TemplateType.CONTROLLER);
                    templateConfig.disable(TemplateType.MAPPER);
                    templateConfig.disable(TemplateType.SERVICE);
                }).templateEngine(new VelocityTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
