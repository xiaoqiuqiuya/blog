package com.nice.demo.service.impl;

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
            result.setMessage("请选择文章来源");
            return result;
        } else {
            //判断文章是否授权了
            if (tabArticle.getArticleType() != "original") {
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

}
