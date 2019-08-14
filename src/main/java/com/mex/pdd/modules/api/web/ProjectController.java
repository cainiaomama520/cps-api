package com.mex.pdd.modules.api.web;


import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.ImmutableMap;
import com.mex.pdd.base.common.controller.base.BaseController;
import com.mex.pdd.base.common.utils.HttpContextUtils;
import com.mex.pdd.base.common.utils.IPUtils;
import com.mex.pdd.base.common.utils.PageQuery;
import com.mex.pdd.modules.api.entity.Project;
import com.mex.pdd.modules.api.service.ProjectService;
import com.pdd.pop.sdk.http.api.request.PddDdkGoodsPromotionUrlGenerateRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 项目表 前端控制器
 * </p>
 *
 * @author david
 * @since 2019-01-13
 */
@Controller
@Api(description = "项目表管理", tags = {"项目表"})
@RequestMapping("/api")
public class ProjectController extends BaseController {
    @Autowired
    private ProjectService projectService;

    @PostMapping("/project-error")
    @ApiOperation(value = "列表")
    public ResponseEntity<Void> error() throws MissingServletRequestParameterException {
        throw new MissingServletRequestParameterException("name", "value");
//        return ResponseEntity.ok(null);
    }
    @PostMapping("/test")
    @ApiOperation(value = "列表")
    public ResponseEntity<PddDdkGoodsPromotionUrlGenerateRequest> error1(PddDdkGoodsPromotionUrlGenerateRequest request) throws MissingServletRequestParameterException {
        throw new MissingServletRequestParameterException("name", "value");
//        return ResponseEntity.ok(null);
    }


    @GetMapping("/project-select-map")
    @ApiOperation(value = "列表")
    public ResponseEntity<List<Project>> map() {
        List<Project> list = projectService.selectByMap(ImmutableMap.of("adverId","1"));
        return ResponseEntity.ok(list);
    }

    @PostMapping("/project-async")
    @ApiOperation(value = "列表")
    public ResponseEntity<Void> async() {
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        String ip = IPUtils.getIpAddr(request);
        projectService.async(ip);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/project-list")
    @ApiOperation(value = "列表")
    public ResponseEntity<Page<Project>> list(@RequestBody PageQuery<Project> pageQuery) {
        Page<Project> page = projectService.selectPage(pageQuery);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/project/{id}")
    @ApiOperation(value = "详情")
    public ResponseEntity<Project> info(@PathVariable("id") Long id) {
        Project project = projectService.selectById(id);
        return ResponseEntity.ok(project);
    }

    @PostMapping("/project")
    @ApiOperation(value = "保存")
    public ResponseEntity<Boolean> save(@RequestBody Project project) {
        boolean b = projectService.insert(project);
        return ResponseEntity.ok(b);
    }

    @PutMapping("/project")
    @ApiOperation(value = "修改")
    public ResponseEntity<Boolean> update(@RequestBody Project project) {
        boolean b = projectService.updateById(project);
        return ResponseEntity.ok(b);
    }

    @PutMapping("/project-update")
    @ApiOperation(value = "修改")
    public ResponseEntity<Boolean> update1(@RequestBody Project project) {
        boolean b = projectService.update(project);
        return ResponseEntity.ok(b);
    }

    @DeleteMapping("/project/{ids}")
    @ApiOperation(value = "删除")
    public ResponseEntity<Boolean> delete(@PathVariable("ids") Long[] ids) {
        boolean b = projectService.deleteBatchIds(Arrays.asList(ids));
        return ResponseEntity.ok(b);
    }

}
