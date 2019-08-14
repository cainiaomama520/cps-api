package com.mex.pdd.base.common.entity.vo;

import com.mex.pdd.modules.admin.sys.entity.SysMenu;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@ApiModel("菜单导航VO")
public class MenuNavVO {
    List<SysMenu> menuList;
    Set<String> permissions;
}
