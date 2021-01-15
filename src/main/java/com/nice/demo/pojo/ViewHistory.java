package com.nice.demo.pojo;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.ibatis.annotations.Delete;

/**
 * <p>
 *
 * </p>
 *
 * @author nice
 * @since 2021-01-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "ViewHistory对象", description = "")
public class ViewHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "文章id")
    private String articleId;

    @ApiModelProperty(value = "浏览的时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtView;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Boolean deleted;
}
