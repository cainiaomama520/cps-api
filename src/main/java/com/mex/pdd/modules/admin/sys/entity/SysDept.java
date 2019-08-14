package com.mex.pdd.modules.admin.sys.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 部门管理
 * </p>
 *
 * @author theodo
 * @since 2017-10-28
 */
@Data
@TableName("sys_dept")
public class SysDept extends Model<SysDept> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "dept_id", type = IdType.AUTO)
    private Long deptId;
    /**
     * 上级部门ID，一级部门为0
     */
    @TableField("parent_id")
    private Long parentId;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 排序
     */
    @TableField("order_num")
    private Integer orderNum;
    /**
     * 是否删除  -1：已删除  0：正常
     */
    @TableField("del_flag")
    private Integer delFlag;

    /**
     * 上级部门名称
     */
    @TableField(exist = false)
    private String parentName;
    /**
     * ztree属性
     */
    @TableField(exist = false)
    private Boolean open;

    @TableField(exist = false)
    private List<?> list;

    @Override
    protected Serializable pkVal() {
        return this.deptId;
    }

}
