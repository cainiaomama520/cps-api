package com.mex.pdd.base.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Builder
@ApiModel("创意详情")
@NoArgsConstructor
@AllArgsConstructor
public class CreativeContentItem extends BaseBean {
    @ApiModelProperty("宽")
    private int w;
    @ApiModelProperty("高")
    private int h;
    @ApiModelProperty("链接")
    private String url;
    @ApiModelProperty("名称")
    private String name;
}
