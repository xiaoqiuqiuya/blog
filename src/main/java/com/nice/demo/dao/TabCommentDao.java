package com.nice.demo.dao;

import com.nice.demo.pojo.TabComment;
import com.nice.demo.pojo.TabReply;
import com.nice.demo.pojo.TabUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;


@Data
@ToString
public class TabCommentDao extends TabComment {
    @ApiModelProperty("点赞状态")
    private boolean status;

    @ApiModelProperty("评论人信息")
    private TabUser user;

    @ApiModelProperty(value = "关于评论的回复")
    private List<ReplyDao> replies;
}
