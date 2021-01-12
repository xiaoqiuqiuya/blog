package com.nice.demo.service.impl;

import com.nice.demo.pojo.Tag;
import com.nice.demo.mapper.TagMapper;
import com.nice.demo.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author nice
 * @since 2021-01-11
 */
@Service
public class TagService extends ServiceImpl<TagMapper, Tag> implements ITagService {

}
