package com.nice.demo.service;

import com.nice.demo.pojo.TabArticleThumbs;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nice.demo.utils.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author nice
 * @since 2021-01-18
 */
public interface ITabArticleThumbsService extends IService<TabArticleThumbs> {

    Result giveTheThumbsUp(HttpServletRequest request, Integer articleId);

    boolean getStatus(HttpServletRequest request,Integer id);
}
