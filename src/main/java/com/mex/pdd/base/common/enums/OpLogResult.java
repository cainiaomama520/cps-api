package com.mex.pdd.base.common.enums;

import com.baomidou.mybatisplus.enums.IEnum;

import java.io.Serializable;

public enum OpLogResult implements IEnum {
    success, error;

    @Override
    public Serializable getValue() {
        return name();
    }
}
