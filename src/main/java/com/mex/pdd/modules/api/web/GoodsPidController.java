package com.mex.pdd.modules.api.web;


import com.mex.pdd.modules.api.bean.GenGoodsDTO;
import com.mex.pdd.modules.api.bean.GenGoodsVO;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import com.mex.pdd.base.common.controller.base.BaseController;

import com.mex.pdd.modules.api.service.GoodsPidService;
import com.mex.pdd.modules.api.entity.GoodsPid;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.plugins.Page;
import com.mex.pdd.base.common.utils.PageQuery;
import org.springframework.http.ResponseEntity;

/**
 * <p>
 * 广告位表 前端控制器
 * </p>
 *
 * @author david
 * @since 2019-05-07
 */
@Controller
@Api(description = "广告位表管理", tags = {"广告位表"})
@RequestMapping("/api")
public class GoodsPidController extends BaseController {
    @Autowired
    private GoodsPidService goodsPidService;

    @PostMapping("/goodsPid-list")
    @ApiOperation(value = "列表")
    public ResponseEntity<Page<GoodsPid>> list(@RequestBody PageQuery<GoodsPid> pageQuery) {
        Page<GoodsPid> page = goodsPidService.selectPage(pageQuery);
        return ResponseEntity.ok(page);
    }


    @PostMapping("/goodsPid")
    @ApiOperation(value = "保存")
    public ResponseEntity<GenGoodsVO> save(@RequestBody GenGoodsDTO dto) throws Exception {
        GenGoodsVO res = goodsPidService.save(dto);
        return ResponseEntity.ok(res);
    }


}
