package com.nice.demo.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nice.demo.pojo.TabUser;
import com.nice.demo.service.impl.TabUserService;
import com.nice.demo.utils.GetIpAddr;
import com.nice.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author nice
 * @since 2021-01-13
 */
@Controller
@RequestMapping("/user")
public class TabUserController {
    @Autowired
    TabUserService tabUserService;
    // 通过账号密码进行登录
    @PostMapping("login")
    @ResponseBody
    public String login(
            @RequestBody(required = false) TabUser user,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model) {
        // 获取登录结果
        Result result = tabUserService.loginUser(user, request);
        // 判断登录结果
        if (result.getCode() == 200) {//登录成功
            //获取登录的对象
            Map<String, Object> map = result.getData();
            TabUser tabUser = (TabUser) map.get("user");
            request.getSession().setAttribute("user", tabUser.getId());
        }
        JSONObject obj = new JSONObject();
        obj.put("result", result);
        return obj.toString();
    }
    // 临时登录
    @GetMapping("login")
    public String login(HttpServletRequest request){
        tabUserService.tempLogin(request);
        // 持久化登录
        request.getSession().setAttribute("user", GetIpAddr.getIpAddr(request));
        return "redirect:/article";
    }




}

