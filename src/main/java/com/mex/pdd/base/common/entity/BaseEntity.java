package com.mex.pdd.base.common.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import com.mex.pdd.base.common.annotation.DataId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Data
@Accessors(chain = true)
public class BaseEntity<T extends BaseEntity> extends Model<T> {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("id")
    @DataId
    private Long id;
    @ApiModelProperty("创建者")
    private Long createBy;
    @ApiModelProperty("创建者")
    private Long updateBy;
    @ApiModelProperty("创建时间")
    private Date createDate;
    @ApiModelProperty("更新时间者")
    private Date updateDate;
    @TableLogic
    @ApiModelProperty("删除标记 0:删除,1:存在")
    private Integer delFlag;
    @ApiModelProperty("备注")
    private String remarks;

    @Override
    protected Serializable pkVal() {
        return getId();
    }

    public boolean isNewRecord() {
        return Objects.isNull(getId());
    }
}
