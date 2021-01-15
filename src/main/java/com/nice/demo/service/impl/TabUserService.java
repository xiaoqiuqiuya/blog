package com.nice.demo.service.impl;

import com.nice.demo.pojo.TabUser;
import com.nice.demo.mapper.TabUserMapper;
import com.nice.demo.service.ITabUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author nice
 * @since 2021-01-13
 */
@Service
public class TabUserService extends ServiceImpl<TabUserMapper, TabUser> implements ITabUserService {

}
