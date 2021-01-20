package com.nice.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nice.demo.pojo.TabArticle;
import com.nice.demo.mapper.TabArticleMapper;
import com.nice.demo.service.ITabArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nice.demo.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author nice
 * @since 2020-12-30
 */
@Service
public class TabArticleService extends ServiceImpl<TabArticleMapper, TabArticle> implements ITabArticleService {

    @Autowired
    TabArticleMapper tabArticleMapper;

    /**
     * 判断内容是否为空
     * author_id -- 未登录
     * articleTitle
     * articleContent
     * articleTags
     * articleType
     * originalLink
     * permission
     * exposeFor
     *
     * @param tabArticle
     * @return
     */
    @Override
    public Result ifNull(TabArticle tabArticle) {
        Result result = new Result();
        // 标题不能为空
        if (tabArticle.getArticleTitle() == null || "".equals(tabArticle.getArticleTitle())) {
            result.setSuccess(false);
            result.setCode(301);
            result.setMessage("文章标题不能为空");
            return result;
        }
        // 内容不能为空
        if (tabArticle.getArticleContent() == null || "".equals(tabArticle.getArticleContent())) {
            result.setSuccess(false);
            result.setCode(302);
            result.setMessage("文章内容不能为空");
            return result;
        }

        // 没有添加标签，则文章归自动类为--其他
        if (tabArticle.getArticleTags() == null || "".equals(tabArticle.getArticleTags())) {
            tabArticle.setArticleTags("157");
        }
        // 没有选择文章来源
        if (tabArticle.getArticleType() == null || "".equals(tabArticle.getArticleType())) {
            result.setSuccess(false);
            result.setCode(303);
            result.setMessage("请选择文章类型");
            return result;
        } else {
            //判断文章是否授权了
            if (!tabArticle.getArticleType().equals("original")) {
                if (tabArticle.getOriginalLink() == null || "".equals(tabArticle.getOriginalLink())) {
                    result.setSuccess(false);
                    result.setCode(304);
                    result.setMessage("请告知文章来源");
                    return result;
                }
                if (!tabArticle.getPermission().equals("on")) {
                    result.setSuccess(false);
                    result.setCode(305);
                    result.setMessage("请确认该文章已获得原创作者授权");
                    return result;
                }
            }
        }
        result.setSuccess(true);
        return result;
    }


    // 保存或者发布文章
    @Override
    public int postOption(TabArticle tabArticle) {
        // 处理tags,
        String tags = tabArticle.getArticleTags();
        tags = "," + tags + ",";
        tabArticle.setArticleTags(tags);
        int result = 0;
        if (tabArticle.getArticleStatus().equals("save")) {
            // 保存文章
//            result = tabArticleMapper.insert(tabArticle);
            System.out.println("保存");
        }
        if (tabArticle.getArticleStatus().equals("post")) {
            // 发布文章
            int insert = tabArticleMapper.insert(tabArticle);
            if (insert != 1) {
                // 插入失败。
                return 0;
            }
            // 插入成功，返回插入的id
            result = tabArticle.getArticleId();
        }
        return result;
    }

    // 获取文章
    @Override
    public List<TabArticle> getArticles(int current, int size) {
        List<TabArticle> tabArticleList = new ArrayList<>();
        IPage<TabArticle> page = new Page<>(current, size);
        page = tabArticleMapper.selectPage(page, null);
        tabArticleList = page.getRecords();
        return tabArticleList;
    }

    //获取热门文章
    @Override
    public List<TabArticle> getHotArticle(int current, int size) {
        QueryWrapper<TabArticle> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("view_num");
        IPage<TabArticle> page = new Page<>(current, size);
        page = tabArticleMapper.selectPage(page, wrapper);
        List<TabArticle> tabArticleList = page.getRecords();
        return tabArticleList;
    }

    // 根据标签获取相关推荐
    @Override
    public List<TabArticle> getRelatedArticle(String[] tags, Integer currentId) {
        List<TabArticle> list = new ArrayList<>();
        for (int i = 1; i < tags.length; i++) {
            QueryWrapper<TabArticle> wrapper = new QueryWrapper<>();
            wrapper.like("article_tags", "," + tags[i] + ",");
            //去除当前的文章
            wrapper.notIn("article_id", currentId);
            //根据浏览次数排序
            wrapper.orderByDesc("view_num");
            //只获取10条
            IPage<TabArticle> page = new Page<>(0, 10);
            page = tabArticleMapper.selectPage(page, wrapper);
            List<TabArticle> articleList = page.getRecords();
            if (i != 1) {
                // 去除重复的文章
                for (TabArticle article : articleList) {
                    if (!list.contains(article)) {
                        list.add(article);
                    }
                }
            } else {
                list.addAll(articleList);
            }
        }
        // 如果标签太少，就获取热门的文章
        if (list.size() < 10) {
            List<TabArticle> hotArticle = getHotArticle(10, 10 - list.size());
            list.addAll(hotArticle);
        }
        list.size();
        return list;
    }

    // 修改文章点赞数量
    @Override
    public void updateLikeNum(Integer articleId, String option) {
        // 获取文章对象
        TabArticle tabArticle = tabArticleMapper.selectById(articleId);
        // 获取当前点赞数
        int like = tabArticle.getLikeNum();
        if (option.equals("increase")) {
            // 增加点赞数
            like += 1;
        } else if (option.equals("decrease")) {
            // 减少点赞数
            like -= 1;
        }
        // 重新构建文章对象
        tabArticle.setLikeNum(like);
        // 更新数据库
        QueryWrapper<TabArticle> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id",tabArticle.getArticleId());
        tabArticleMapper.update(tabArticle,wrapper);
    }
}
