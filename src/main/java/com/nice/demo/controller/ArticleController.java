package com.nice.demo.controller;

import com.nice.demo.dao.Article;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    @PostMapping("/article")
    public String publishArticle(Article article){
        System.out.println(article);
        return null;
    }

}
