package com.mex.pdd.base.common.enums;

import com.baomidou.mybatisplus.enums.IEnum;

import java.io.Serializable;

/**
 * offer状态
 */
public enum OfferStatus implements IEnum {
    active, pause;

    @Override
    public Serializable getValue() {
        return name();
    }
}
