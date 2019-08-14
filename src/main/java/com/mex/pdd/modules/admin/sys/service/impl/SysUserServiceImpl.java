package com.mex.pdd.modules.admin.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mex.pdd.base.common.annotation.DataFilter;
import com.mex.pdd.base.common.entity.DropdownBean;
import com.mex.pdd.base.common.utils.PageQuery;
import com.mex.pdd.modules.admin.sys.dao.SysUserDao;
import com.mex.pdd.modules.admin.sys.entity.SysUser;
import com.mex.pdd.modules.admin.sys.service.SysUserRoleService;
import com.mex.pdd.modules.admin.sys.service.SysUserService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author theodo
 * @since 2017-10-28
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public Page<SysUser> selectPage(PageQuery<SysUser> pageQuery) {
        Page<SysUser> p = new Page<>(pageQuery.getPage(), pageQuery.getLimit());
        Wrapper<SysUser> wrapper = pageQuery.buildWrapper();
        p.setRecords(sysUserDao.queryPageByWrapper(p, wrapper));
        return p;
    }

    @Override
    public List<DropdownBean> dropdown(Long roleId) {
        List<SysUser> list;
        if (Objects.isNull(roleId)) {
            list = sysUserDao.queryList(Collections.emptyMap());
        } else {
            Wrapper<SysUser> wrapper = new EntityWrapper<SysUser>().eq("role.role_id", roleId).isWhere(true);
            list = sysUserDao.queryListByWrapper(wrapper);
        }

        return list.stream().map(sysUser -> DropdownBean.of(sysUser.getUserId(), sysUser.getUsername())).collect(Collectors.toList());
    }

    @Override
//    @DataFilter(tableAlias = "u", user = false)
    public Page<SysUser> queryPageList(Page<SysUser> page, Map<String, Object> map) {
        page.setRecords(sysUserDao.queryPageList(page, map));
        return page;
    }

    @Override
    @DataFilter(tableAlias = "u", user = false)
    public List<SysUser> queryList(Map<String, Object> map) {
        return sysUserDao.queryList(map);
    }

    @Override
    public List<String> queryAllPerms(Long userId) {
        return sysUserDao.queryAllPerms(userId);
    }

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return sysUserDao.queryAllMenuId(userId);
    }

    @Override
    public SysUser queryByUserName(String username) {
        return sysUserDao.queryByUserName(username);
    }

    @Override
    @Transactional
    public void save(SysUser user) {
        user.setCreateTime(new Date());
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
        user.setSalt(salt);
        sysUserDao.insert(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Override
    @Transactional
    public void update(SysUser user) {
        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(null);
        } else {
            user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
        }
        sysUserDao.updateById(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Override
    @Transactional
    public void deleteBatch(Long[] userId) {
        sysUserDao.deleteBatch(userId);
    }

}
