package com.sybr.backend.utils;

/**
 * @author Mengjun Wen
 */
public class ResultUtils {

    public static Result success(int resultCode, String msg, Object data) {
        Result<Object> result = new Result<>();
        result.setCode(resultCode);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static Result success(Object data) {

        return new Result<>(ResultCode.SUCCESS, data);
    }

    public static Result warn(ResultCode resultCode, String msg) {
        Result<Object> result = new Result<>(resultCode);
        result.setMsg(msg);
        return result;
    }

    public static Result warn(ResultCode resultCode) {
        return new Result(resultCode);
    }

}
