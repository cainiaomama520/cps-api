package com.mex.pdd.modules.api.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.mex.pdd.base.common.annotation.DataName;
import com.mex.pdd.base.common.entity.CreativeContentItem;
import com.mex.pdd.base.common.entity.DataEntity;
import com.mex.pdd.base.common.entity.TestBean;
import com.mex.pdd.base.common.enums.Country;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 项目表
 * </p>
 *
 * @author david
 * @since 2019-01-13
 */
@Data
@Accessors(chain = true)
@TableName(value = "t_project", resultMap = "ProjectResultMap")
@ApiModel("项目表")
public class Project extends DataEntity<Project> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("项目名称")
    @DataName
    private String name;
    @ApiModelProperty("项目编号")
    private String sn;
    @ApiModelProperty("合同编号")
    @TableField(el = "contractNo,typeHandler=com.mex.pdd.base.common.utils.mybatis.JsonTypeHandler")
    private List<Country> contractNo;
    @ApiModelProperty("所属平台")
    private Integer plat;
    @ApiModelProperty("广告主ID")
    private String adverId;
    @ApiModelProperty("广告主体客户编号")
    @TableField(el = "adverSubjectCode,typeHandler=com.mex.pdd.base.common.utils.mybatis.JsonTypeHandler")
    private List<CreativeContentItem> adverSubjectCode;
    @ApiModelProperty("返点形式")
    private Integer rebateType;
    @ApiModelProperty("折扣类型 1返现2返货")
    private Integer discountType;
    @ApiModelProperty("返点信息JSON")
    @TableField(el = "rebateDetail,typeHandler=com.mex.pdd.base.common.utils.mybatis.JsonTypeHandler")
    private TestBean rebateDetail;
    @ApiModelProperty("公司主体ID")
    private Integer companyId;
    @ApiModelProperty("签约金额")
    private BigDecimal contractAmt;
    @ApiModelProperty("项目开始时间")
    private String startDate;
    @ApiModelProperty("项目结束时间")
    private String endDate;
    @ApiModelProperty("序号")
    private Integer serial;

}
