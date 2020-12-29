package com.nice.demo.mapper;

import com.nice.demo.dao.TagDao;
import com.nice.demo.pojo.TagSort;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author nice
 * @since 2020-12-27
 */

public interface TagSortMapper extends BaseMapper<TagSort> {

    TagSort getDemo();
    List<TagDao> getTagList();
}
