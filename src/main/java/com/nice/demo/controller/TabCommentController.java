package com.nice.demo.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.nice.demo.pojo.TabComment;
import com.nice.demo.service.impl.TabCommentServiceImpl;
import com.nice.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author nice
 * @since 2021-01-21
 */
@Controller
@RequestMapping("/comment")
public class TabCommentController {

    @Autowired
    TabCommentServiceImpl commentService;

    //发布评论
    @PostMapping("publish")
    @ResponseBody
    public Result publishComment(@RequestBody(required = false) TabComment comment, HttpServletRequest request){
        Result result = new Result();
        result = commentService.publishComment(comment,request);
        return result;
    }

}

