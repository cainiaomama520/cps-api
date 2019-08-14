package com.mex.pdd.base.common.utils;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 查询参数
 *
 * @author david
 */
@ApiModel("渠道可拉列表参数")
@Data
@Accessors(chain = true)
public class ChannelOfferPageQuery<T> extends PageQuery<T> {
    @ApiModelProperty("渠道id")
    private int channelId;
    @ApiModelProperty("ID")
    private List<String> uniqueIds;
    @ApiModelProperty("offerid")
    private List<String> offerIds;
    @ApiModelProperty("订单名称")
    private String name;
}
