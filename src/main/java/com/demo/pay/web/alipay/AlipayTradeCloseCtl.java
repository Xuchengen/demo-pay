package com.demo.pay.web.alipay;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeCloseModel;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.demo.pay.support.response.ResponseWrapper;
import com.demo.pay.web.BaseCtl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 支付宝-统一收单交易关闭接口
 */
@Controller
@RequestMapping(value = "/alipay/tradeClose")
public class AlipayTradeCloseCtl extends BaseCtl {

    private static final Log log = LogFactory.get(AlipayTradeCloseCtl.class);

    @GetMapping(value = {"", "/"})
    public String page() {
        return "alipay/tradeClose.html";
    }

    @PostMapping(value = "/doClose")
    @ResponseBody
    public ResponseWrapper doClose(String server_url,
                                   String app_id,
                                   String merchant_private_key,
                                   String alipay_public_key,
                                   String charset,
                                   String format,
                                   String sign_type,
                                   String version,
                                   String trade_no,
                                   String out_trade_no) {
        try {
            //会话中暂存
            HttpSession session = getSession();
            session.setAttribute("app_id", app_id);
            session.setAttribute("merchant_private_key", merchant_private_key);
            session.setAttribute("alipay_public_key", alipay_public_key);

            DefaultAlipayClient client = DefaultAlipayClient.builder(server_url, app_id, merchant_private_key)
                    .alipayPublicKey(alipay_public_key)
                    .charset(charset)
                    .format(format)
                    .signType(sign_type)
                    .build();

            AlipayTradeCloseRequest alipayTradeCloseRequest = new AlipayTradeCloseRequest();
            alipayTradeCloseRequest.setApiVersion(version);

            AlipayTradeCloseModel alipayTradeCloseModel = new AlipayTradeCloseModel();
            alipayTradeCloseModel.setTradeNo(trade_no);
            alipayTradeCloseModel.setOutTradeNo(out_trade_no);

            alipayTradeCloseRequest.setBizModel(alipayTradeCloseModel);

            AlipayTradeCloseResponse response = client.execute(alipayTradeCloseRequest);

            if (response.isSuccess()) {
                return ResponseWrapper.ok();
            } else {
                return ResponseWrapper.fail().setData(response.getMsg());
            }
        } catch (Exception e) {
            log.error("调用支付宝统一收单交易关闭接口异常：", e);
            return ResponseWrapper.error();
        }
    }
}
