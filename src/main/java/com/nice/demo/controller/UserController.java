package com.nice.demo.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nice.demo.pojo.User;
import com.nice.demo.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 杨锦亮
 * @since 2020-11-04
 */
@Controller
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @PostMapping("login")
    public String login(User user, HttpServletRequest request, Model model){
//        条件构造器
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",user.getUserName());
        wrapper.eq("password",user.getPassword());
        User loginUser = userService.getOne(wrapper);

        if (loginUser != null) {
            request.getSession().setAttribute("loginUser",loginUser.getId());
            System.out.println("登录成功");
            return "article";
        }else {
            System.out.println("登录失败");
            model.addAttribute("msg","error请确认账号密码是否正确");
            return "login";
        }
    }
}

