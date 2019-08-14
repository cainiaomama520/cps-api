package com.mex.pdd.base.common.utils;

import java.util.UUID;

/**
 * Created by David
 * on 2017/3/24
 */
public class RandomUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
