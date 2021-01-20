package com.nice.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nice.demo.dao.TagDao;
import com.nice.demo.mapper.TabArticleMapper;
import com.nice.demo.mapper.TagMapper;
import com.nice.demo.mapper.TagSortMapper;
import com.nice.demo.pojo.TabArticle;
import com.nice.demo.pojo.TabUser;
import com.nice.demo.pojo.Tag;
import com.nice.demo.service.impl.TabArticleService;
import com.nice.demo.service.impl.TabArticleThumbsServiceImpl;
import com.nice.demo.service.impl.TabUserService;
import com.nice.demo.service.impl.ViewHistoryService;
import com.nice.demo.utils.GetIpAddr;
import com.nice.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author nice
 * @since 2020-12-30
 */
@Controller
@RestController

public class TabArticleController {
    @Autowired
    TagSortMapper tagSortMapper;
    @Autowired
    TagMapper tagMapper;
    @Autowired
    TabArticleMapper tabArticleMapper;
    @Autowired
    TabArticleService tabArticleService;
    @Autowired
    TabUserService tabUserService;
    @Autowired
    ViewHistoryService historyService;
    @Autowired
    TabArticleThumbsServiceImpl tabArticleThumbsService;

    // 进入博客首页
    @GetMapping("/article")
    public ModelAndView articleList(@RequestParam(required = true, defaultValue = "1") int current, @RequestParam(required = true, defaultValue = "5") int size, Model model) {
        //视图跳转
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("article");
        // 获取文章
        List<TabArticle> articleList = new ArrayList();
        articleList = tabArticleService.getArticles(current, size);
        model.addAttribute("articleList", articleList);
        return modelAndView;
    }

    // 文章详情
    @GetMapping("/article/{id}")
    public ModelAndView articleDetail(@PathVariable("id") Integer id, Model model, HttpServletRequest request) {
        String ip = GetIpAddr.getIpAddr(request);
        //增加阅读量
        historyService.increaseView(request, String.valueOf(id), ip);
        ModelAndView modelAndView = new ModelAndView();
        QueryWrapper<TabArticle> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", id);
        //获取文章内容
        TabArticle tabArticle = tabArticleService.getOne(wrapper);
        //获取tag列表
        QueryWrapper<Tag> tqw = new QueryWrapper<>();
        tqw.in("id", tabArticle.getArticleTags().split(","));
        List<Tag> tags = tagMapper.selectList(tqw);
        //获取作者信息
        TabUser author = tabUserService.getById(tabArticle.getAuthorId());
        //获取热门文章
        List<TabArticle> hotArticle = tabArticleService.getHotArticle(0, 10);
        //获取相关推荐
        String[] tt = tabArticle.getArticleTags().toString().split(",");
        List<TabArticle> relatedArticle = tabArticleService.getRelatedArticle(tt, tabArticle.getArticleId());
        //获取点赞状态
        boolean status = tabArticleThumbsService.getStatus(request,id);

        model.addAttribute("article", tabArticle);
        model.addAttribute("tags", tags);
        model.addAttribute("hot", hotArticle);
        model.addAttribute("related", relatedArticle);
        model.addAttribute("author", author);
        model.addAttribute("islike", status);
        modelAndView.setViewName("details");
        return modelAndView;
    }

    // 进入发布文章页
    @GetMapping("/publish")
    public ModelAndView getPublish(Model model) {
        List<TagDao> tagList = tagSortMapper.getTagList();
        model.addAttribute("tag", tagList);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("publish");
        return modelAndView;
    }

    // 发布文章
    @PostMapping("/publish")
    public Result publish_Article(@RequestBody(required = true) TabArticle tabArticle, Model model) {
        Result result = tabArticleService.ifNull(tabArticle);
        if (result.isSuccess() != true) {
            // 发布的内容有空值，返回错误信息
            return result;
        }
        //
        int postResutl = tabArticleService.postOption(tabArticle);
        // 判断是否插入成功
        if (postResutl < 1) {
            result.setSuccess(false);
            result.setMessage("文章更新失败");
        }
        //成功后返回成功的id
        result.setSuccess(true);
        result.setCode(200);
        result.setMessage("文章更新成功");
        Map map = new HashMap();
        map.put("id", postResutl);
        result.setData(map);
        return result;
    }

    // 点赞
    @GetMapping("/like/{id}")
    @ResponseBody
    public String like(@PathVariable("id") Integer articleId,
                       HttpServletRequest request) {
        JSONObject obj = new JSONObject();
        Result result = tabArticleThumbsService.giveTheThumbsUp(request, articleId);
        obj.put("result", result);
        return obj.toString();
    }

}

