package com.mex.pdd.modules.admin.sys.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.mex.pdd.base.common.controller.base.BaseController;
import com.mex.pdd.base.common.entity.StringDropdownBean;
import com.mex.pdd.base.common.enums.DictType;
import com.mex.pdd.base.common.utils.PageQuery;
import com.mex.pdd.modules.admin.sys.entity.SysDict;
import com.mex.pdd.modules.admin.sys.service.SysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 数据字典表 前端控制器
 * </p>
 *
 * @author david
 * @since 2019-01-14
 */
@Controller
@Api(description = "数据字典表管理", tags = {"数据字典表"})
@RequestMapping("/admin.sys")
public class SysDictController extends BaseController {
    @Autowired
    private SysDictService sysDictService;

    @PostMapping("/sysDict-list")
    @ApiOperation(value = "列表")
    public ResponseEntity<Page<SysDict>> list(@RequestBody PageQuery<SysDict> pageQuery) {
        Page<SysDict> page = sysDictService.selectPage(pageQuery);
        return ResponseEntity.ok(page);
    }

    @PostMapping("/sysDict-dropdown")
    @ApiOperation(value = "列表")
    public ResponseEntity<List<StringDropdownBean>> dropdown(DictType type) {
        List<StringDropdownBean> dropdown = sysDictService.dropdown(type);
        return ResponseEntity.ok(dropdown);
    }

    @GetMapping("/sysDict/{id}")
    @ApiOperation(value = "详情")
    public ResponseEntity<SysDict> info(@PathVariable("id") Long id) {
        SysDict sysDict = sysDictService.selectById(id);
        return ResponseEntity.ok(sysDict);
    }

    @PostMapping("/sysDict")
    @ApiOperation(value = "保存")
    public ResponseEntity<Boolean> save(@RequestBody SysDict sysDict) {
        boolean b = sysDictService.insert(sysDict);
        return ResponseEntity.ok(b);
    }

    @PutMapping("/sysDict")
    @ApiOperation(value = "修改")
    public ResponseEntity<Boolean> update(@RequestBody SysDict sysDict) {
        boolean b = sysDictService.updateById(sysDict);
        return ResponseEntity.ok(b);
    }

    @DeleteMapping("/sysDict/{ids}")
    @ApiOperation(value = "删除")
    public ResponseEntity<Boolean> delete(@PathVariable("ids") Long[] ids) {
        boolean b = sysDictService.deleteBatchIds(Arrays.asList(ids));
        return ResponseEntity.ok(b);
    }

}
