package com.nice.demo.mapper;

import com.nice.demo.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: nice
 * @date: 2020/12/25 16:28
 * @description:
 */

@Repository
@Mapper
public interface TagDaoMapper{
    @Select("SELECT * FROM  tag")
    List<Tag> getTagDao();

}
