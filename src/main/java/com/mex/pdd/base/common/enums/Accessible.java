package com.mex.pdd.base.common.enums;

import com.baomidou.mybatisplus.enums.IEnum;

import java.io.Serializable;

/**
 * 是否能跳到应用商店
 */
public enum Accessible implements IEnum {
    unlimited, yes, no;

    @Override
    public Serializable getValue() {
        return name();
    }
}
