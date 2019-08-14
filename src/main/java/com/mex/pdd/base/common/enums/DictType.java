package com.mex.pdd.base.common.enums;

import com.baomidou.mybatisplus.enums.IEnum;

import java.io.Serializable;

/**
 * 字典类型
 */
public enum DictType implements IEnum {
    sex,country;

    @Override
    public Serializable getValue() {
        return name();
    }
}
