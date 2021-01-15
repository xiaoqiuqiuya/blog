package com.nice.demo;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;

/**
 * @author: nice
 * @date: 2020/12/24 16:32
 * @description:mybatis-plus 代码生成器
 */
public class CodeProduction {
    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();

//        配置策略
//        1、全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("nice");
        gc.setOpen(false);
        gc.setFileOverride(false);
        gc.setServiceImplName("%service");
        gc.setDateType(DateType.ONLY_DATE);
        gc.setSwagger2(true);
        mpg.setGlobalConfig(gc);

//        设置数据源
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost/blog?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

//        包的配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("demo");
        pc.setParent("com.nice");
        pc.setEntity("pojo");
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);

//        策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setInclude("view_history"); //需要映射的表
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setEntityLombokModel(true);

//        自动填充策略
//        strategyConfig.setLogicDeleteFieldName("deleted");
//        strategyConfig.setVersionFieldName("version");
        mpg.setStrategy(strategyConfig);

        mpg.execute();


    }
}
