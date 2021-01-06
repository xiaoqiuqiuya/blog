package com.nice.demo.utils;

/**
 * @author: nice
 * @date: 2021/1/1 17:05
 * @description:
 */
public enum ResultEnum {
    UNKNOW_ERROR(-1, "未知错误"),
    SUCCESS(200, "成功"),
    ERROR(0,"错误")
    ;


    private int code;
    private String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
