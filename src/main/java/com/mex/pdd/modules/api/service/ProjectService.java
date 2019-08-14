package com.mex.pdd.modules.api.service;

import com.mex.pdd.base.common.service.BaseService;
import com.mex.pdd.modules.api.entity.Project;
import org.springframework.scheduling.annotation.Async;

/**
 * <p>
 * 项目表 服务类
 * </p>
 *
 * @author david
 * @since 2019-01-11
 */
public interface ProjectService extends BaseService<Project> {

    boolean update(Project project);

    @Async
    void async(String ip);
}
