package com.nice.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nice.demo.mapper.TabArticleMapper;
import com.nice.demo.pojo.TabArticle;
import com.nice.demo.pojo.TabUser;
import com.nice.demo.pojo.ViewHistory;
import com.nice.demo.mapper.ViewHistoryMapper;
import com.nice.demo.service.IViewHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.View;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author nice
 * @since 2021-01-13
 */
@Service
public class ViewHistoryService extends ServiceImpl<ViewHistoryMapper, ViewHistory> implements IViewHistoryService {

    @Autowired
    ViewHistoryMapper historyMapper;
    @Autowired
    TabArticleMapper articleMapper;
    //浏览次数
    @Override
    public void increaseView(String articleId,String ip) {
        // 获取当前登录的用户
        // 主要是根据IP进行判断，如果登录了就记录用户的id，
        // 30分钟内只算一次
        TabUser tabUser = null;
        String userId = "";
        if (tabUser != null) {
            userId = String.valueOf(tabUser.getId());
        } else {
            userId = ip;
        }
        //1.查询数据库看用户是否点击过这个文章，并获取时间
        QueryWrapper<ViewHistory> wrapper = new QueryWrapper<>();
        Map<String,String> map = new HashMap();
        map.put("user_id",userId);
        map.put("article_id",articleId);
        wrapper.allEq(map);
        wrapper.orderByDesc("gmt_view");
        ViewHistory history = historyMapper.selectOne(wrapper);
        if (history == null) {
            // 第一次浏览、直接+1、添加历史记录
            inc(articleId);
            logHistory(userId,articleId,true);
        } else {
            logHistory(userId,articleId,false);
            //浏览的时间
            Date viewDate = history.getGmtView();
            //当前时间
            Date currentDate = new Date();
            // 时间间隔
            Long time = currentDate.getTime() - viewDate.getTime();
            if (time > 1800000) {
                // 更新浏览时间、+1
                inc(articleId);
            }
        }
    }
    // 更新浏览记录
    public void logHistory(String userId,String articleId,boolean isInsert){
        ViewHistory updateHistory = new ViewHistory();
        updateHistory.setUserId(userId);
        updateHistory.setArticleId(articleId);
        if (isInsert){
            historyMapper.insert(updateHistory);
        }else {
            QueryWrapper<ViewHistory> wrapper = new QueryWrapper<>();
            Map<String,String> map = new HashMap<>();
            map.put("user_id",userId);
            map.put("article_id",articleId);
            wrapper.allEq(map);
            historyMapper.update(updateHistory,wrapper);
        }
    }
    // 浏览次数+1
    public void inc(String articleId) {
        QueryWrapper<TabArticle> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("article_id", articleId);
        TabArticle tabArticle = articleMapper.selectById(articleId);
        Long viewNum = tabArticle.getViewNum();
        tabArticle.setViewNum(viewNum + 1);
        articleMapper.update(tabArticle, wrapper1);
    }
}
