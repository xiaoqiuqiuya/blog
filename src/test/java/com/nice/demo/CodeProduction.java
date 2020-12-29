package com.nice.demo;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

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
        gc.setServiceImplName("%"+"Service");
        gc.setIdType(IdType.ID_WORKER);
        gc.setDateType(DateType.ONLY_DATE);
        gc.setSwagger2(true);
        mpg.setGlobalConfig(gc);

//        设置数据源
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/blog?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC");
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
        strategyConfig.setInclude("tag_sort");
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setEntityLombokModel(true);

//        自动填充策略


        mpg.setStrategy(strategyConfig);

        mpg.execute();








    }
}
