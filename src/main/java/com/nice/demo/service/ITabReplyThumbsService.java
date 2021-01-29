package com.nice.demo.service;

import com.nice.demo.pojo.TabReplyThumbs;
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
public interface ITabReplyThumbsService extends IService<TabReplyThumbs> {

    Result likeReply(Integer id, HttpServletRequest request);
}
