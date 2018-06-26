package com.healtt.commons.result;

/**
 * Created by wushenjun on 2018/5/31.
 */
public enum ReturnCode {
    SUCCESS("40001", "成功"),
    EXCEPTION("40002", "系统异常"),
    ERROR("40003", "一般错误"),
    PARAM_ERROR("40004", "参数错误"),
    NOT_LOGIN("40020", "该操作需要登录"),
    NO_PRIVILEGE("40030", "无权限"),
    NEED_ALERT("40040", "前端需弹窗的错误");

    private String code;
    private String msg;

    private ReturnCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}