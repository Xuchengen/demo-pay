package com.github.xuchengen.web.alipay;

import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradePayModel;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.response.AlipayTradePayResponse;
import com.github.xuchengen.support.response.ResponseResult;
import com.github.xuchengen.tool.OrderNoTool;
import com.github.xuchengen.web.BaseCtl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 支付宝-条码支付
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/9/18
 */
@Controller
@RequestMapping(value = "/alipay/barcodePay")
public class AliPayBarcodePayCtl extends BaseCtl {

    public static final Log log = LogFactory.get(AliPayBarcodePayCtl.class);

    @GetMapping(value = {"", "/"})
    public String index(ModelMap modelMap) {
        modelMap.put("apiUrl", getHostUrl() + "/alipay/barcodePay/doPay");
        return "/alipay/barcodePay.html";
    }

    @PostMapping(value = "/doPay")
    @ResponseBody
    public ResponseResult doPay(String subject,
                                String amount,
                                String barcodeValue) {
        try {
            AlipayClient client = getAlipayClient();

            AlipayTradePayRequest alipayTradePayRequest = new AlipayTradePayRequest();
            alipayTradePayRequest.setNotifyUrl(getHostUrl() + "/alipay/doNotify");

            AlipayTradePayModel alipayTradePayModel = new AlipayTradePayModel();
            alipayTradePayModel.setOutTradeNo(OrderNoTool.generate());
            alipayTradePayModel.setScene("bar_code");
            alipayTradePayModel.setAuthCode(barcodeValue);
            alipayTradePayModel.setSubject(subject);
            alipayTradePayModel.setTotalAmount(amount);
            alipayTradePayModel.setProductCode("FACE_TO_FACE_PAYMENT");
            alipayTradePayRequest.setBizModel(alipayTradePayModel);

            AlipayTradePayResponse response = client.execute(alipayTradePayRequest);
            log.info("支付宝条码支付响应参数：{}", JSONUtil.toJsonStr(response));
            if (response.isSuccess()) {
                return ResponseResult.ok();
            } else {
                return ResponseResult.fail(response.getMsg());
            }
        } catch (Exception e) {
            log.error("调用支付宝条码支付接口异常：{}", JSONUtil.toJsonStr(e));
            return ResponseResult.error();
        }
    }

}
