package com.mex.pdd.modules.api;

import com.pdd.pop.sdk.common.util.JsonUtil;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.PopHttpClient;
import com.pdd.pop.sdk.http.api.request.PddDdkGoodsPidGenerateRequest;
import com.pdd.pop.sdk.http.api.request.PddDdkGoodsPidQueryRequest;
import com.pdd.pop.sdk.http.api.request.PddDdkGoodsPromotionUrlGenerateRequest;
import com.pdd.pop.sdk.http.api.response.PddDdkGoodsPidGenerateResponse;
import com.pdd.pop.sdk.http.api.response.PddDdkGoodsPidQueryResponse;
import com.pdd.pop.sdk.http.api.response.PddDdkGoodsPromotionUrlGenerateResponse;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PddTest {
    String clientId = "be0356fc977b412cb9c75fd10c2d7e19";
    String clientSecret = "4da70d9f64731cde6c47058b3acab8243ccd5df9";
    PopClient client = new PopHttpClient(clientId, clientSecret);

    //生成广告位
    @Test
    public void genGoods() throws Exception {
        PddDdkGoodsPidGenerateRequest request = new PddDdkGoodsPidGenerateRequest();
        request.setNumber(1L);
//        List<String> pIdNameList = new ArrayList<String>();
//        pIdNameList.add("test123");
//        pIdNameList.add("test456");
//        request.setPIdNameList(pIdNameList);
        PddDdkGoodsPidGenerateResponse response = client.syncInvoke(request);
        PddDdkGoodsPidGenerateResponse.PIdGenerateResponse resp = response.getPIdGenerateResponse();
        System.out.println(JsonUtil.transferToJson(resp));
        System.out.println(JsonUtil.transferToJson(response));


    }

    //广告位查询
    @Test
    public void queryGoods() throws Exception {
        PddDdkGoodsPidQueryRequest req = new PddDdkGoodsPidQueryRequest();
        req.setPage(1);
        req.setPageSize(10);
        PddDdkGoodsPidQueryResponse resp = client.syncInvoke(req);
        System.out.println(resp.getErrorResponse());
        System.out.println(JsonUtil.transferToJson(resp));
    }

    //
    @Test
    public void genGoodsPromotion() throws Exception {
        PddDdkGoodsPromotionUrlGenerateRequest request = new PddDdkGoodsPromotionUrlGenerateRequest();
        request.setPId("8611765_65069540");
        List<Long> goodsIdList = new ArrayList<Long>();
        goodsIdList.add(0L);
        request.setGoodsIdList(goodsIdList);
        request.setGenerateShortUrl(true);
        request.setMultiGroup(true);
        request.setCustomParameters("test");
        request.setGenerateWeappWebview(true);
        request.setGenerateWeApp(true);
        request.setGenerateWeiboappWebview(true);
        PddDdkGoodsPromotionUrlGenerateResponse response = client.syncInvoke(request);
        System.out.println(JsonUtil.transferToJson(response));
    }
}
