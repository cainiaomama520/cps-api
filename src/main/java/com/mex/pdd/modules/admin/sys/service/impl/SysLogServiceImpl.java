package com.mex.pdd.modules.admin.sys.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mex.pdd.modules.admin.sys.dao.SysLogDao;
import com.mex.pdd.modules.admin.sys.entity.SysLog;
import com.mex.pdd.modules.admin.sys.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author theodo
 * @since 2017-10-28
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLog> implements SysLogService {

    @Autowired
    private SysLogDao sysLogDao;

    @Override
    public Page<SysLog> selectPageList(Page<SysLog> page, Map<String, Object> map) {
        page.setRecords(sysLogDao.selectPageList(page, map));
        return page;
    }

}
