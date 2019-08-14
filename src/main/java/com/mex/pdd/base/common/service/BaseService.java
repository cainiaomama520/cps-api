package com.mex.pdd.base.common.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.mex.pdd.base.common.utils.PageQuery;

import java.util.List;

/**
 * service父类
 */
public interface BaseService<T> extends IService<T> {
    Page<T> selectPage(PageQuery<T> pageQuery);

    List<T> selectList(PageQuery<T> pageQuery);
}
