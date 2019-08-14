package com.mex.pdd.modules.admin.sys.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mex.pdd.modules.admin.sys.dao.SysUserRoleDao;
import com.mex.pdd.modules.admin.sys.entity.SysUserRole;
import com.mex.pdd.modules.admin.sys.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户与角色对应关系 服务实现类
 * </p>
 *
 * @author theodo
 * @since 2017-10-28
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleDao, SysUserRole> implements SysUserRoleService {
    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Override
    public void saveOrUpdate(Long userId, List<Long> roleIdList) {
        if (roleIdList.size() == 0) {
            return;
        }

        //先删除用户与角色关系
        sysUserRoleDao.deleteByUserId(userId);

        //保存用户与角色关系
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("roleIdList", roleIdList);
        sysUserRoleDao.save(map);
    }

    @Override
    public List<Long> queryRoleIdList(Long userId) {
        return sysUserRoleDao.queryRoleIdList(userId);
    }

    @Override
    public void deleteByUserId(Long userId) {
        sysUserRoleDao.deleteByUserId(userId);
    }
}
