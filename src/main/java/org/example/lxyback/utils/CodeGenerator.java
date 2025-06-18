package org.example.lxyback.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.sql.Types;
import java.util.Collections;

/**
 * mp代码生成器
 * by 程序员青
 * @since 2024 5 8
 */
public class CodeGenerator {
    public static void main(String[] args) {
        generate();
    }
    private static void generate(){
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/mybd?serverTimezone=GMT%2B8", "root", "wjw959599")
                .globalConfig(builder -> {
                    builder.author("LxyBack") // 设置作者
                            .fileOverride()
                            .outputDir("G:\\Mavensource\\LXYBack\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("org.example.lxyback") // 设置父包名
                            .moduleName(null) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "G:\\Mavensource\\LXYBack\\src\\main\\resources\\mapper\\")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok();
                    builder.controllerBuilder().enableHyphenStyle()//开启驼峰转字符
                                    .enableRestStyle();//开启@RequestController控制器
                    builder.addInclude("sys_dict") // 设置需要生成的表名
                            .addTablePrefix("t_", "sys_"); // 设置过滤表前缀
                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
