package com.mex.pdd.modules.api.support;

import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;
import java.util.Objects;

public class PageHelper {
    private static final int PAGE_SIZE = 10;
    private static final int FIRST_PAGE = 1;

    public static <T> Page<T> of(Page page1, List<T> records) {
        Page<T> page = new Page<>(page1.getCurrent(), page1.getSize());
        page.setRecords(records);
        return page;
    }

    public static <T> Page<T> of(Integer currentPage, Integer pageSize) {
        if (Objects.isNull(currentPage)) {
            currentPage = FIRST_PAGE;
        }
        if (Objects.isNull(pageSize)) {
            pageSize = PAGE_SIZE;
        }
        return new Page<>(currentPage, pageSize);
    }
}
