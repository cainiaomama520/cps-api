package com.mex.pdd.base.common.utils;

/**
 * Redis所有Keys
 *
 * @author theodo
 * @email 36780272@qq.com
 * @date 2017-10-18 19:51
 */
public class RedisKeys {

    public static String getSysConfigKey(String key) {
        return "sys:config:" + key;
    }
}
