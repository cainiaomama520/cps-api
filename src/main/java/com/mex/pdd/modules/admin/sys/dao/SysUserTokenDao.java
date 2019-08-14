package com.mex.pdd.modules.admin.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mex.pdd.modules.admin.sys.entity.SysUserToken;

/**
 * <p>
 * 系统用户Token Mapper 接口
 * </p>
 *
 * @author theodo
 * @since 2017-10-28
 */
public interface SysUserTokenDao extends BaseMapper<SysUserToken> {

    SysUserToken queryByToken(String token);

}