package com.mex.pdd.base.common.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@ApiModel("订单组删除")
public class ApiOfferDelDTO {
    @NotNull(message = "订单组id不能为空")
    @ApiModelProperty("订单组id")
    private Long offerGroupId;
    @NotNull(message = "订单id不能为空")
    @ApiModelProperty("订单id")
    private Set<String> offerIds;
}
