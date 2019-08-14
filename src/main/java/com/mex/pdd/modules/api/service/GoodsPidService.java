package com.mex.pdd.modules.api.service;

import com.mex.pdd.modules.api.bean.GenGoodsDTO;
import com.mex.pdd.modules.api.bean.GenGoodsVO;
import com.mex.pdd.modules.api.entity.GoodsPid;
import com.mex.pdd.base.common.service.BaseService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 广告位表 服务类
 * </p>
 *
 * @author david
 * @since 2019-05-07
 */
public interface GoodsPidService extends BaseService<GoodsPid> {

    @Transactional
    GenGoodsVO save(GenGoodsDTO dto) throws Exception;
}
