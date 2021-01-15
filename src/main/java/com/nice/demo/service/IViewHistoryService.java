package com.nice.demo.service;

import com.nice.demo.pojo.ViewHistory;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author nice
 * @since 2021-01-13
 */
public interface IViewHistoryService extends IService<ViewHistory> {

    void increaseView(String articleId,String ip);
}
