package com.github.xuchengen.web.alipay;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.Setting;
import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePayModel;
import com.alipay.api.internal.util.AlipaySignature;
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

import java.io.File;
import java.util.Map;

/**
 * 支付宝-条码支付
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/9/18
 */
@Controller
@RequestMapping(value = "/alipay/barcodePay")
public class BarcodePayCtl extends BaseCtl {

    public static final Log log = LogFactory.get(BarcodePayCtl.class);

    private static final String userDir = System.getProperty("user.home");

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
            File file = new File(userDir + File.separator + ".demo-pay" + File.separator + "alipay.ini");

            if (!file.exists()) {
                return ResponseResult.error("支付宝配置文件不存在");
            }

            Setting setting = new Setting(file.getAbsolutePath());

            Map<String, String> alipay = setting.getMap("alipay");

            DefaultAlipayClient client = DefaultAlipayClient.builder(alipay.get("gateway"), alipay.get("appId"), alipay.get("privateKey"))
                    .alipayPublicKey(alipay.get("publicKey"))
                    .charset(CharsetUtil.UTF_8)
                    .format("JSON")
                    .signType("RSA2")
                    .build();

            AlipayTradePayRequest alipayTradePayRequest = new AlipayTradePayRequest();
            alipayTradePayRequest.setNotifyUrl(getHostUrl() + "/alipay/barcodePay/doNotify");

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
        } catch (AlipayApiException e) {
            log.error("调用支付宝条码支付接口异常：{}", JSONUtil.toJsonStr(e));
            return ResponseResult.error();
        }
    }

    /**
     * 异步通知并进行验签
     */
    @PostMapping(value = "/doNotify")
    @ResponseBody
    public String doNotify() {
        try {
            Map<String, String> paramMap = ServletUtil.getParamMap(getRequest());
            log.info("支付宝条码支付接口回调参数：{}", JSONUtil.toJsonStr(paramMap));

            if (CollUtil.isEmpty(paramMap)) {
                return "fail";
            }

            File file = new File(userDir + File.separator + ".demo-pay" + File.separator + "alipay.ini");

            if (!file.exists()) {
                throw new RuntimeException("支付宝配置文件不存在");
            }

            Setting setting = new Setting(file.getAbsolutePath());

            Map<String, String> alipay = setting.getMap("alipay");

            boolean signResult = AlipaySignature.rsaCheckV1(paramMap, alipay.get("publicKey"),
                    CharsetUtil.UTF_8, "RSA2");

            if (signResult) {
                log.info("验签通过");
                //商户订单号
                String out_trade_no = paramMap.get("out_trade_no");

                //支付宝交易号
                String trade_no = paramMap.get("trade_no");

                //交易状态
                String trade_status = paramMap.get("trade_status");

                if (trade_status.equals("TRADE_FINISHED")) {
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序

                    //注意：
                    //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                } else if (trade_status.equals("TRADE_SUCCESS")) {
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序

                    //注意：
                    //付款完成后，支付宝系统发送该交易状态通知
                }
                return "success";
            } else {
                String sWord = AlipaySignature.getSignCheckContentV2(paramMap);
                log.info("验签失败：签名前构建验签字符串为：{}", sWord);
            }
        } catch (Exception e) {
            log.error("支付宝条码支付接口回调处理异常：{}", e);
        }
        return "fail";
    }
}
