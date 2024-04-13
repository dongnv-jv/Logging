package com.vn.demo.aop;

import com.vn.demo.constant.LogActionEnum;
import com.vn.demo.constant.LogFunctionEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    LogActionEnum action();
    LogFunctionEnum function() ;
}
