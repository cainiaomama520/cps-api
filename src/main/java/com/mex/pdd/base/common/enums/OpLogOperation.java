package com.mex.pdd.base.common.enums;

import com.baomidou.mybatisplus.enums.IEnum;

import java.io.Serializable;

public enum OpLogOperation implements IEnum {
    insert("创建"), update("更新")/*, status("修改状态")*/;
    private String name;

    OpLogOperation(String name) {
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
