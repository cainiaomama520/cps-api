package com.mex.pdd.base.common.enums;

import com.baomidou.mybatisplus.enums.IEnum;

import java.io.Serializable;

/**
 * offer类型
 */
public enum OfferType implements IEnum {
    cpi, cpe, cps, cpa;

    @Override
    public Serializable getValue() {
        return name();
    }
}
