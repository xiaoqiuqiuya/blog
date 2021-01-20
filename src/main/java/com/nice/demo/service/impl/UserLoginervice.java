package com.nice.demo.service.impl;

import com.nice.demo.pojo.UserLogin;
import com.nice.demo.mapper.UserLoginMapper;
import com.nice.demo.service.IUserLoginService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author nice
 * @since 2021-01-18
 */
@Service
public class UserLoginervice extends ServiceImpl<UserLoginMapper, UserLogin> implements IUserLoginService {

}
