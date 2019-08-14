package com.mex.pdd.modules.admin.sys.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.mex.pdd.base.common.annotation.Log;
import com.mex.pdd.base.common.controller.base.BaseController;
import com.mex.pdd.base.common.entity.DropdownBean;
import com.mex.pdd.base.common.utils.PageQuery;
import com.mex.pdd.base.common.validator.Assert;
import com.mex.pdd.base.common.validator.ValidatorUtils;
import com.mex.pdd.base.common.validator.group.AddGroup;
import com.mex.pdd.base.common.validator.group.UpdateGroup;
import com.mex.pdd.modules.admin.sys.entity.SysUser;
import com.mex.pdd.modules.admin.sys.service.SysUserRoleService;
import com.mex.pdd.modules.admin.sys.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 系统用户
 *
 * @author theodo
 * @email 36780272@qq.com
 * @date 2016年10月31日 上午10:40:10
 */
@RestController
@RequestMapping("/sys/user")
@Api(description = "用户管理", tags = {"用户相关"})
public class SysUserController extends BaseController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;


    @PostMapping(value = "/list")
//    @RequiresPermissions("sys:user:list")
    @ApiOperation("所有用户列表")
    public ResponseEntity<Page<SysUser>> list(@RequestBody PageQuery<SysUser> pageQuery) {
        Page<SysUser> page = sysUserService.selectPage(pageQuery);
        return ResponseEntity.ok(page);
    }

    @GetMapping(value = "/dropdown")
    @ApiOperation("下拉列表")
    public ResponseEntity<List<DropdownBean>> dropdown(@RequestParam(required = false) @ApiParam("角色id") Long roleId) {
        List<DropdownBean> dropdown = sysUserService.dropdown(roleId);
        return ResponseEntity.ok(dropdown);
    }

    @GetMapping("/info")
    @ApiOperation("用户信息")
    public ResponseEntity<SysUser> info() {
        SysUser user = getUser();
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(user.getUserId());
        user.setRoleIdList(roleIdList);
        return ResponseEntity.ok(user);
    }


    @Log("修改密码")
    @PutMapping("/password")
    @ApiOperation("修改密码")
    public ResponseEntity<String> password(String password, String newPassword) {
        Assert.isBlank(newPassword, "新密码不为能空");
        //sha256加密
        password = new Sha256Hash(password, getUser().getSalt()).toHex();
        SysUser sysUser = sysUserService.selectById(getUserId());
        if (!StringUtils.equals(sysUser.getPassword(), password)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("原密码不正确");
        }
        //sha256加密
        newPassword = new Sha256Hash(newPassword, getUser().getSalt()).toHex();
        sysUser.setPassword(newPassword);
        //更新密码
        sysUserService.update(sysUser);
        return ResponseEntity.ok("修改成功");
    }


    @GetMapping("/info/{userId}")
//    @RequiresPermissions("sys:user:info")
    @ApiOperation("用户信息")
    public ResponseEntity<SysUser> info(@PathVariable("userId") Long userId) {
        SysUser user = sysUserService.selectById(userId);
        //获取用户所属的角色列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);
        return ResponseEntity.ok().body(user);
    }


    @Log("保存用户")
    @PostMapping("/save")
//    @RequiresPermissions("sys:user:save")
    @ApiOperation("保存用户")
    public ResponseEntity<String> save(@RequestBody SysUser user) {
        ValidatorUtils.validateEntity(user, AddGroup.class);
        user.setCreateTime(new Date());
        user.setCreateUserId(getUserId());
        sysUserService.save(user);
        return ResponseEntity.ok("保存成功");
    }


    @Log("修改用户")
    @PutMapping("/update")
//    @RequiresPermissions("sys:user:update")
    @ApiOperation("修改用户")
    public ResponseEntity<String> update(@RequestBody SysUser user) {
        ValidatorUtils.validateEntity(user, UpdateGroup.class);
        user.setCreateUserId(getUserId());
        sysUserService.update(user);
        return ResponseEntity.ok("修改成功");

    }


    @Log("删除用户")
    @DeleteMapping("/delete")
//    @RequiresPermissions("sys:user:delete")
    @ApiOperation("删除用户")
    public ResponseEntity<String> delete(@RequestBody Long[] userIds) {
        if (ArrayUtils.contains(userIds, 1L)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("系统管理员不能删除");
        }
        if (ArrayUtils.contains(userIds, getUserId())) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("当前用户不能删除");
        }
        sysUserService.deleteBatch(userIds);
        return ResponseEntity.ok("删除成功");
    }
}
