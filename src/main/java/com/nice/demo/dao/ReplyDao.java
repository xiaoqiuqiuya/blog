package com.nice.demo.dao;

import com.nice.demo.pojo.TabReply;
import com.nice.demo.pojo.TabUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ReplyDao extends TabReply {
    @ApiModelProperty("点赞状态")
    private boolean status;

    @ApiModelProperty(value = "用户信息")
    private TabUser user;
}
