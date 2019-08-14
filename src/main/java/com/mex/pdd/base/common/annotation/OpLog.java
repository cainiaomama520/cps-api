package com.mex.pdd.base.common.annotation;

import com.mex.pdd.base.common.enums.OpLogOperation;
import com.mex.pdd.base.common.enums.OpLogType;

import java.lang.annotation.*;

/**
 * 操作日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OpLog {

    OpLogOperation op();

    OpLogType type();
}
