package com.mex.pdd.modules.admin.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.mex.pdd.modules.admin.sys.entity.SysLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统日志 Mapper 接口
 * </p>
 *
 * @author theodo
 * @since 2017-10-28
 */
@Mapper
public interface SysLogDao extends BaseMapper<SysLog> {

    /**
     * <p>
     * 查询 : 根据state状态查询用户列表，分页显示
     * </p>
     *
     * @param page  翻页对象，可以作为 xml 参数直接使用，传递参数 Page 即自动分页
     * @param state 状态
     * @return
     */
    List<SysLog> selectPageList(Pagination page, Map<String, Object> map);

}