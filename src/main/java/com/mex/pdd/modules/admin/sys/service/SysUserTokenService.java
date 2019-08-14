package com.mex.pdd.modules.admin.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.mex.pdd.modules.admin.sys.entity.SysUserToken;

/**
 * <p>
 * 系统用户Token 服务类
 * </p>
 *
 * @author theodo
 * @since 2017-10-28
 */
public interface SysUserTokenService extends IService<SysUserToken> {
    SysUserToken queryByToken(String token);

    /**
     * 生成token
     *
     * @param userId 用户ID
     */
    SysUserToken createToken(long userId);

    /**
     * 退出，修改token值
     *
     * @param userId 用户ID
     */
    void logout(long userId);
}
