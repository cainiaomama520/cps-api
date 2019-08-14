package com.mex.pdd.modules.pdd;

import com.mex.pdd.base.common.exception.RRException;
import com.mex.pdd.config.ApplicationProperties;
import com.mex.pdd.modules.api.bean.GenGoodsDTO;
import com.pdd.pop.sdk.http.PopBaseHttpResponse;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.PopHttpClient;
import com.pdd.pop.sdk.http.api.request.PddDdkGoodsPidGenerateRequest;
import com.pdd.pop.sdk.http.api.request.PddDdkGoodsPidQueryRequest;
import com.pdd.pop.sdk.http.api.response.PddDdkGoodsPidGenerateResponse;
import com.pdd.pop.sdk.http.api.response.PddDdkGoodsPidQueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Service
public class PddService {
    @Autowired
    private ApplicationProperties properties;
    public PopClient client;

    @PostConstruct
    public void init() {
        ApplicationProperties.Pdd pdd = properties.getPdd();
        client = new PopHttpClient(pdd.getClient_id(), pdd.getClient_secret());
    }

    //处理异常
    public void dealException(PopBaseHttpResponse response) {
        PopBaseHttpResponse.ErrorResponse error = response.getErrorResponse();
        if (Objects.nonNull(error)) {
            throw new RRException(String.format("拼多多API错误 %S 错误码:%S", error.getErrorMsg(), error.getErrorCode()));
        }
    }

    //创建广告位
    public PddDdkGoodsPidGenerateResponse genGoodsPid(GenGoodsDTO dto) throws Exception {
        PddDdkGoodsPidGenerateRequest request = new PddDdkGoodsPidGenerateRequest();
        request.setNumber(dto.getNumber());
        request.setPIdNameList(dto.getPIdNameList());
        PddDdkGoodsPidGenerateResponse response = client.syncInvoke(request);
        dealException(response);
        return response;
    }

    //查询广告位
    public PddDdkGoodsPidQueryResponse queryGoodsPid(Integer page, Integer pageSize) throws Exception {
        PddDdkGoodsPidQueryRequest req = new PddDdkGoodsPidQueryRequest();
        req.setPage(page);
        req.setPageSize(pageSize);
        PddDdkGoodsPidQueryResponse resp = client.syncInvoke(req);
        dealException(resp);
        return resp;
    }
}
