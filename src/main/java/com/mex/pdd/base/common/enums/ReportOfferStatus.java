package com.mex.pdd.base.common.enums;

import com.baomidou.mybatisplus.enums.IEnum;

import java.io.Serializable;

/**
 * offer状态
 */
public enum ReportOfferStatus implements IEnum {
    global, active, part_blacklist, blacklist;

    @Override
    public Serializable getValue() {
        return name();
    }
}
