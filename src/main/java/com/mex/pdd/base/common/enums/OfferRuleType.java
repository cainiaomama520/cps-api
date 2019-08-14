package com.mex.pdd.base.common.enums;

import com.baomidou.mybatisplus.enums.IEnum;

import java.io.Serializable;

/**
 * 规则类型
 */
public enum OfferRuleType implements IEnum {
    system, custom, packet;

    @Override
    public Serializable getValue() {
        return name();
    }
}
