package com.mex.pdd.modules.api.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.mex.pdd.base.common.entity.BaseEntity;

import com.baomidou.mybatisplus.annotations.Version;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 广告位表
 * </p>
 *
 * @author david
 * @since 2019-05-07
 */
@Data
@Accessors(chain = true)
@TableName("t_goods_pid")
@ApiModel("广告位表")
public class GoodsPid extends BaseEntity<GoodsPid> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("推广位id")
    private String pid;
    @ApiModelProperty("推广位名称")
    private String pidName;

    @Override
    protected Serializable pkVal() {
        return this.getId();
    }

}
