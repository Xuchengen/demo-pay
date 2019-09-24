package com.github.xuchengen.web.alipay;

import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.github.xuchengen.setting.AliPaySetting;
import com.github.xuchengen.setting.SettingTool;
import com.github.xuchengen.web.BaseCtl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 支付宝-电脑网站支付
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/9/17
 */
@Controller
@RequestMapping(value = "/alipay/pagePay")
public class AliPayPagePayCtl extends BaseCtl {

    private static final Log log = LogFactory.get(AliPayPagePayCtl.class);

    @GetMapping(value = {"", "/"})
    public String index(ModelMap modelMap) {
        modelMap.put("returnUrl", getHostUrl() + "/alipay/pagePay");
        modelMap.put("notifyUrl", getHostUrl() + "/alipay/doNotify");
        return "/alipay/pagePay.html";
    }

    @PostMapping(value = "/doPay")
    @ResponseBody
    public String doPay(String orderNo,
                        String subject,
                        String totalAmount,
                        String notifyUrl,
                        String returnUrl) {
        try {
            AliPaySetting aliPaySetting = SettingTool.getAliPaySetting();

            DefaultAlipayClient client = DefaultAlipayClient.builder(aliPaySetting.getGateway(), aliPaySetting.getAppId(), aliPaySetting.getPrivateKey())
                    .alipayPublicKey(aliPaySetting.getPublicKey())
                    .charset(aliPaySetting.getCharset())
                    .format(aliPaySetting.getFormat())
                    .signType(aliPaySetting.getSignType())
                    .build();

            AlipayTradePagePayRequest alipayTradePagePayRequest = new AlipayTradePagePayRequest();
            alipayTradePagePayRequest.setNotifyUrl(notifyUrl);
            alipayTradePagePayRequest.setReturnUrl(returnUrl);

            AlipayTradePagePayModel alipayTradePagePayModel = new AlipayTradePagePayModel();
            alipayTradePagePayModel.setOutTradeNo(orderNo);
            alipayTradePagePayModel.setSubject(subject);
            alipayTradePagePayModel.setTotalAmount(totalAmount);
            alipayTradePagePayModel.setProductCode("FAST_INSTANT_TRADE_PAY");

            alipayTradePagePayRequest.setBizModel(alipayTradePagePayModel);

            AlipayTradePagePayResponse alipayTradePagePayResponse = client.pageExecute(alipayTradePagePayRequest);
            log.info("支付宝电脑网站支付响应参数：{}", JSONUtil.toJsonStr(alipayTradePagePayResponse));
            if (alipayTradePagePayResponse.isSuccess()) {
                return alipayTradePagePayResponse.getBody();
            } else {
                throw new RuntimeException("调用支付宝电脑网站支付失败");
            }
        } catch (Exception e) {
            log.error("调用支付宝电脑网站支付接口异常：{}", JSONUtil.toJsonStr(e));
            throw new RuntimeException("支付宝电脑网站支付异常", e);
        }
    }

}
