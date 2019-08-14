package com.mex.pdd.modules.admin.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.mex.pdd.modules.admin.sys.entity.SysRoleDept;

import java.util.List;

/**
 * <p>
 * 角色与部门对应关系 服务类
 * </p>
 *
 * @author theodo
 * @since 2017-10-28
 */
public interface SysRoleDeptService extends IService<SysRoleDept> {

    void saveOrUpdate(Long roleId, List<Long> deptIdList);

    /**
     * 根据角色ID，获取部门ID列表
     */
    List<Long> queryDeptIdList(Long roleId);

    List<Long> queryDeptIdList(Long[] roleIds);

}
