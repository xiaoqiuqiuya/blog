package com.nice.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nice.demo.dao.TagDao;
import com.nice.demo.mapper.TabArticleMapper;
import com.nice.demo.mapper.TagSortMapper;
import com.nice.demo.pojo.TabArticle;
import com.nice.demo.service.impl.TabArticleService;
import com.nice.demo.utils.Result;
import com.nice.demo.utils.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
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
    TabArticleMapper tabArticleMapper;
    @Autowired
    TabArticleService tabArticleService;

    // 进入博客首页
    @GetMapping("/article")
    public ModelAndView articleList(
            @RequestParam(required = true, defaultValue = "1")
                    int current,
            @RequestParam(required = true, defaultValue = "5")
                    int size,
            Model model) {
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
    public ModelAndView articleDetail(@PathVariable("id") Integer id, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        QueryWrapper<TabArticle> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", id);
        TabArticle tabArticle = tabArticleService.getOne(wrapper);
        model.addAttribute("article", tabArticle);
        modelAndView.setViewName("details");
        return modelAndView;
    }

    //    进入发布文章
    @GetMapping("/publish")
    public ModelAndView getPublish(Model model) {
        List<TagDao> tagList = tagSortMapper.getTagList();
        model.addAttribute("tag", tagList);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("publish");
        return modelAndView;
    }

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
}

