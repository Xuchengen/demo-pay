package com.github.xuchengen.web.alipay;

import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.github.xuchengen.support.response.ResponseResult;
import com.github.xuchengen.web.BaseCtl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 支付宝-退款
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/9/24
 */
@Controller
@RequestMapping(value = "/alipay/refund")
public class AliPayRefundCtl extends BaseCtl {

    private static final Log log = LogFactory.get(AliPayRefundCtl.class);

    @GetMapping(value = {"", "/"})
    public String index() {
        return "/alipay/refund.html";
    }

    @PostMapping(value = "/doRefund")
    @ResponseBody
    public ResponseResult doRefund(String outRequestNo,
                                   String outTradeNo,
                                   String tradeNo,
                                   String refundAmount,
                                   String refundReason) {
        try {
            AlipayClient client = getAlipayClient();

            AlipayTradeRefundRequest alipayTradeRefundRequest = new AlipayTradeRefundRequest();

            AlipayTradeRefundModel alipayTradeRefundModel = new AlipayTradeRefundModel();
            alipayTradeRefundModel.setOutRequestNo(outRequestNo);
            alipayTradeRefundModel.setOutTradeNo(outTradeNo);
            alipayTradeRefundModel.setTradeNo(tradeNo);
            alipayTradeRefundModel.setRefundAmount(refundAmount);
            alipayTradeRefundModel.setRefundReason(refundReason);

            alipayTradeRefundRequest.setBizModel(alipayTradeRefundModel);

            AlipayTradeRefundResponse response = client.execute(alipayTradeRefundRequest);

            log.info("支付宝退款响应参数：{}", JSONUtil.toJsonStr(response));

            if (response.isSuccess()) {
                return ResponseResult.ok();
            } else {
                return ResponseResult.fail(response.getSubMsg());
            }

        } catch (Exception e) {
            log.error("调用支付宝退款接口异常：{}", JSONUtil.toJsonStr(e));
            throw new RuntimeException("支付宝退款异常", e);
        }
    }
}
