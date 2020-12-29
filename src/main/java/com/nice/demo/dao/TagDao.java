package com.nice.demo.dao;

import com.nice.demo.pojo.Tag;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author: nice
 * @date: 2020/12/24 17:34
 * @description:
 */
@Data
@ToString
public class TagDao {
    private Integer id;//tagID
    private String name;//tag分类
    private List<Tag> tagList;//tag列表
}
