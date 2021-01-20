package com.nice.demo.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;

import java.lang.reflect.Field;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author nice
 * @since 2021-01-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="UserLogin对象", description="")
public class UserLogin implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "表主键")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "userId")
    private String userId;

    @ApiModelProperty(value = "登录的IP地址")
    private String ipAddr;

    @ApiModelProperty(value = "登录时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtLogin;


}
