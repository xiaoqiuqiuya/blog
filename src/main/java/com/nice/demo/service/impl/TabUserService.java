package com.nice.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nice.demo.mapper.UserLoginMapper;
import com.nice.demo.pojo.TabUser;
import com.nice.demo.mapper.TabUserMapper;
import com.nice.demo.pojo.UserLogin;
import com.nice.demo.service.ITabUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nice.demo.utils.GetIpAddr;
import com.nice.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author nice
 * @since 2021-01-13
 */
@Service
public class TabUserService extends ServiceImpl<TabUserMapper, TabUser> implements ITabUserService {
    @Autowired
    TabUserMapper tabUserMapper;

    @Autowired
    UserLoginMapper userLoginMapper;

    @Override
    public Result loginUser(TabUser user, HttpServletRequest request) {
        Result result = new Result();
        if (user.getUserName() == null || "".equals(user.getUserName())) {
            result.setCode(401);
            result.setSuccess(false);
            result.setMessage("账号密码不能为空");
            return result;
        }
        if (user.getPassword() == null || "".equals(user.getPassword())) {
            result.setCode(401);
            result.setSuccess(false);
            result.setMessage("账号密码不能为空");
            return result;
        }
        Map<String, String> map = new HashMap<>();
        map.put("user_name", user.getUserName());
        map.put("password", user.getPassword());
//        条件构造器
        QueryWrapper<TabUser> wrapper = new QueryWrapper<>();
        wrapper.allEq(map);
        TabUser loginUser = tabUserMapper.selectOne(wrapper);
        if (loginUser == null) { //登录失败
            result.setCode(402);
            result.setSuccess(false);
            result.setMessage("请确认账号密码是否正确");
            return result;
        }
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("user", loginUser);
        result.setMessage("欢迎回来");
        result.setSuccess(true);
        result.setData(userMap);
        result.setCode(200);
        loginSuccess(loginUser.getId().toString(), request);
        return result;
    }

    // 临时登录
    @Override
    public void tempLogin(HttpServletRequest request) {
        // 直接登录成功
        loginSuccess("temp", request);
    }

    // 添加登录日志
    private void loginSuccess(String userId, HttpServletRequest request) {
        String ip = GetIpAddr.getIpAddr(request);
        if (userId == "temp" || userId.equals("temp")) {
            userId = ip;
        }
        UserLogin userLogin = new UserLogin();
        userLogin.setUserId(userId);
        userLogin.setIpAddr(ip);
        userLoginMapper.insert(userLogin);
    }
}
