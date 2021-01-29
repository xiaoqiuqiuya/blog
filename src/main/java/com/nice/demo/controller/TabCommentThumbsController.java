package com.nice.demo.controller;


import com.nice.demo.mapper.TabCommentMapper;
import com.nice.demo.pojo.TabCommentThumbs;
import com.nice.demo.service.impl.TabCommentServiceImpl;
import com.nice.demo.service.impl.TabCommentThumbsServiceImpl;
import com.nice.demo.utils.Result;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 前端控制器
 * 评论点赞
 * </p>
 *
 * @author nice
 * @since 2021-01-27
 */
@Controller
@RequestMapping("/tabCommentThumbs")
public class TabCommentThumbsController {

    @Autowired
    TabCommentThumbsServiceImpl commentThumbsService;


    //点赞评论
    @GetMapping("/likeComment/{id}")
    @ResponseBody
    public Result likeComment(@PathVariable("id") Integer id, HttpServletRequest request) {
        Result result = new Result();
        result = commentThumbsService.likeComment(id,request);
        return result;
    }


}

