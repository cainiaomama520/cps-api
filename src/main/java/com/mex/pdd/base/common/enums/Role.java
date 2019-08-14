package com.mex.pdd.base.common.enums;

import com.baomidou.mybatisplus.enums.IEnum;

import java.io.Serializable;

/**
 * 角色
 */
public enum Role implements IEnum {
    admin, operator, sales, medium;

    @Override
    public Serializable getValue() {
        return name();
    }
}
