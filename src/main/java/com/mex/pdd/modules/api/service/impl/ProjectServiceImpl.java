package com.mex.pdd.modules.api.service.impl;

import com.mex.pdd.base.common.controller.utils.ShiroUtils;
import com.mex.pdd.base.common.service.BaseServiceImpl;
import com.mex.pdd.modules.admin.sys.entity.SysUser;
import com.mex.pdd.modules.api.entity.Project;
import com.mex.pdd.modules.api.mapper.ProjectDao;
import com.mex.pdd.modules.api.service.ProjectService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 项目表 服务实现类
 * </p>
 *
 * @author david
 * @since 2019-01-11
 */
@Service
public class ProjectServiceImpl extends BaseServiceImpl<ProjectDao, Project> implements ProjectService {

    //    @Log("更新")
    @Override
    public boolean updateById(Project entity) {
        return super.updateById(entity);
    }

    //    @Log("创建")
    @Transactional
    @Override
    public boolean insert(Project entity) {
//        throw  new RuntimeException("hahaha");
        boolean b = super.insert(entity);
        int i  = 1/0;
        return b;
    }

    @Override
    public boolean update(Project project) {
        return updateById(project);
    }

    @Async
    @Override
    public void async(String ip) {
        SysUser user = ShiroUtils.getUser();

        System.out.println(ip);
        System.out.println(user);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.err.println("sync");
    }
}
