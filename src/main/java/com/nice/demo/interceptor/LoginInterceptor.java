package com.nice.demo.interceptor;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 用户进入发布页面进行拦截
         * 若没有登录则跳回到登录页面
         */
        System.out.println("正在进行拦截发布文章请求");
        Object loginUser = request.getSession().getAttribute("user");
        if (!StringUtils.isEmpty(loginUser)) {
            return true;
        } else {
            request.getRequestDispatcher("login.html").forward(request,response);
            return false;
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
