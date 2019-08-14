package com.mex.pdd.modules.admin.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mex.pdd.base.common.entity.StringDropdownBean;
import com.mex.pdd.base.common.enums.DictType;
import com.mex.pdd.base.common.utils.PageQuery;
import com.mex.pdd.modules.admin.sys.dao.SysDictDao;
import com.mex.pdd.modules.admin.sys.entity.SysDict;
import com.mex.pdd.modules.admin.sys.service.SysDictService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 数据字典表 服务实现类
 * </p>
 *
 * @author david
 * @since 2019-01-14
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictDao, SysDict> implements SysDictService {

    public Page<SysDict> selectPage(PageQuery<SysDict> pageQuery) {
        Page<SysDict> p = new Page<>(pageQuery.getPage(), pageQuery.getLimit());
        Wrapper<SysDict> wrapper = pageQuery.buildWrapper();
        return super.selectPage(p, wrapper);
    }

    @Override
    public List<SysDict> selectList(PageQuery<SysDict> pageQuery) {
        Wrapper<SysDict> wrapper = pageQuery.buildWrapper();
        return super.selectList(wrapper);
    }

    @Override
    public List<StringDropdownBean> dropdown(DictType type) {
        Wrapper<SysDict> wrapper = new EntityWrapper<SysDict>().eq("type", type).orderBy("orderNum", true);
        List<SysDict> list = selectList(wrapper);
        return list.stream().map(dict -> StringDropdownBean.of(dict.getCode(), dict.getValue())).collect(Collectors.toList());
    }
}
