package com.nice.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nice.demo.dao.TabCommentDao;
import com.nice.demo.dao.ReplyDao;
import com.nice.demo.mapper.*;
import com.nice.demo.pojo.*;
import com.nice.demo.service.ITabCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nice.demo.utils.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class TabCommentServiceImpl extends ServiceImpl<TabCommentMapper, TabComment> implements ITabCommentService {


    @Autowired
    TabCommentMapper commentMapper;
    @Autowired
    TabArticleMapper articleMapper;
    @Autowired
    TabUserMapper userMapper;
    @Autowired
    TabReplyMapper replyMapper;

    @Autowired
    TabCommentThumbsMapper thumbsMapper;

    @Autowired
    TabReplyThumbsMapper replyThumbsMapper;

    // 发布一级评论
    @Override
    public Result publishComment(TabComment comment, HttpServletRequest request) {
        Result result = new Result();
        Object userId = request.getSession().getAttribute("user");
        if (StringUtils.isEmpty(userId)) {
            result.setCode(403);
            result.setSuccess(false);
            result.setMessage("请登录后再进行操作");
            return result;
        }
        if (StringUtils.isEmpty(comment.getContent())) {
            result.setCode(405);
            result.setSuccess(false);
            result.setMessage("评论内容不能为空");
            return result;
        }
        comment.setUserId(userId.toString());
        // 插入数据库
        commentMapper.insert(comment);
        // 取出原来文章的信息
        TabArticle tabArticle = articleMapper.selectById(comment.getArticleId());
        //文章评论数+1
        tabArticle.setCommentNum(tabArticle.getCommentNum() + 1);
        // 更新文章信息
        QueryWrapper<TabArticle> articleQueryWrapper = new QueryWrapper<>();
        articleQueryWrapper.eq("article_id", comment.getArticleId());
        articleMapper.update(tabArticle, articleQueryWrapper);

        result.setCode(200);
        result.setSuccess(true);
        result.setMessage("评论成功");

        return result;
    }

    // 获取组装好的评论
    @Override
    public List<TabCommentDao> finComments(Integer id, HttpServletRequest request) {
        //获取一级评论
        QueryWrapper<TabComment> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", id);
        List<TabComment> commentList = commentMapper.selectList(wrapper);
        // 返回的结果集
        List<TabCommentDao> tabCommentDaos = new ArrayList<>();
        //遍历一级评论
        for (TabComment comment : commentList) {
            List<ReplyDao> replyDaos = new ArrayList<>();
            if (comment.getReplyNum() != 0) {
                //获取二级评论
                Map<String, String> map = new HashMap<>();
                map.put("article_id", id.toString());
                map.put("comment_id", comment.getId().toString());
                QueryWrapper<TabReply> replyQueryWrapper = new QueryWrapper<>();
                replyQueryWrapper.allEq(map);
                List<TabReply> replies = replyMapper.selectList(replyQueryWrapper);
                for (TabReply reply : replies) {
                    TabUser user1 = userMapper.selectById(reply.getUserId());
                    ReplyDao replyDao = new ReplyDao();
                    BeanUtils.copyProperties(reply, replyDao);
                    replyDao.setUser(user1);
                    //设置回复的点赞状态
                    replyDao.setStatus(getReplyLikeStatus(reply.getId(), request));
                    replyDaos.add(replyDao);
                }
            }
            TabCommentDao tabCommentDao = new TabCommentDao();
            //使用帮助类快速转换对象
            BeanUtils.copyProperties(comment, tabCommentDao);
            tabCommentDao.setReplies(replyDaos);
            tabCommentDao.setUser(userMapper.selectById(comment.getUserId()));
            //设置点赞状态
            tabCommentDao.setStatus(getCommentLikeStatus(comment.getId(), request));
            tabCommentDaos.add(tabCommentDao);
        }
        return tabCommentDaos;
    }

    //更新点赞数目
    @Override
    public void updateLikeNum(Integer id, String option) {
        QueryWrapper<TabComment> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        TabComment comment = commentMapper.selectById(id);
        if (option.equals("decrease")) {
            comment.setLikeNum(comment.getLikeNum() - 1);
        } else {
            comment.setLikeNum(comment.getLikeNum() + 1);
        }
        commentMapper.update(comment, wrapper);
    }


    //获取评论的点赞状态
    Boolean getCommentLikeStatus(Integer id, HttpServletRequest request) {
        Object user = request.getSession().getAttribute("user");
        if (StringUtils.isEmpty(user)) {
            return false;
        }
        QueryWrapper<TabCommentThumbs> wrapper = new QueryWrapper<>();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", user.toString());
        map.put("comment_id", id.toString());
        map.put("status", "0");
        wrapper.allEq(map);
        Integer count = thumbsMapper.selectCount(wrapper);
        if (count == 0) {
            return false;
        }
        return true;
    }
    //获取回复的点赞状态
    Boolean getReplyLikeStatus(Integer replyId, HttpServletRequest request) {
        Object user = request.getSession().getAttribute("user");
        if (StringUtils.isEmpty(user)) {
            return false;
        }
        QueryWrapper<TabReplyThumbs> wrapper = new QueryWrapper<>();
        Map<String, String> map = new HashMap<>();
        map.put("user_id", user.toString());
        map.put("reply_id", replyId.toString());
        map.put("status", "0");
        wrapper.allEq(map);
        Integer count = replyThumbsMapper.selectCount(wrapper);
        if (count == 0) {
            return false;
        }
        return true;
    }

}


















