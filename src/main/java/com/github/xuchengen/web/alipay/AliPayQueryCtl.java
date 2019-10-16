package com.github.xuchengen.web.alipay;

import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.github.xuchengen.support.response.ResponseResult;
import com.github.xuchengen.web.BaseCtl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 支付宝-交易查询
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/10/8
 */
@Controller
@RequestMapping(value = "/alipay/query")
public class AliPayQueryCtl extends BaseCtl {

    private static final Log log = LogFactory.get(AliPayQueryCtl.class);

    @GetMapping(value = {"", "/"})
    public String index() {
        return "/alipay/query.html";
    }

    @PostMapping(value = "/doQuery")
    @ResponseBody
    public ResponseResult doQuery(String orderNo,
                                  String tradeNo) {
        try {
            AlipayClient client = getAlipayClient();

            AlipayTradeQueryRequest alipayTradeQueryRequest = new AlipayTradeQueryRequest();

            AlipayTradeQueryModel alipayTradeQueryModel = new AlipayTradeQueryModel();
            alipayTradeQueryModel.setOutTradeNo(orderNo);
            alipayTradeQueryModel.setTradeNo(tradeNo);

            alipayTradeQueryRequest.setBizModel(alipayTradeQueryModel);

            AlipayTradeQueryResponse alipayTradeQueryResponse = client.execute(alipayTradeQueryRequest);
            log.info("支付宝交易查询响应参数：{}", JSONUtil.toJsonStr(alipayTradeQueryResponse));
            if (alipayTradeQueryResponse.isSuccess()) {
                return ResponseResult.ok(JSONUtil.formatJsonStr(alipayTradeQueryResponse.getBody()));
            }
            return ResponseResult.fail();
        } catch (Exception e) {
            log.error("调用支付宝交易查询接口异常：{}", JSONUtil.toJsonStr(e));
            return ResponseResult.error();
        }
    }
}
