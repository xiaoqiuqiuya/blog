package com.nice.demo.service;

import com.nice.demo.pojo.TabReply;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nice.demo.utils.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author nice
 * @since 2021-01-21
 */
public interface ITabReplyService extends IService<TabReply> {

    Result postReply(TabReply reply, HttpServletRequest request);

    void updateLikeNum(Integer id, String option);
}
