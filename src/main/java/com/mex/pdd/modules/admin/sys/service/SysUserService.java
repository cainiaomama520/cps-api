package com.mex.pdd.modules.admin.sys.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.mex.pdd.base.common.entity.DropdownBean;
import com.mex.pdd.base.common.utils.PageQuery;
import com.mex.pdd.modules.admin.sys.entity.SysUser;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author theodo
 * @since 2017-10-28
 */
public interface SysUserService extends IService<SysUser> {

    Page<SysUser> selectPage(PageQuery<SysUser> pageQuery);

    List<DropdownBean> dropdown(Long roleId);

    Page<SysUser> queryPageList(Page<SysUser> pageUtil, Map<String, Object> map);

    /**
     * 查询用户列表
     */
    List<SysUser> queryList(Map<String, Object> map);

    /**
     * 查询用户的所有权限
     *
     * @param userId 用户ID
     */
    List<String> queryAllPerms(Long userId);

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> queryAllMenuId(Long userId);

    /**
     * 根据用户名，查询系统用户
     */
    SysUser queryByUserName(String username);

    /**
     * 删除用户
     */
    void deleteBatch(Long[] userIds);

    void save(SysUser user);

    void update(SysUser user);

}
