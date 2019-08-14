package com.mex.pdd.base.common.enums;

import com.baomidou.mybatisplus.enums.IEnum;

import java.io.Serializable;

/**
 * 请求方式
 */
public enum RequestMethod implements IEnum {
    GET,POST;
    @Override
    public Serializable getValue() {
        return name();
    }
}
