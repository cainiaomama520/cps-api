package com.mex.pdd.base.common.utils;

import org.apache.commons.lang3.Validate;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * Spring Context 工具类
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月29日 下午11:45:51
 */
@Service
@Lazy(false)
public class SpringContextUtils implements ApplicationContextAware, DisposableBean {
    private static ApplicationContext context = null;
    private static final Logger logger = LogManager.getLogger(SpringContextUtils.class);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {

        logger.info("intApplicationContext  --->  " + applicationContext);
        SpringContextUtils.context = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return context;
    }

    public static Object getBean(String name) {
        return context.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        assertContextInjected();
        return context.getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return context.getBean(name, requiredType);
    }

    public static boolean containsBean(String name) {
        return context.containsBean(name);
    }

    public static boolean isSingleton(String name) {
        return context.isSingleton(name);
    }

    public static Class<?> getType(String name) {
        return context.getType(name);
    }

    /**
     * 检查ApplicationContext不为空.
     */
    private static void assertContextInjected() {
        Validate.validState(context != null, "applicaitonContext属性未注入");
    }

    /**
     * 实现DisposableBean接口, 在Context关闭时清理静态变量.
     */
    @Override
    public void destroy() throws Exception {
        SpringContextUtils.clearHolder();
    }

    /**
     * 清除SpringContextHolder中的ApplicationContext为Null.
     */
    public static void clearHolder() {
        if (logger.isDebugEnabled()) {
            logger.debug("清除ApplicationContext:" + context);
        }
        context = null;
    }
}
