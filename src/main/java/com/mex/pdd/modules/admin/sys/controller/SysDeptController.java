package com.mex.pdd.modules.admin.sys.controller;


import com.mex.pdd.base.common.controller.base.BaseController;
import com.mex.pdd.base.common.utils.Constant;
import com.mex.pdd.modules.admin.sys.entity.SysDept;
import com.mex.pdd.modules.admin.sys.service.SysDeptService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


/**
 * 部门管理
 *
 * @author theodo
 * @email 36780272@qq.com
 * @date 2017-06-20 15:23:47
 */
@RestController
@RequestMapping("/sys/dept")
@Api(description = "部门表管理", tags = {"部门"})
public class SysDeptController extends BaseController {
    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:dept:list")
    public ResponseEntity<List<SysDept>> list() {
        List<SysDept> deptList = sysDeptService.queryList(new HashMap<>());
        return ResponseEntity.ok(deptList);
    }

    /**
     * 选择部门(添加、修改菜单)
     */
    @GetMapping("/select")
    @RequiresPermissions("sys:dept:select")
    public ResponseEntity<List<SysDept>> select() {
        List<SysDept> deptList = sysDeptService.queryList(new HashMap<>());
        //添加一级部门
        if (getUserId() == Constant.SUPER_ADMIN) {
            SysDept root = new SysDept();
            root.setDeptId(0L);
            root.setName("一级部门");
            root.setParentId(-1L);
            root.setOpen(true);
            deptList.add(root);
        }
        return ResponseEntity.ok(deptList);
    }

    /**
     * 上级部门Id(管理员则为0)
     */
    @GetMapping("/info")
    @RequiresPermissions("sys:dept:list")
    public ResponseEntity<Long> info() {
        long deptId = 0;
        if (getUserId() != Constant.SUPER_ADMIN) {
            SysDept dept = sysDeptService.selectById(getDeptId());
            deptId = dept.getParentId();
        }
        return ResponseEntity.ok(deptId);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{deptId}")
    @RequiresPermissions("sys:dept:info")
    public ResponseEntity<SysDept> info(@PathVariable("deptId") Long deptId) {
        SysDept dept = sysDeptService.selectById(deptId);
        return ResponseEntity.ok(dept);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("sys:dept:save")
    public ResponseEntity<Boolean> save(@RequestBody SysDept dept) {
        boolean b = sysDeptService.insert(dept);
        return ResponseEntity.ok(b);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("sys:dept:update")
    public ResponseEntity<Boolean> update(@RequestBody SysDept dept) {
        boolean b = sysDeptService.updateById(dept);
        return ResponseEntity.ok(b);
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:dept:delete")
    public ResponseEntity<Object> delete(long deptId) {
        //判断是否有子部门
        List<Long> deptList = sysDeptService.queryDetpIdList(deptId);
        if (deptList.size() > 0) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("请先删除子部门");
        }
        //逻辑删除
        SysDept dept = new SysDept();
        dept.setDeptId(deptId);
        dept.setDelFlag(-1);
        boolean b = sysDeptService.updateById(dept);
        //物理删除
        //sysDeptService.delete(deptId);
        return ResponseEntity.ok(b);
    }

}
