package com.mex.pdd.modules.admin.sys.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.mex.pdd.modules.admin.sys.entity.SysLog;

import java.util.Map;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author theodo
 * @since 2017-10-28
 */
public interface SysLogService extends IService<SysLog> {
    Page<SysLog> selectPageList(Page<SysLog> page, Map<String, Object> map);
}
