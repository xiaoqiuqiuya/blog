package com.nice.demo.mapper;

import com.nice.demo.pojo.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 杨锦亮
 * @since 2020-11-04
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}