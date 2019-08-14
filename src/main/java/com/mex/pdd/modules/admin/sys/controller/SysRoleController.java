package com.mex.pdd.modules.admin.sys.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.mex.pdd.base.common.annotation.Log;
import com.mex.pdd.base.common.controller.base.BaseController;
import com.mex.pdd.base.common.entity.DropdownBean;
import com.mex.pdd.base.common.utils.PageQuery;
import com.mex.pdd.base.common.validator.ValidatorUtils;
import com.mex.pdd.modules.admin.sys.entity.SysRole;
import com.mex.pdd.modules.admin.sys.service.SysRoleDeptService;
import com.mex.pdd.modules.admin.sys.service.SysRoleMenuService;
import com.mex.pdd.modules.admin.sys.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 角色管理
 *
 * @author theodo
 * @email 36780272@qq.com
 * @date 2016年11月8日 下午2:18:33
 */
@RestController
@RequestMapping("/sys/role")
@Api(description = "角色管理", tags = {"角色相关"})
public class SysRoleController extends BaseController {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysRoleDeptService sysRoleDeptService;


    @GetMapping("/list")
    @RequiresPermissions("sys:role:list")
    @ApiOperation("角色列表")
    public ResponseEntity<Page<SysRole>> list(@RequestBody PageQuery<SysRole> pageQuery) {
        Page<SysRole> page = sysRoleService.selectPage(pageQuery);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/dropdown")
    @ApiOperation("角色下拉")
    public ResponseEntity<List<DropdownBean>> select() {
        Map<String, Object> map = new HashMap<>();
        List<DropdownBean> list = sysRoleService.queryList(map).stream().map(role -> new DropdownBean().setId(role.getRoleId()).setName(role.getRoleName())).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    /**
     * 角色信息
     */
    @GetMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:info")
    public ResponseEntity<SysRole> info(@PathVariable("roleId") Long roleId) {
        SysRole role = sysRoleService.selectById(roleId);
        //查询角色对应的菜单
        List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
        role.setMenuIdList(menuIdList);
//        //查询角色对应的部门
//        List<Long> deptIdList = sysRoleDeptService.queryDeptIdList(roleId);
//        role.setDeptIdList(deptIdList);
        return ResponseEntity.ok(role);
    }

    /**
     * 保存角色
     */
    @Log("保存角色")
    @PostMapping("/save")
    @RequiresPermissions("sys:role:save")
    public ResponseEntity<Void> save(@RequestBody SysRole role) {
        ValidatorUtils.validateEntity(role);
        sysRoleService.save(role);
        return ResponseEntity.ok(null);
    }

    /**
     * 修改角色
     */
    @Log("修改角色")
    @PostMapping("/update")
    @RequiresPermissions("sys:role:update")
    public ResponseEntity<Void> update(@RequestBody SysRole role) {
        ValidatorUtils.validateEntity(role);
        sysRoleService.update(role);
        return ResponseEntity.ok(null);
    }

    /**
     * 删除角色
     */
    @Log("删除角色")
    @PostMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    public ResponseEntity<Void> delete(@RequestBody Long[] roleIds) {
        sysRoleService.deleteBatch(roleIds);
        return ResponseEntity.ok(null);
    }
}
