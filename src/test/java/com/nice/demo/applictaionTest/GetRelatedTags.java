package com.nice.demo.applictaionTest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nice.demo.mapper.TabArticleMapper;
import com.nice.demo.pojo.TabArticle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class GetRelatedTags {
    @Autowired
    TabArticleMapper tabArticleMapper;

    @Test
    public void test1() {
        String ss = ",2,3,";
        String[] tags = ss.split(",");
        Integer currentId = 12;
        // 去掉当前的文章
        List<TabArticle> list = new ArrayList<>();
        for (int i = 1; i < tags.length; i++) {
            QueryWrapper<TabArticle> wrapper = new QueryWrapper<>();
            wrapper.like("article_tags", "," + tags[i] + ",");
            wrapper.notIn("article_id", currentId);
            wrapper.orderByDesc("view_num");
            List<TabArticle> articleList = tabArticleMapper.selectList(wrapper);
            if (i != 1) {
                for (TabArticle article : articleList) {
                    if (!list.contains(article)) {
                        list.add(article);
                    }
                }
            } else {
                list.addAll(articleList);
            }
        }
        list.size();
    }

    @Test
    public void test2() {
        String ss = ",2,3,";
        String[] tags = ss.split(",");
        Integer currentId = 12;
        List<TabArticle> list = new ArrayList<>();
        List<TabArticle>[] array = new ArrayList[5];
        for (int i = 1; i < tags.length; i++) {
            QueryWrapper<TabArticle> wrapper = new QueryWrapper<>();
            wrapper.like("article_tags", "," + tags[i] + ",");
            wrapper.notIn("article_id", currentId);
            wrapper.orderByDesc("view_num");
            array[i - 1] = tabArticleMapper.selectList(wrapper);
        }

        for (int i = 0; i < tags.length-1; i++) {
            for (int j = 0; j < array[i].size(); j++) {
                if (list.size()<=10 && !list.contains(array[i].get(0))){
                    list.add(array[i].get(0));
                    array[i].remove(0);
                    continue;
                }
            }
        }

        System.out.println(list.size());
    }
}
