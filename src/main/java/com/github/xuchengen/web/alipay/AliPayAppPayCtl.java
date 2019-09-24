package com.github.xuchengen.web.alipay;

import cn.hutool.cache.impl.TimedCache;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.github.xuchengen.setting.AliPaySetting;
import com.github.xuchengen.setting.SettingTool;
import com.github.xuchengen.web.BaseCtl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付宝-APP支付
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/9/20
 */
@Controller
@RequestMapping(value = "/alipay/appPay")
public class AliPayAppPayCtl extends BaseCtl {

    private static final Log log = LogFactory.get(AliPayAppPayCtl.class);

    @Resource(name = "timedCache")
    public TimedCache<String, Object> timedCache;

    @GetMapping(value = {"", "/"})
    public String index(ModelMap modelMap) {
        modelMap.put("notifyUrl", getHostUrl() + "/alipay/doNotify");
        return "/alipay/appPay.html";
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

            AlipayTradeAppPayRequest alipayTradeAppPayRequest = new AlipayTradeAppPayRequest();
            alipayTradeAppPayRequest.setNotifyUrl(notifyUrl);
            alipayTradeAppPayRequest.setReturnUrl(returnUrl);

            AlipayTradeAppPayModel alipayTradeAppPayModel = new AlipayTradeAppPayModel();
            alipayTradeAppPayModel.setOutTradeNo(orderNo);
            alipayTradeAppPayModel.setSubject(subject);
            alipayTradeAppPayModel.setTotalAmount(totalAmount);
            alipayTradeAppPayModel.setProductCode("QUICK_MSECURITY_PAY");

            alipayTradeAppPayRequest.setBizModel(alipayTradeAppPayModel);

            AlipayTradeAppPayResponse alipayTradeAppPayResponse = client.sdkExecute(alipayTradeAppPayRequest);
            log.info("支付宝APP支付响应参数：{}", JSONUtil.toJsonStr(alipayTradeAppPayResponse));
            if (alipayTradeAppPayResponse.isSuccess()) {
                String payUrl = alipayTradeAppPayResponse.getBody();
                Map<String, Object> paramsMap = new HashMap<>();
                paramsMap.put("payUrl", payUrl);
                paramsMap.put("payMode", "alipay");
                paramsMap.put("orderNo", orderNo);
                paramsMap.put("subject", subject);
                paramsMap.put("totalAmount", totalAmount);
                timedCache.put(orderNo, paramsMap);
                return getHostUrl() + "/common/order/getOrder?orderNo=" + orderNo;
            } else {
                throw new RuntimeException("调用支付宝APP支付失败");
            }
        } catch (Exception e) {
            log.error("调用支付宝APP支付接口异常：{}", JSONUtil.toJsonStr(e));
            throw new RuntimeException("支付宝APP支付异常", e);
        }
    }

}
