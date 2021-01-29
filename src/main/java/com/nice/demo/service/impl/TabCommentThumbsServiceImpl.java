package com.nice.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nice.demo.pojo.TabCommentThumbs;
import com.nice.demo.mapper.TabCommentThumbsMapper;
import com.nice.demo.service.ITabCommentThumbsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nice.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sun.security.krb5.internal.tools.Ktab;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author nice
 * @since 2021-01-27
 */
@Service
public class TabCommentThumbsServiceImpl extends ServiceImpl<TabCommentThumbsMapper, TabCommentThumbs> implements ITabCommentThumbsService {

    @Autowired
    TabCommentThumbsMapper commentThumbsMapper;

    @Autowired
    TabCommentServiceImpl commentService;

    // 点赞评论
    @Override
    public Result likeComment(Integer id, HttpServletRequest request) {
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
        map.put("comment_id", id.toString());
        QueryWrapper<TabCommentThumbs> wrapper = new QueryWrapper<>();
        wrapper.allEq(map);

        List<TabCommentThumbs> tabCommentThumbs = commentThumbsMapper.selectList(wrapper);
        TabCommentThumbs thumbs = new TabCommentThumbs();
        thumbs.setCommentId(id);
        thumbs.setUserId(user.toString());

        if (tabCommentThumbs.size() == 0) {
            // 第一次点赞，直接添加
            commentThumbsMapper.insert(thumbs);
            commentService.updateLikeNum(id, "increase");
            //返回结果
            result.setMessage("点赞成功");
            result.setCode(200);
            result.setSuccess(true);
            return result;
        }
        //遍历结果集   status:0点赞  1取消
        for (TabCommentThumbs thumb : tabCommentThumbs) {
            if (!thumb.getStatus()) {
                //取消
                result.setMessage("取消点赞成功");
                // 修改点赞数目
                commentService.updateLikeNum(id, "decrease");
            } else {
                //点赞
                result.setMessage("再次点赞成功");
                // 修改点赞数目
                commentService.updateLikeNum(id, "increase");
            }
            result.setCode(200);
            result.setSuccess(true);
            thumb.setStatus(!thumb.getStatus());
            //更新数据库
            commentThumbsMapper.update(thumb, wrapper);
        }
        return result;
    }

}
