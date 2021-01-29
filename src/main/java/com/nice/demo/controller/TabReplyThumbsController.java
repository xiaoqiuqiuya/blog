package com.nice.demo.controller;


import com.nice.demo.service.impl.TabReplyThumbsServiceImpl;
import com.nice.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
 * @since 2021-01-27
 */
@Controller
@RequestMapping("/tabReplyThumbs")
public class TabReplyThumbsController {

    @Autowired
    TabReplyThumbsServiceImpl thumbsService;

    //点赞评论
    @GetMapping("/likeReply/{id}")
    @ResponseBody
    public Result likeComment(@PathVariable("id") Integer id, HttpServletRequest request) {
        Result result = new Result();
        result = thumbsService.likeReply(id,request);
        return result;
    }

}

