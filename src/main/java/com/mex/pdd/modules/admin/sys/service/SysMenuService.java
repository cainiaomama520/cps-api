package com.mex.pdd.modules.admin.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.mex.pdd.modules.admin.sys.entity.SysMenu;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单管理 服务类
 * </p>
 *
 * @author theodo
 * @since 2017-10-28
 */
public interface SysMenuService extends IService<SysMenu> {
    /**
     * 根据父菜单，查询子菜单
     *
     * @param parentId   父菜单ID
     * @param menuIdList 用户菜单ID
     */
    List<SysMenu> queryListParentId(Long parentId, List<Long> menuIdList);

    /**
     * 根据父菜单，查询子菜单
     *
     * @param parentId 父菜单ID
     */
    List<SysMenu> queryListParentId(Long parentId);

    /**
     * 获取不包含按钮的菜单列表
     */
    List<SysMenu> queryNotButtonList();

    /**
     * 获取用户菜单列表
     */
    List<SysMenu> getUserMenuList(Long userId);

    /**
     * 查询菜单列表
     */
    List<SysMenu> queryList(Map<String, Object> map);

    /**
     * 删除
     */
    void deleteBatch(Long[] menuIds);

    /**
     * 查询用户的权限列表
     */
    List<SysMenu> queryUserList(Long userId);

    List<SysMenu> getAllMenuList(List<Long> ids, boolean b, boolean b1);
}
