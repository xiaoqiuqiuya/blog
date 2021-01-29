package com.nice.demo.mapper;

import com.nice.demo.pojo.TabComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author nice
 * @since 2021-01-21
 */
public interface TabCommentMapper extends BaseMapper<TabComment> {

    List<TabComment> getCommentsDetails(Integer id);
}
