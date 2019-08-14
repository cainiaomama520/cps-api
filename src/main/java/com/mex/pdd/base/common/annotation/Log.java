package com.mex.pdd.base.common.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解
 *
 * @author theodo
 * @email 36780272@qq.com
 * @date 2017年10月8日 上午10:19:56
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    String value() default "";
}
