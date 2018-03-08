package com.sybr.backend.utils;

/**
 * @author haibokong
 */
public enum  ResultCode {

    /**
     * 请求成功
     */
    SUCCESS(0, "请求成功"),
    /**
     * 请求失败
     */
    FAIL(20101, "名称已存在"),
    CodeFAIL(20102, "编码已存在"),
    DELETE_ERROR(20100,"删除失败"),
    WEAK_NET_WORK(-1, "网络异常，请稍后重试"),
    PASSWORD_ERROR(10001, "用户名或密码错误"),
    PARAMETER_ERROR(10101, "参数错误"),
    AUTHENTICATION_FAILED(9999, "认证未通过"),
    UNAUTHORIZED(10000, "未授权"),
    USERNAME_EXIST(10111,"用户名已存在"),
    USERCODE_EXIST(10112,"用户代码已存在"),
    IMAGECODE_ERROR(10113,"验证码错误"),
    IMAGECODE_NULL(10114,"验证码不存在"),
    IMAGECODE_TIMEOUT(10115,"验证码过期"),
    USERLOGIN(10116,"用户在线"),
    KAFKAFAILE(10117,"kafka发送信息失败"),
    READFILEFAIL(10118,"读取文件失败"),
    ;

    /**
     * 请求代码
     */
    private int code;
    private String msg;

    ResultCode(int code, String msg) {
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