package com.mex.pdd.base.common.entity.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@ApiModel("直客创意关联")
public class CpaCreativeOfferLinkDTO {
    @NotNull(message = "创意id不能为空")
    private Long creativeId;
    @NotEmpty(message = "订单id不能为空")
    private Set<Long> offerIds;
}
