package com.mex.pdd.base.common.enums;

import com.baomidou.mybatisplus.enums.IEnum;

import java.io.Serializable;

/**
 * 任务状态
 */
public enum TaskStatus implements IEnum {
    pending, success, failed;

    @Override
    public Serializable getValue() {
        return name();
    }
}
