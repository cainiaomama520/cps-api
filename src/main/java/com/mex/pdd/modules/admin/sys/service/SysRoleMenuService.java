package com.mex.pdd.modules.admin.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.mex.pdd.modules.admin.sys.entity.SysRoleMenu;

import java.util.List;

/**
 * <p>
 * 角色与菜单对应关系 服务类
 * </p>
 *
 * @author theodo
 * @since 2017-10-28
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {
    void saveOrUpdate(Long roleId, List<Long> menuIdList);

    /**
     * 根据角色ID，获取菜单ID列表
     */
    List<Long> queryMenuIdList(Long roleId);
}
