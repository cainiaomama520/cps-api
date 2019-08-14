package com.mex.pdd.modules.admin.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mex.pdd.base.common.utils.Constant;
import com.mex.pdd.modules.admin.sys.dao.SysMenuDao;
import com.mex.pdd.modules.admin.sys.dao.SysRoleMenuDao;
import com.mex.pdd.modules.admin.sys.entity.SysRoleMenu;
import com.mex.pdd.modules.admin.sys.service.SysMenuService;
import com.mex.pdd.modules.admin.sys.service.SysUserService;
import com.mex.pdd.modules.admin.sys.entity.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单管理 服务实现类
 * </p>
 *
 * @author theodo
 * @since 2017-10-28
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenu> implements SysMenuService {
    //所有可用状态
    public static final Integer STATE_ON = 1;

    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;
    @Autowired
    private SysMenuDao sysMenuDao;
    @Autowired
    private SysUserService sysUserService;

    @Override
    public List<SysMenu> queryListParentId(Long parentId, List<Long> menuIdList) {
        List<SysMenu> menuList = queryListParentId(parentId);
        if (menuIdList == null) {
            return menuList;
        }

        List<SysMenu> userMenuList = new ArrayList<>();
        for (SysMenu menu : menuList) {
            if (menuIdList.contains(menu.getMenuId())) {
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }

    @Override
    public List<SysMenu> queryListParentId(Long parentId) {
        return sysMenuDao.queryListParentId(parentId);
    }

    @Override
    public List<SysMenu> queryNotButtonList() {
        return sysMenuDao.queryNotButtonList();
    }

    @Override
    public List<SysMenu> getUserMenuList(Long userId) {
        //系统管理员，拥有最高权限
        if (userId == Constant.SUPER_ADMIN) {
            return getAllMenuList(null);
        }

        //用户菜单列表
        List<Long> menuIdList = sysUserService.queryAllMenuId(userId);
        return getAllMenuList(menuIdList);
    }

    @Override
    public List<SysMenu> queryList(Map<String, Object> map) {
        return sysMenuDao.queryList(map);
    }

    @Override
    @Transactional
    public void deleteBatch(Long[] menuIds) {
        // sysMenuDao.deleteBatch(menuIds);
        Wrapper<SysRoleMenu> sysRoleMenuWrapper = new EntityWrapper<>();
        sysRoleMenuWrapper.in("menu_id", menuIds);
        sysRoleMenuDao.delete(sysRoleMenuWrapper);
        sysMenuDao.deleteById(menuIds[0]);
    }

    @Override
    public List<SysMenu> queryUserList(Long userId) {
        return sysMenuDao.queryUserList(userId);
    }

    /**
     * 获取所有菜单列表
     */
    private List<SysMenu> getAllMenuList(List<Long> menuIdList) {
        //查询根菜单列表
        List<SysMenu> menuList = queryListParentId(0L, menuIdList);
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList);

        return menuList;
    }

    /**
     * 递归
     */
    private List<SysMenu> getMenuTreeList(List<SysMenu> menuList, List<Long> menuIdList) {
        List<SysMenu> subMenuList = new ArrayList<SysMenu>();

        for (SysMenu entity : menuList) {
            if (entity.getType() == Constant.MenuType.CATALOG.getValue()) {//目录
                entity.setChildren(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
            }
            subMenuList.add(entity);
        }

        return subMenuList;
    }

    @Override
    public List<SysMenu> getAllMenuList(List<Long> menuIdList, boolean all, boolean onlyShow) {
        //查询根菜单列表
        List<SysMenu> menuList = queryListParentId(0L, menuIdList);
        if (onlyShow)
            menuList = menuList.stream().filter(entity -> Constant.STATE_ON.equals(entity.getIsShow())).collect(Collectors.toList());
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList, all, onlyShow);

        return menuList;
    }

    /**
     * 递归
     */
    private List<SysMenu> getMenuTreeList(List<SysMenu> menuList, List<Long> menuIdList, boolean all, boolean onlyShow) {
        List<SysMenu> subMenuList = new ArrayList<SysMenu>();

        for (SysMenu entity : menuList) {
            if (all || entity.getType() == Constant.MenuType.CATALOG.getValue()) {//目录
                List<SysMenu> subList = queryListParentId(entity.getMenuId(), menuIdList);
                if (onlyShow)
                    subList = subList.stream().filter(e -> Constant.STATE_ON.equals(e.getIsShow())).collect(Collectors.toList());
                entity.setChildren(getMenuTreeList(subList, menuIdList, all, onlyShow));
            }
            subMenuList.add(entity);
        }

        return subMenuList;
    }


}
