package com.mp.commons.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class JsonResult<T> implements JsonResultLogOps<T> {
    @ApiModelProperty(
            value = "状态码",
            example = "40001",
            required = true,
            position = -2
    )
    private String code;
    @ApiModelProperty(
            value = "返回消息",
            example = "恭喜，成功！",
            required = true,
            position = -1
    )
    private String message;
    @ApiModelProperty("具体的具体数据")
    private T data;

    public JsonResult() {
    }

    public static <D> JsonResult<D> build(ReturnCode code) {
        JsonResult<D> jr = new JsonResult();
        jr.setCode(code.getCode());
        jr.setMessage(code.getMsg());
        return jr;
    }

    public static <D> JsonResult<D> build(ReturnCode code, String msg) {
        JsonResult<D> jr = new JsonResult();
        jr.setCode(code.getCode());
        jr.setMessage(msg);
        return jr;
    }

    public static <D> JsonResult<D> build(ReturnCode code, String msg, D data) {
        JsonResult<D> jr = new JsonResult();
        jr.setCode(code.getCode());
        jr.setMessage(msg);
        jr.setData(data);
        return jr;
    }

    public static <D> JsonResult<D> success() {
        return build(ReturnCode.SUCCESS);
    }

    public static <D> JsonResult<D> success(String msg) {
        return build(ReturnCode.SUCCESS, msg);
    }

    public static <D> JsonResult<D> success(String msg, D data) {
        return build(ReturnCode.SUCCESS, msg, data);
    }

    public static <D> JsonResult<D> exception() {
        return build(ReturnCode.EXCEPTION);
    }

    public static <D> JsonResult<D> exception(String msg) {
        return build(ReturnCode.EXCEPTION, msg);
    }

    public static <D> JsonResult<D> error(String msg) {
        return build(ReturnCode.ERROR, msg);
    }

    public static <D> JsonResult<D> error(String msg, D data) {
        return build(ReturnCode.ERROR, msg, data);
    }

    public static <D> JsonResult<D> paramError(String msg) {
        return build(ReturnCode.PARAM_ERROR, msg);
    }

    public static <D> JsonResult paramError(String msg, D data) {
        return build(ReturnCode.PARAM_ERROR, msg, data);
    }

    public static <D> JsonResult<D> notLogin() {
        return build(ReturnCode.NOT_LOGIN);
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}