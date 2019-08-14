package com.mex.pdd.modules.admin.sys.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mex.pdd.modules.admin.sys.dao.SysRoleDeptDao;
import com.mex.pdd.modules.admin.sys.service.SysRoleDeptService;
import com.mex.pdd.modules.admin.sys.entity.SysRoleDept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色与部门对应关系 服务实现类
 * </p>
 *
 * @author theodo
 * @since 2017-10-28
 */
@Service
public class SysRoleDeptServiceImpl extends ServiceImpl<SysRoleDeptDao, SysRoleDept> implements SysRoleDeptService {
    @Autowired
    private SysRoleDeptDao sysRoleDeptDao;

    @Override
    @Transactional
    public void saveOrUpdate(Long roleId, List<Long> deptIdList) {
        //先删除角色与菜单关系
        sysRoleDeptDao.deleteByRoleId(roleId);

        if (deptIdList.size() == 0) {
            return;
        }

        //保存角色与菜单关系
        Map<String, Object> map = new HashMap<>();
        map.put("roleId", roleId);
        map.put("deptIdList", deptIdList);
        sysRoleDeptDao.save(map);
    }

    @Override
    public List<Long> queryDeptIdList(Long roleId) {
        return sysRoleDeptDao.queryDeptIdList(roleId);
    }

    @Override
    public List<Long> queryDeptIdList(Long[] roleIds) {
        return sysRoleDeptDao.queryDeptIdListByRoleIds(roleIds);
    }
}
