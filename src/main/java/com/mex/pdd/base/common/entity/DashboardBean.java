package com.mex.pdd.base.common.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * bean
 */
@Data
@Accessors(chain = true)
public class DashboardBean<T> extends BaseBean {
    private List<String> categories;
    private List<T> data;
}
