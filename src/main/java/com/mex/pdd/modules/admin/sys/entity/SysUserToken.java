package com.mex.pdd.modules.admin.sys.entity;





import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统用户Token
 * </p>
 *
 * @author theodo
 * @since 2017-10-28
 */
@TableName("sys_user_token")
@Data
public class SysUserToken extends Model<SysUserToken> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.INPUT)
    private Long userId;
    /**
     * token
     */
    private String token;
    /**
     * 过期时间
     */
    @TableField("expire_time")
    private Date expireTime;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;


    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

}
