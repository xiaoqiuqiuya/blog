package com.nice.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nice.demo.mapper.TabArticleMapper;
import com.nice.demo.mapper.TabCommentMapper;
import com.nice.demo.pojo.TabArticle;
import com.nice.demo.pojo.TabComment;
import com.nice.demo.pojo.TabReply;
import com.nice.demo.mapper.TabReplyMapper;
import com.nice.demo.service.ITabReplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nice.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author nice
 * @since 2021-01-21
 */
@Service
public class TabReplyServiceImpl extends ServiceImpl<TabReplyMapper, TabReply> implements ITabReplyService {

    @Autowired
    TabReplyMapper replyMapper;
    @Autowired
    TabCommentMapper commentMapper;

    // 发表回复
    @Override
    public Result postReply(TabReply reply, HttpServletRequest request) {
        Result result = new Result();
        Object user = request.getSession().getAttribute("user");
        // 判断是否登录
        if (StringUtils.isEmpty(user)) {
            result.setCode(403);
            result.setSuccess(false);
            result.setMessage("请登录后再进行操作");
            return result;
        } else {
            // 设置发表回复的用户
            reply.setUserId(user.toString());
        }
        // 非空判断
        if (StringUtils.isEmpty(reply.getContent())) {
            result.setMessage("不能发表空回复");
            result.setCode(405);
            result.setSuccess(false);
            return result;
        }
        // 去掉开头的空格
        reply.setContent(reply.getContent().trim());
        int insert = replyMapper.insert(reply);
        if (insert != 0) {
            Map<String, String> map = new HashMap<>();
            map.put("comment_id", reply.getCommentId());
            QueryWrapper<TabReply> replyQueryWrapper = new QueryWrapper<>();
            replyQueryWrapper.allEq(map);
            //获取当前回复的数量
            Integer integer = replyMapper.selectCount(replyQueryWrapper);
            // 更新回复数
            TabComment comment = commentMapper.selectById(reply.getArticleId());
            comment.setReplyNum(integer);
            QueryWrapper<TabComment> commentQueryWrapper = new QueryWrapper<>();
            commentQueryWrapper.eq("id",reply.getCommentId());
            commentMapper.update(comment,commentQueryWrapper);


            result.setMessage("已成功回复");
            result.setCode(200);
            result.setSuccess(true);
            return result;
        }
        result.setMessage("未知错误");
        result.setSuccess(false);
        result.setCode(-1);
        return result;
    }

    // 更新回复的点赞数
    @Override
    public void updateLikeNum(Integer id, String option) {
        QueryWrapper<TabReply> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        TabReply comment = replyMapper.selectById(id);
        if (option.equals("decrease")) {
            comment.setLikeNum(comment.getLikeNum() - 1);
        } else {
            comment.setLikeNum(comment.getLikeNum() + 1);
        }
        replyMapper.update(comment, wrapper);
    }
}
