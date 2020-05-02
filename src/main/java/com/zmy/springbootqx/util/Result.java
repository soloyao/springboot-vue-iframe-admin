package com.zmy.springbootqx.util;

/**
 * 提示信息工具类
 */
public class Result {
    public static int SUCCESS_CODE = 0;
    public static int FAIL_CODE = 1;

    int code;
    String msg;
    Object data;

    private Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result success() {
        return new Result(SUCCESS_CODE, null, null);
    }

    public static Result success(Object data) {
        return new Result(SUCCESS_CODE, "", data);
    }

    public static Result fail(String message) {
        return new Result(FAIL_CODE, message, null);
    }

    public static int getSUCCESS_CODE() {
        return SUCCESS_CODE;
    }

    public static void setSUCCESS_CODE(int sUCCESS_CODE) {
        SUCCESS_CODE = sUCCESS_CODE;
    }

    public static int getFAIL_CODE() {
        return FAIL_CODE;
    }

    public static void setFAIL_CODE(int fAIL_CODE) {
        FAIL_CODE = fAIL_CODE;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
