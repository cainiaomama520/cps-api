package com.mex.pdd.base.common.enums;

import com.baomidou.mybatisplus.enums.IEnum;

import java.io.Serializable;

/**
 * 订单上报类型
 */
public enum OfferSyncType implements IEnum {
    client, server;

    @Override
    public Serializable getValue() {
        return name();
    }
}
