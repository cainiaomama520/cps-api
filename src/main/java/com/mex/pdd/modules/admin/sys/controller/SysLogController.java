package com.mex.pdd.modules.admin.sys.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.mex.pdd.base.common.controller.base.BaseController;
import com.mex.pdd.base.common.utils.Query;
import com.mex.pdd.modules.admin.sys.entity.SysLog;
import com.mex.pdd.modules.admin.sys.service.SysLogService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 系统日志
 *
 * @author theodo
 * @email 36780272@qq.com
 * @date 2017-03-08 10:40:56
 */
@RestController
@RequestMapping("/sys/log")
@Api(description = "系统日志管理", tags = {"系统日志"})
public class SysLogController extends BaseController {
    @Autowired
    private SysLogService sysLogService;

    /**
     * 列表
     */
    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("sys:log:list")
    public ResponseEntity<Page<SysLog>> list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        Page<SysLog> pageUtil = new Page<SysLog>(query.getPage(), query.getLimit());
        Page<SysLog> page = sysLogService.selectPageList(pageUtil, query);
        return ResponseEntity.ok(page);
    }

}
