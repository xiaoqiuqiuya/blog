package com.nice.demo.utils;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: nice
 * @date: 2020/12/30 16:45
 * @description: 插入时填充
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    // 插入时自动填充
    @Override
    public void insertFill(MetaObject metaObject) {
        System.out.println(new Date());
        this.setFieldValByName("gmtCreate", new Date(), metaObject);
        this.setFieldValByName("gmtModified", new Date(), metaObject);
        this.setFieldValByName("gmtView", new Date(), metaObject);

    }

    // 修改时自动填充
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("gmt_modified", new Date(), metaObject);
        this.setFieldValByName("gmtView", new Date(), metaObject);

    }
}
