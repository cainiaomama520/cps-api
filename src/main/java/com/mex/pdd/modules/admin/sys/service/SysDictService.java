package com.mex.pdd.modules.admin.sys.service;

import com.mex.pdd.base.common.entity.StringDropdownBean;
import com.mex.pdd.base.common.enums.DictType;
import com.mex.pdd.base.common.service.BaseService;
import com.mex.pdd.modules.admin.sys.entity.SysDict;

import java.util.List;

/**
 * <p>
 * 数据字典表 服务类
 * </p>
 *
 * @author david
 * @since 2019-01-14
 */
public interface SysDictService extends BaseService<SysDict> {

    List<StringDropdownBean> dropdown(DictType type);
}
