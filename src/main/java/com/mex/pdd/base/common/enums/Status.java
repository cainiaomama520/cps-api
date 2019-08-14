package com.mex.pdd.base.common.enums;

import com.baomidou.mybatisplus.enums.IEnum;

import java.io.Serializable;

/**
 * 状态
 */
public enum Status implements IEnum {
    enable(1), disable(0);
    private Integer v;

    Status(Integer v) {
        this.v = v;
    }

    public Integer getV() {
        return v;
    }

    @Override
    public Serializable getValue() {
        return name();
    }
}
