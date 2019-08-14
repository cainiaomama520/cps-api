package com.mex.pdd.modules.api.service.impl;

import com.mex.pdd.modules.api.bean.GenGoodsDTO;
import com.mex.pdd.modules.api.bean.GenGoodsVO;
import com.mex.pdd.modules.api.entity.GoodsPid;
import com.mex.pdd.modules.api.mapper.GoodsPidDao;
import com.mex.pdd.modules.api.service.GoodsPidService;
import com.mex.pdd.base.common.service.BaseServiceImpl;
import com.mex.pdd.modules.pdd.PddService;
import com.pdd.pop.sdk.common.util.JsonUtil;
import com.pdd.pop.sdk.http.api.response.PddDdkGoodsPidQueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 广告位表 服务实现类
 * </p>
 *
 * @author david
 * @since 2019-05-07
 */
@Service
public class GoodsPidServiceImpl extends BaseServiceImpl<GoodsPidDao, GoodsPid> implements GoodsPidService {
    @Autowired
    private PddService pddService;

    @Override
    @Transactional
    public GenGoodsVO save(GenGoodsDTO dto) throws Exception {
        Date date = new Date();
        pddService.genGoodsPid(dto);
        Long number = dto.getNumber();
        PddDdkGoodsPidQueryResponse response = pddService.queryGoodsPid(1, Math.toIntExact(number <= 10 ? 10 : number));
        List<GoodsPid> list = response.getPIdQueryResponse().getPIdList().stream().limit(number).map(i -> {
            GoodsPid entity = new GoodsPid();
            entity.setPid(i.getPId());
            entity.setPidName(i.getPidName());
            entity.setCreateDate(date);
            entity.setUpdateDate(date);
            return entity;
        }).collect(Collectors.toList());
        super.insertBatch(list);
        return new GenGoodsVO().setList(list).setResponse(JsonUtil.transferToJson(response));
    }
}
