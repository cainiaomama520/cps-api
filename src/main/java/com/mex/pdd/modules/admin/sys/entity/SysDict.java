package com.mex.pdd.modules.admin.sys.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;

import com.mex.pdd.base.common.enums.DictType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 数据字典表
 * </p>
 *
 * @author david
 * @since 2019-01-14
 */
@Data
@Accessors(chain = true)
@ApiModel("数据字典表")
public class SysDict extends Model<SysDict> {

    private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty("id")
	protected Long id;
	@ApiModelProperty("字典名称")
	private String name;
	@ApiModelProperty("字典类型")
	private DictType type;
	@ApiModelProperty("字典码")
	private String code;
	@ApiModelProperty("字典值")
	private String value;
	@ApiModelProperty("排序")
	private Integer orderNum;
	@ApiModelProperty("备注")
	private String remark;
	@TableLogic
	@ApiModelProperty("删除标记 0:删除,1:存在")
	private Integer delFlag;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
