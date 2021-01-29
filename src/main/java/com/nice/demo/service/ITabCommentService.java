package com.nice.demo.service;

import com.nice.demo.dao.TabCommentDao;
import com.nice.demo.pojo.TabComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nice.demo.utils.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author nice
 * @since 2021-01-21
 */
public interface ITabCommentService extends IService<TabComment> {

    Result publishComment(TabComment comment, HttpServletRequest request);

    List<TabCommentDao> finComments(Integer id,HttpServletRequest request);

    void updateLikeNum(Integer id, String decrease);
}
