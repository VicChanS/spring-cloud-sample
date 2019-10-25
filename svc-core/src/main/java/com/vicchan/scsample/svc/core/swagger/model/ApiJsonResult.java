package com.vicchan.scsample.svc.core.swagger.model;

/**
 * Created by yueh on 2018/9/7.
 */


import com.vicchan.scsample.svc.core.common.GlobalString;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiJsonResult {

    String[] value();

    String name() default "";

    String type() default GlobalString.RESULT_TYPE_NORMAL;


}