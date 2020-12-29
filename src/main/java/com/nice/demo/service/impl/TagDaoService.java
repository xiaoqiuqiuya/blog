package com.nice.demo.service.impl;

import com.nice.demo.mapper.TagDaoMapper;
import com.nice.demo.pojo.Tag;
import com.nice.demo.service.ITagDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: nice
 * @date: 2020/12/24 17:41
 * @description:
 */
@Service
public class TagDaoService implements ITagDaoService {
    @Autowired
    TagDaoMapper tagDaoMapper;

    @Override
    public List<Tag> getTagDaos() {

        List<Tag> tagDaos = new ArrayList<>();
        tagDaos =tagDaoMapper.getTagDao();

        return tagDaos;
    }
}
