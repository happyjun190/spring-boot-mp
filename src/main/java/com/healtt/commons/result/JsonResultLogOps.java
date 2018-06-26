package com.healtt.commons.result;

/**
 * Created by wushenjun on 2018/5/31.
 */
public interface JsonResultLogOps<T> {
    String getMessage();

    String getCode();

    T getData();

    void setData(T var1);
}
