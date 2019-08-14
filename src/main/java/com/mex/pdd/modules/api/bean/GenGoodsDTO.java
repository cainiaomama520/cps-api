package com.mex.pdd.modules.api.bean;

import com.mex.pdd.base.common.entity.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("创建广告位")
public class GenGoodsDTO extends BaseBean {
    @ApiModelProperty("个数")
    private Long number;
    @ApiModelProperty("名称")
    private List<String> pIdNameList;
}
