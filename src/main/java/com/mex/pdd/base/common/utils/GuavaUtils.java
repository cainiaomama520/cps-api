package com.mex.pdd.base.common.utils;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.mapstruct.ap.internal.util.Strings;

import java.util.List;

/**
 * Created by David
 * on 2017/3/14
 */
public class GuavaUtils {

    private static final GuavaUtils INSTANCE;

    static {
        INSTANCE = new GuavaUtils();
    }

    private GuavaUtils() {
    }

    public static GuavaUtils getInstance() {
        return INSTANCE;
    }


    public static List<String> split(String target, String separator) {
        if (Strings.isEmpty(target)) return Lists.newArrayList();
        return Lists.newArrayList(Splitter.on(separator).trimResults().omitEmptyStrings().splitToList(target));
    }

    public static String join(Iterable<?> target, String separator) {
        return Joiner.on(separator).skipNulls().join(target);
    }

}
