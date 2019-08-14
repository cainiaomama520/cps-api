package com.mex.pdd.modules.admin.sys.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mex.pdd.base.common.controller.oauth2.TokenGenerator;
import com.mex.pdd.modules.admin.sys.dao.SysUserTokenDao;
import com.mex.pdd.modules.admin.sys.entity.SysUserToken;
import com.mex.pdd.modules.admin.sys.service.SysUserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 系统用户Token 服务实现类
 * </p>
 *
 * @author theodo
 * @since 2017-10-28
 */
@Service
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenDao, SysUserToken> implements SysUserTokenService {
    //8小时 后过期
    private final static int EXPIRE = 3600 * 8;
    @Autowired
    private SysUserTokenDao sysUserTokenDao;

    @Override
    public SysUserToken queryByToken(String token) {
        return sysUserTokenDao.queryByToken(token);
    }

    @Override
    public SysUserToken createToken(long userId) {
        //生成一个token
        String token = TokenGenerator.generateValue();

        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

        //判断是否生成过token
        SysUserToken tokenEntity = sysUserTokenDao.selectById(userId);
        if (tokenEntity == null) {
            tokenEntity = new SysUserToken();
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //保存token
            sysUserTokenDao.insert(tokenEntity);
        } else {
//            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //更新token
            sysUserTokenDao.updateById(tokenEntity);
        }
        return tokenEntity;
    }

    @Override
    public void logout(long userId) {
        Wrapper<SysUserToken> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        sysUserTokenDao.delete(wrapper);
    }

}
