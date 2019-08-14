package com.mex.pdd.base.common.service;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mex.pdd.base.common.controller.utils.ShiroUtils;
import com.mex.pdd.base.common.entity.BaseEntity;
import com.mex.pdd.base.common.utils.Beans;
import com.mex.pdd.base.common.utils.PageQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements BaseService<T> {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Transactional
    @Override
    public boolean insert(T entity) {
        preInsert(entity);
        return super.insert(entity);
    }

    //    @DataFilter(userId = "create_by")
    public Page<T> selectPage(PageQuery<T> pageQuery) {
        Page<T> p = new Page<>(pageQuery.getPage(), pageQuery.getLimit());
        Wrapper<T> wrapper = pageQuery.buildWrapper();
        //TODO 先去掉where
        wrapper.isWhere(false);
        return super.selectPage(p, wrapper);
    }

    @Override
    public List<T> selectList(PageQuery<T> pageQuery) {
        Wrapper<T> wrapper = pageQuery.buildWrapper();
        wrapper.isWhere(false);
        return super.selectList(wrapper);
    }

    @Transactional
    @Override
    public boolean updateById(T entity) {
        dealUpdate(entity);
        preUpdate(entity);
        return super.updateAllColumnById(entity);
//        preUpdate(entity);
//        return super.updateById(entity);
    }

    @Override
    public boolean updateAllColumnById(T entity) {
        dealUpdate(entity);
        preUpdate(entity);
        return super.updateAllColumnById(entity);
    }

    //只更新非空字段
    private T dealUpdate(T t) {
        Long id = t.getId();
        if (Objects.nonNull(id)) {
            final T db = selectById(id);
            final List<String> nonNullProperties = Beans.getNonNullProperties(t);
            BeanUtils.copyProperties(db, t, nonNullProperties.toArray(new String[]{}));
        }
        return t;
    }


    private void preUpdate(T entity) {
        Date date = new Date();
        entity.setUpdateDate(date);
        if (Objects.nonNull(entity.getUpdateBy())) return;
        Optional<Long> userId = ShiroUtils.getUserId();
        userId.ifPresent(entity::setUpdateBy);
    }

    private void preInsert(T entity) {
        Date date = new Date();
        entity.setCreateDate(date);
        entity.setUpdateDate(date);
        if (Objects.nonNull(entity.getCreateBy())) return;
        Optional<Long> userId = ShiroUtils.getUserId();
        userId.ifPresent(id -> {
            entity.setUpdateBy(id);
            entity.setCreateBy(id);
        });
    }

}
