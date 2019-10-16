package com.github.xuchengen.web.alipay;

import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeCancelModel;
import com.alipay.api.request.AlipayTradeCancelRequest;
import com.alipay.api.response.AlipayTradeCancelResponse;
import com.github.xuchengen.support.response.ResponseResult;
import com.github.xuchengen.web.BaseCtl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 支付宝-交易撤销
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/9/24
 */
@Controller
@RequestMapping(value = "/alipay/cancel")
public class AliPayCancelCtl extends BaseCtl {

    private static final Log log = LogFactory.get(AliPayCancelCtl.class);

    @GetMapping(value = {"", "/"})
    public String index() {
        return "/alipay/cancel.html";
    }

    @PostMapping(value = "/doCancel")
    @ResponseBody
    public ResponseResult doCancel(String tradeNo,
                                   String outTradeNo) {
        try {
            AlipayClient client = getAlipayClient();

            AlipayTradeCancelRequest alipayTradeCancelRequest = new AlipayTradeCancelRequest();

            AlipayTradeCancelModel alipayTradeCancelModel = new AlipayTradeCancelModel();
            alipayTradeCancelModel.setOutTradeNo(outTradeNo);
            alipayTradeCancelModel.setTradeNo(tradeNo);

            alipayTradeCancelRequest.setBizModel(alipayTradeCancelModel);

            AlipayTradeCancelResponse response = client.execute(alipayTradeCancelRequest);

            log.info("支付宝交易撤销接口响应参数：{}", JSONUtil.toJsonStr(response));

            if (response.isSuccess()) {
                return ResponseResult.ok();
            } else {
                return ResponseResult.fail(response.getSubMsg());
            }

        } catch (Exception e) {
            log.error("调用支付宝交易撤销接口异常：{}", JSONUtil.toJsonStr(e));
            throw new RuntimeException("支付宝交易撤销异常", e);
        }
    }
}
