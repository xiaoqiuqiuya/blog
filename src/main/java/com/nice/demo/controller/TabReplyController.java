package com.nice.demo.controller;


import com.nice.demo.pojo.TabReply;
import com.nice.demo.service.impl.TabReplyServiceImpl;
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
 * 前端控制器
 * </p>
 *
 * @author nice
 * @since 2021-01-21
 */
@Controller
@RequestMapping("/tabReply")
public class TabReplyController {

    @Autowired
    TabReplyServiceImpl replyService;

    //发表回复
    @PostMapping("reply")
    @ResponseBody
    public Result postReply(@RequestBody TabReply reply, HttpServletRequest request) {
        Result result = new Result();
        result = replyService.postReply(reply,request);
        return result;
    }


}

