package com.mex.pdd.modules.admin.sys.controller;

import com.mex.pdd.base.common.annotation.Log;
import com.mex.pdd.base.common.controller.base.BaseController;
import com.mex.pdd.base.common.entity.vo.MenuNavVO;
import com.mex.pdd.base.common.exception.RRException;
import com.mex.pdd.base.common.utils.Constant;
import com.mex.pdd.modules.admin.sys.entity.SysMenu;
import com.mex.pdd.modules.admin.sys.service.ShiroService;
import com.mex.pdd.modules.admin.sys.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 系统菜单
 *
 * @author theodo
 * @email 36780272@qq.com
 * @date 2016年10月27日 下午9:58:15
 */
@RestController
@RequestMapping("/sys/menu")
@Api(description = "菜单管理", tags = {"菜单表"})
public class SysMenuController extends BaseController {
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private ShiroService shiroService;

    /**
     * 导航菜单
     */
    @GetMapping("/nav")
    public ResponseEntity<MenuNavVO> nav() {
        List<SysMenu> menuList = sysMenuService.getUserMenuList(getUserId());
        Set<String> permissions = shiroService.getUserPermissions(getUserId());
        MenuNavVO vo = new MenuNavVO();
        vo.setMenuList(menuList);
        vo.setPermissions(permissions);
        return ResponseEntity.ok(vo);
    }

    /**
     * 所有菜单列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:menu:list")
    public ResponseEntity<List<SysMenu>> list() {
        List<SysMenu> menuList = sysMenuService.queryList(new HashMap<>());
        return ResponseEntity.ok(menuList);
    }

    /**
     * 所有菜单列表
     */
    @GetMapping("/apiList")
    @RequiresPermissions("sys:menu:list")
    public ResponseEntity<List<SysMenu>> list2() {
        List<SysMenu> menuList = sysMenuService.getUserMenuList(1L);
        return ResponseEntity.ok(menuList);
    }

    /**
     * 选择菜单(添加、修改菜单)
     */
    @GetMapping("/select")
    @RequiresPermissions("sys:menu:select")
    public ResponseEntity<List<SysMenu>> select() {
        //查询列表数据
        List<SysMenu> menuList = sysMenuService.queryNotButtonList();
        //添加顶级菜单
        SysMenu root = new SysMenu();
        root.setMenuId(0L);
        root.setName("一级菜单");
        root.setParentId(-1L);
        root.setOpen(true);
        menuList.add(root);
        return ResponseEntity.ok(menuList);
    }

    /**
     * 菜单信息
     */
    @GetMapping("/info/{menuId}")
    @RequiresPermissions("sys:menu:info")
    public ResponseEntity<SysMenu> info(@PathVariable("menuId") Long menuId) {
        SysMenu menu = sysMenuService.selectById(menuId);
        return ResponseEntity.ok(menu);
    }

    /**
     * 保存
     */
    @Log("保存菜单")
    @PostMapping("/save")
    @RequiresPermissions("sys:menu:save")
    public ResponseEntity<Boolean> save(@RequestBody SysMenu menu) {
        //数据校验
        verifyForm(menu);
        boolean b = sysMenuService.insert(menu);
        return ResponseEntity.ok(b);
    }

    /**
     * 修改
     */
    @Log("修改菜单")
    @PostMapping("/update")
    @RequiresPermissions("sys:menu:update")
    public ResponseEntity<Boolean> update(@RequestBody SysMenu menu) {
        //数据校验
        verifyForm(menu);
        boolean b = sysMenuService.updateById(menu);
        return ResponseEntity.ok(b);
    }

    /**
     * 删除
     */
    @Log("删除菜单")
    @PostMapping("/delete")
    @RequiresPermissions("sys:menu:delete")
    public ResponseEntity delete(long menuId) {
        if (menuId <= 31) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("系统菜单，不能删除");
        }
        //判断是否有子菜单或按钮
        List<SysMenu> menuList = sysMenuService.queryListParentId(menuId);
        if (menuList.size() > 0) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("请先删除子菜单或按钮");
        }
        sysMenuService.deleteBatch(new Long[]{menuId});
        return ResponseEntity.ok(null);
    }

    /**
     * 验证参数是否正确
     */
    private void verifyForm(SysMenu menu) {
        if (StringUtils.isBlank(menu.getName())) {
            throw new RRException("菜单名称不能为空");
        }
        if (menu.getParentId() == null) {
            throw new RRException("上级菜单不能为空");
        }
        //菜单
        if (menu.getType() == Constant.MenuType.MENU.getValue()) {
            if (StringUtils.isBlank(menu.getUrl())) {
                throw new RRException("菜单URL不能为空");
            }
        }
        //上级菜单类型
        int parentType = Constant.MenuType.CATALOG.getValue();
        if (menu.getParentId() != 0) {
            SysMenu parentMenu = sysMenuService.selectById(menu.getParentId());
            parentType = parentMenu.getType();
        }
        //目录、菜单
        if (menu.getType() == Constant.MenuType.CATALOG.getValue() ||
                menu.getType() == Constant.MenuType.MENU.getValue()) {
            if (parentType != Constant.MenuType.CATALOG.getValue()) {
                throw new RRException("上级菜单只能为目录类型");
            }
            return;
        }
        //按钮
        if (menu.getType() == Constant.MenuType.BUTTON.getValue()) {
            if (parentType != Constant.MenuType.MENU.getValue()) {
                throw new RRException("上级菜单只能为菜单类型");
            }
            return;
        }
    }


    @GetMapping("/apiSelect")
    @RequiresPermissions("sys:menu:select")
    @ApiOperation(value = "API选择菜单")
    public ResponseEntity<List<SysMenu>> apiSelect() {
        //查询列表数据
        List<SysMenu> menuList = sysMenuService.queryNotButtonList();
        //添加顶级菜单
        SysMenu root = new SysMenu();
        root.setMenuId(0L);
        root.setName("一级菜单");
        root.setParentId(-1L);
        root.setOpen(true);
        menuList.add(root);
        List<Long> ids = menuList.stream().map(SysMenu::getMenuId).collect(Collectors.toList());
        List<SysMenu> res = sysMenuService.getAllMenuList(ids, false, false);
        return ResponseEntity.ok(res);
    }
}
