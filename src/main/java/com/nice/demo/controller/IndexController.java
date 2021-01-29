package com.nice.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

//    @GetMapping("/article")
//    public String getArticle(){
//        return "article";
//    }

    @GetMapping("/")
    public String getIndex(){
        return "index";
    }
}
