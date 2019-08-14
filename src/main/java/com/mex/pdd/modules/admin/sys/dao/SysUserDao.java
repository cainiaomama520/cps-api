package com.mex.pdd.modules.admin.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mex.pdd.modules.admin.sys.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统用户 Mapper 接口
 * </p>
 *
 * @author theodo
 * @since 2017-10-28
 */
public interface SysUserDao extends BaseMapper<SysUser> {

    List<SysUser> queryPageList(Page<SysUser> page, Map<String, Object> map);

    List<SysUser> queryPageByWrapper(RowBounds rowBounds, @Param("ew") Wrapper<SysUser> wrapper);

    List<SysUser> queryListByWrapper(@Param("ew") Wrapper<SysUser> wrapper);

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

    int deleteBatch(Object[] id);

}