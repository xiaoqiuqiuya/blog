package com.nice.demo.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * code:
 *  成功：200
 *  错误：
 *      401：账号密码为空
 *      402：账号密码错误
 *      403: 用户未登录
 *      404：已经点赞过该文章
 *      405: 发表空回复
 *
 *
 *
 * @author: nice
 * @date: 2021/1/1 16:40
 * @description:返回类型的封装
 */
@Data
public class Result {

    @ApiModelProperty(value = "是否成功")
    private boolean success;

    @ApiModelProperty(value = "状态码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    public Result() {
    }

    //请求成功
    public static Result OK() {
        Result result = new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMessage("请求成功");
        return result;
    }

    public static Result error() {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(ResultEnum.ERROR.getCode());
        result.setMessage("失败");
        return result;
    }



    public Result message(String message) {
        this.setMessage(message);
        return this;
    }

    public Result code(Integer code) {
        this.setCode(code);
        return this;
    }

    public Result data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public Result data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }

}
