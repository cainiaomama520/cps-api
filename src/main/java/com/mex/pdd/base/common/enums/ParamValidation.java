package com.mex.pdd.base.common.enums;

import com.baomidou.mybatisplus.enums.IEnum;

import java.io.Serializable;

/**
 *  参数验证类型os
 */
public enum ParamValidation implements IEnum {
    idfa, gaid, android_id;

    @Override
    public Serializable getValue() {
        return name();
    }
}
