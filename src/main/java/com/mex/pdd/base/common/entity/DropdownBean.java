package com.mex.pdd.base.common.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 下拉菜单bean
 */
@Data
@Accessors(chain = true)
public class DropdownBean extends BaseBean {
    private Long id;
    private String name;
    private List<DropdownBean> children;

    public DropdownBean() {
    }

    public DropdownBean(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public DropdownBean(Long id, String name, List<DropdownBean> children) {
        this.id = id;
        this.name = name;
        this.children = children;
    }

    public static DropdownBean of(Long id, String name) {
        return new DropdownBean(id, name);
    }
}
