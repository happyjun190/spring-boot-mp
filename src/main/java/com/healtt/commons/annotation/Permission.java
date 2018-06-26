package com.healtt.commons.annotation;

import com.healtt.commons.constant.Privilege;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义权限注解
 * Created by wushenjun on 2018/5/31.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Permission {
    boolean loginReqired() default true;

    Privilege[] privilege() default {Privilege.ANY};
}
