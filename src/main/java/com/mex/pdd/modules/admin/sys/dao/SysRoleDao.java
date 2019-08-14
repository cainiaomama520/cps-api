package com.mex.pdd.modules.admin.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mex.pdd.modules.admin.sys.entity.SysRole;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author theodo
 * @since 2017-10-28
 */
public interface SysRoleDao extends BaseMapper<SysRole> {

    List<SysRole> queryPageList(Page<SysRole> page, Map<String, Object> map);

    List<SysRole> queryList(Map<String, Object> map);

    int deleteBatch(Object[] id);

}