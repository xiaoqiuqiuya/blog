package com.nice.demo.controller;

import com.nice.demo.dao.Article;
import com.nice.demo.dao.TagDao;
import com.nice.demo.mapper.TagSortMapper;
import com.nice.demo.pojo.TagSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ArticleController {

    @Autowired
    TagSortMapper tagSortMapper;


    @PostMapping("/article")
    public String publishArticle(Article article){
        System.out.println(article);
        return null;
    }

//    进入发布文章
    @GetMapping("/publish")
    public String getPublish(Model model){

        List<TagDao> tagList = tagSortMapper.getTagList();
        model.addAttribute("tag",tagList);

        return "publish";
    }
}
