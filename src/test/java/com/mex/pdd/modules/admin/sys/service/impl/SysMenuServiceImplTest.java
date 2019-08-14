package com.mex.pdd.modules.admin.sys.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.mex.pdd.modules.admin.sys.entity.SysMenu;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SysMenuServiceImplTest {
    @Autowired
    SysMenuServiceImpl sysMenuService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void queryListParentId() {
        List<SysMenu> sysMenus = sysMenuService.queryListParentId(1L);
        System.out.println(sysMenus.size());
        Page<SysMenu> page = new Page<>();
        Page<SysMenu> sysMenuPage = sysMenuService.selectPage(page);
        System.out.println(sysMenuPage.getRecords().size());
        System.out.println(sysMenuPage.getPages());
    }
}