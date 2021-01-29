package com.nice.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nice.demo.pojo.TabCommentThumbs;
import com.nice.demo.pojo.TabReplyThumbs;
import com.nice.demo.mapper.TabReplyThumbsMapper;
import com.nice.demo.service.ITabReplyThumbsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nice.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author nice
 * @since 2021-01-27
 */
@Service
public class TabReplyThumbsServiceImpl extends ServiceImpl<TabReplyThumbsMapper, TabReplyThumbs> implements ITabReplyThumbsService {


    @Autowired
    TabReplyThumbsMapper thumbsMapper;

    @Autowired
    TabReplyServiceImpl replyService;

    // 点赞评论
    @Override
    public Result likeReply(Integer id, HttpServletRequest request) {
        Result result = new Result();
        Object user = request.getSession().getAttribute("user");
        if (StringUtils.isEmpty(user)) {
            result.setCode(403);
            result.setSuccess(false);
            result.setMessage("请登录后操作");
            return result;
        }
        //1.查询是否有点赞记录
        //有就取消点赞，否则执行点赞
        Map<String, String> map = new HashMap();
        map.put("user_id", user.toString());
        map.put("reply_id", id.toString());
        QueryWrapper<TabReplyThumbs> wrapper = new QueryWrapper<>();
        wrapper.allEq(map);

        List<TabReplyThumbs> replyThumbs = thumbsMapper.selectList(wrapper);
        TabReplyThumbs thumbs = new TabReplyThumbs();
        thumbs.setReplyId(id);
        thumbs.setUserId(user.toString());

        if (replyThumbs.size() == 0) {
            // 第一次点赞，直接添加
            thumbsMapper.insert(thumbs);
            replyService.updateLikeNum(id, "increase");
            //返回结果
            result.setMessage("点赞成功");
            result.setCode(200);
            result.setSuccess(true);
            return result;
        }
        //遍历结果集   status:0点赞  1取消
        for (TabReplyThumbs thumb : replyThumbs) {
            if (!thumb.getStatus()) {
                //取消
                result.setMessage("取消点赞成功");
                // 修改点赞数目
                replyService.updateLikeNum(id, "decrease");
            } else {
                //点赞
                result.setMessage("再次点赞成功");
                // 修改点赞数目
                replyService.updateLikeNum(id, "increase");
            }
            result.setCode(200);
            result.setSuccess(true);
            thumb.setStatus(!thumb.getStatus());
            //更新数据库
            thumbsMapper.update(thumb, wrapper);
        }
        return result;
    }
}
