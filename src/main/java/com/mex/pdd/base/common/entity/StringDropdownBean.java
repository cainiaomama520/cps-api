package com.mex.pdd.base.common.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 下拉菜单bean
 */
@Data
@Accessors(chain = true)
public class StringDropdownBean extends BaseBean {
    private String id;
    private String name;
    private List<StringDropdownBean> children;

    public StringDropdownBean() {
    }

    public StringDropdownBean(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public StringDropdownBean(String id, String name, List<StringDropdownBean> children) {
        this.id = id;
        this.name = name;
        this.children = children;
    }

    public static StringDropdownBean of(String id, String name) {
        return new StringDropdownBean(id, name);
    }

    public static StringDropdownBean of(String id, String name, List<StringDropdownBean> children) {
        return new StringDropdownBean(id, name, children);
    }

}
