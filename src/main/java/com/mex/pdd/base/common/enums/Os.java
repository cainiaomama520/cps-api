package com.mex.pdd.base.common.enums;

import com.baomidou.mybatisplus.enums.IEnum;

import java.io.Serializable;

/**
 * 设备os
 */
public enum Os implements IEnum {
    IOS {
        @Override
        public String map2String() {
            return name().toLowerCase();
        }
    }, ANDROID {
        @Override
        public String map2String() {
            return name().toLowerCase();
        }
    }, ALL {
        @Override
        public String map2String() {
            return "";
        }
    };

    abstract public String map2String();

    @Override
    public Serializable getValue() {
        return name();
    }
}
