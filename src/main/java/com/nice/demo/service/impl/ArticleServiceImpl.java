package com.nice.demo.service.impl;

import com.nice.demo.pojo.Article;
import com.nice.demo.mapper.ArticleMapper;
import com.nice.demo.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 杨锦亮
 * @since 2020-11-04
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

}
