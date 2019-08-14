package com.mex.pdd.base.common.enums;

import com.baomidou.mybatisplus.enums.IEnum;

import java.io.Serializable;

/**
 * 字典类型
 */
public enum OpLogType implements IEnum {
    cpa_offer("直客订单"), cpa_campaign("直客活动");

    private String name;

    OpLogType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public Serializable getValue() {
        return name();
    }
}
