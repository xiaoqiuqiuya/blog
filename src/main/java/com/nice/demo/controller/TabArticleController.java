package com.nice.demo.controller;


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


    @PostMapping("/article")
    public String publishArticle() {
        System.out.println();
        return null;
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

