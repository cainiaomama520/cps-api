package com.mex.pdd.base.common.aspect;

public class DataFilterThreadLocalContext {
    public static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    public static String get() {
        return CONTEXT.get();
    }

    public static void set(String value) {
        CONTEXT.set(value);
    }

    public static void remove() {
        CONTEXT.remove();
    }
}
