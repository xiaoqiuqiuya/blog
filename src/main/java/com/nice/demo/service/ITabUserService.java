package com.nice.demo.service;

import com.nice.demo.pojo.TabUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nice.demo.utils.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author nice
 * @since 2021-01-13
 */
public interface ITabUserService extends IService<TabUser> {

    Result loginUser(TabUser user, HttpServletRequest request);

    void tempLogin(HttpServletRequest request);
}
