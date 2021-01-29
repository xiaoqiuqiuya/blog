package com.nice.demo.service;

import com.nice.demo.pojo.TabCommentThumbs;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nice.demo.utils.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author nice
 * @since 2021-01-27
 */
public interface ITabCommentThumbsService extends IService<TabCommentThumbs> {

    Result likeComment(Integer id, HttpServletRequest request);
}
