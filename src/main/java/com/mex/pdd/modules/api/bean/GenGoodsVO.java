package com.mex.pdd.modules.api.bean;

import com.mex.pdd.base.common.entity.BaseBean;
import com.mex.pdd.modules.api.entity.GoodsPid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@ApiModel("")
public class GenGoodsVO extends BaseBean {
    @ApiModelProperty("列表")
    private List<GoodsPid> list;
    @ApiModelProperty("原始返回值")
    private String response;
}
