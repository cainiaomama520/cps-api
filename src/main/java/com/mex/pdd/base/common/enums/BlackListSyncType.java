package com.mex.pdd.base.common.enums;

import com.baomidou.mybatisplus.enums.IEnum;

import java.io.Serializable;

/**
 * 创建类型类型
 */
public enum BlackListSyncType implements IEnum {
    manual, auto_low_cvr, auto_high_cvr;

    @Override
    public Serializable getValue() {
        return name();
    }
}
