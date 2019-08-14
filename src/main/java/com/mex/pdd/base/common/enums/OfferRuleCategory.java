package com.mex.pdd.base.common.enums;

import com.baomidou.mybatisplus.enums.IEnum;

import java.io.Serializable;

/**
 * 规则类型
 */
public enum OfferRuleCategory implements IEnum {
    push, pull;

    @Override
    public Serializable getValue() {
        return name();
    }
}
