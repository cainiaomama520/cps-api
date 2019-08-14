package com.mex.pdd.modules.admin.sys.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mex.pdd.base.common.annotation.DataFilter;
import com.mex.pdd.base.common.utils.PageQuery;
import com.mex.pdd.modules.admin.sys.dao.SysRoleDao;
import com.mex.pdd.modules.admin.sys.entity.SysRole;
import com.mex.pdd.modules.admin.sys.service.SysRoleDeptService;
import com.mex.pdd.modules.admin.sys.service.SysRoleMenuService;
import com.mex.pdd.modules.admin.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author theodo
 * @since 2017-10-28
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRole> implements SysRoleService {
    @Autowired
    private SysRoleDao sysRoleDao;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysRoleDeptService sysRoleDeptService;

    @Override
    public Page<SysRole> selectPage(PageQuery<SysRole> pageQuery) {
        Page<SysRole> p = new Page<>(pageQuery.getPage(), pageQuery.getLimit());
        Wrapper<SysRole> wrapper = pageQuery.buildWrapper();
        return super.selectPage(p, wrapper);
    }

    @Override
    public Page<SysRole> queryPageList(Page<SysRole> page, Map<String, Object> map) {
        page.setRecords(sysRoleDao.queryPageList(page, map));
        return page;
    }

    @Override
    @DataFilter(tableAlias = "r", user = false)
    public List<SysRole> queryList(Map<String, Object> map) {
        return sysRoleDao.queryList(map);
    }

    @Override
    @Transactional
    public void save(SysRole role) {
        role.setCreateTime(new Date());
        sysRoleDao.insert(role);

        //保存角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());

        //保存角色与部门关系
        sysRoleDeptService.saveOrUpdate(role.getRoleId(), role.getDeptIdList());
    }

    @Override
    @Transactional
    public void update(SysRole role) {
        sysRoleDao.updateById(role);

        //更新角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());

        //保存角色与部门关系
        sysRoleDeptService.saveOrUpdate(role.getRoleId(), role.getDeptIdList());
    }

    @Override
    @Transactional
    public void deleteBatch(Long[] roleIds) {
        sysRoleDao.deleteBatch(roleIds);
    }


}
