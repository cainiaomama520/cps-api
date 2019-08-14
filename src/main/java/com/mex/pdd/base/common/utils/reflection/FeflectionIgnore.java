package com.mex.pdd.base.common.utils.reflection;

import java.lang.annotation.*;

/**
 * 反射忽略字段
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FeflectionIgnore {
}
