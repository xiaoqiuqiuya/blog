package com.nice.demo.service;

import com.nice.demo.pojo.TabArticle;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nice.demo.utils.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author nice
 * @since 2020-12-30
 */
public interface ITabArticleService extends IService<TabArticle> {

    Result ifNull(TabArticle tabArticle);

    int postOption(TabArticle tabArticle);

    List<TabArticle> getArticles(int current, int size);

    List<TabArticle> getHotArticle(int current, int size);

    List<TabArticle> getRelatedArticle(String[] tags,Integer currentId);
}
