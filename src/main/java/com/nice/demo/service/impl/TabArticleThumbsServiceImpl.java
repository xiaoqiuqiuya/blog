package com.nice.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nice.demo.mapper.TabArticleMapper;
import com.nice.demo.pojo.TabArticle;
import com.nice.demo.pojo.TabArticleThumbs;
import com.nice.demo.mapper.TabArticleThumbsMapper;
import com.nice.demo.service.ITabArticleThumbsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nice.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author nice
 * @since 2021-01-18
 */
@Service
public class TabArticleThumbsServiceImpl extends ServiceImpl<TabArticleThumbsMapper, TabArticleThumbs> implements ITabArticleThumbsService {
    @Autowired
    TabArticleThumbsMapper thumbsMapper;

    @Autowired
    TabArticleService articleService;

    @Override
    public Result giveTheThumbsUp(HttpServletRequest request, Integer articleId) {
        Result result = new Result();
        //获取登录状态
        Object user = request.getSession().getAttribute("user");
        if (StringUtils.isEmpty(user)) {
            //未登录
            result.setCode(403);
            result.message("请登录后操作");
            result.setSuccess(false);
            return result;
        }
        // 配置查询条件
        Map<String, String> map = new HashMap<>();
        map.put("user_id", user.toString());
        map.put("article_id", articleId.toString());
        QueryWrapper<TabArticleThumbs> wrapper = new QueryWrapper<>();
        wrapper.allEq(map);
        //构造对象
        TabArticleThumbs tabArticleThumbs = new TabArticleThumbs();
        tabArticleThumbs.setArticleId(articleId);
        tabArticleThumbs.setUserId(user.toString());
        //根据状态status判断当前操作是为点赞或者取消点赞
        List<TabArticleThumbs> list = thumbsMapper.selectList(wrapper);
        //数据库不存在该条数据，视为第一次点赞
        if (list.size() == 0) {
            // 添加数据库记录
            thumbsMapper.insert(tabArticleThumbs);
            // 修改文章点赞数 +
            articleService.updateLikeNum(articleId,"increase");
            result.setCode(200);
            result.message("点赞成功");
            result.setSuccess(true);
            return result;
        }
        // 遍历结果集，正常情况下应该只有一条数据
        for (TabArticleThumbs articleThumbs : list) {
            if (articleThumbs.getStatus()) {  //1
                //当前状态为取消点赞--1
                tabArticleThumbs.setStatus(false);
                result.message("再次点赞成功");
                // 修改文章点赞数 +
                articleService.updateLikeNum(articleId,"increase");

            } else {
                // 当前状态为点赞--0
                tabArticleThumbs.setStatus(true);
                result.message("取消点赞成功");
                // 修改文章点赞数 -
                articleService.updateLikeNum(articleId,"decrease");

            }
            // 更新数据库
            thumbsMapper.update(tabArticleThumbs, wrapper);
        }
        result.setCode(200);
        result.setSuccess(true);
        return result;
    }

    //获取文章的点赞状态
    @Override
    public boolean getStatus(HttpServletRequest request, Integer articleId) {
        Object user = request.getSession().getAttribute("user");
        if (StringUtils.isEmpty(user)) {
            //未登录
            return false;
        }
        Map<String, String> map = new HashMap<>();
        map.put("user_id", user.toString());
        map.put("article_id", articleId.toString());
        QueryWrapper<TabArticleThumbs> wrapper = new QueryWrapper<>();
        wrapper.allEq(map);
        List<TabArticleThumbs> list = thumbsMapper.selectList(wrapper);
        if (list.size() == 0) {
            // 没有点赞
            return false;
        }
        boolean flag = false;
        for (TabArticleThumbs thumbs : list) {
            if (!thumbs.getStatus()) {
                // 0 已点赞
                flag = true;
            }
        }
        return flag;
    }
}
