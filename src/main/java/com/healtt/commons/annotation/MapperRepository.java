package com.healtt.commons.annotation;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Created by wushenjun on 2018/6/1.
 */
@Mapper
@Repository
public @interface MapperRepository {
    String value() default "";
}
