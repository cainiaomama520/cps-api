package com.mex.pdd.base.common.enums;

import com.baomidou.mybatisplus.enums.IEnum;

import java.io.Serializable;

/**
 * 订单价格历史类型
 */
public enum OfferPriceHistoryType implements IEnum {
    cpa_offer, cpa_campaign, api;

    @Override
    public Serializable getValue() {
        return name();
    }
}
