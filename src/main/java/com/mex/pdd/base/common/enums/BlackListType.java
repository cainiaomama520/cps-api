package com.mex.pdd.base.common.enums;

import com.baomidou.mybatisplus.enums.IEnum;

import java.io.Serializable;

/**
 * 黑名单类型
 */
public enum BlackListType implements IEnum {
    offer_global, offer_channel, offer_subchannel;

    @Override
    public Serializable getValue() {
        return name();
    }
}
