package com.demo.pay.web.alipay;

import cn.hutool.cache.impl.TimedCache;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePayModel;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.response.AlipayTradePayResponse;
import com.demo.pay.support.response.ResponseWrapper;
import com.demo.pay.utils.OrderNoUtil;
import com.demo.pay.web.BaseCtl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 条码支付
 */
@Controller
@RequestMapping(value = "/alipay/barcodePay")
public class AlipayBarcodePayCtl extends BaseCtl {

    private static final Log log = LogFactory.get(AlipayBarcodePayCtl.class);

    private static final String SERVER_URL_CACHE = "alipay:bar_code_pay:server_url";
    private static final String APP_ID_CACHE = "alipay:bar_code_pay:app_id";
    private static final String CHARSET_CACHE = "alipay:bar_code_pay:charset";
    private static final String FORMAT_CACHE = "alipay:bar_code_pay:format";
    private static final String SIGN_TYPE_CACHE = "alipay:bar_code_pay:sign_type";
    private static final String MERCHANT_PRIVATE_KEY_CACHE = "alipay:bar_code_pay:merchant_private_key";
    private static final String ALIPAY_PUBLIC_KEY_CACHE = "alipay:bar_code_pay:alipay_public_key";
    private static final String VERSION_CACHE = "alipay:bar_code_pay:version";

    @Resource
    private TimedCache<String, String> timedCache;

    @GetMapping(value = {"", "/"})
    public String page(ModelMap modelMap) {
        modelMap.put("notifyUrl", getHostUrl() + "/alipay/barcodePay/notify");
        modelMap.put("apiUrl", getHostUrl() + "/alipay/barcodePay/doPay");
        return "alipay/barcodePay.html";
    }

    /**
     * 保存表单信息
     */
    @PostMapping(value = "/save")
    public String doPay(String server_url,
                        String app_id,
                        String merchant_private_key,
                        String alipay_public_key,
                        String charset,
                        String format,
                        String sign_type,
                        String version) {
        //会话中暂存
        HttpSession session = getSession();
        session.setAttribute("app_id", app_id);
        session.setAttribute("merchant_private_key", merchant_private_key);
        session.setAttribute("alipay_public_key", alipay_public_key);

        //缓存中暂存
        timedCache.put(SERVER_URL_CACHE, server_url);
        timedCache.put(APP_ID_CACHE, app_id);
        timedCache.put(MERCHANT_PRIVATE_KEY_CACHE, merchant_private_key);
        timedCache.put(ALIPAY_PUBLIC_KEY_CACHE, alipay_public_key);
        timedCache.put(CHARSET_CACHE, charset);
        timedCache.put(FORMAT_CACHE, format);
        timedCache.put(SIGN_TYPE_CACHE, sign_type);
        timedCache.put(VERSION_CACHE, version);
        return REDIRECT + "/alipay/barcodePay";
    }

    @PostMapping(value = "/doPay")
    @ResponseBody
    public ResponseWrapper doPay(String subject,
                                 String amount,
                                 String barcodeValue) {
        try {
            DefaultAlipayClient client = DefaultAlipayClient.builder(timedCache.get(SERVER_URL_CACHE),
                    timedCache.get(APP_ID_CACHE), timedCache.get(MERCHANT_PRIVATE_KEY_CACHE))
                    .alipayPublicKey(timedCache.get(ALIPAY_PUBLIC_KEY_CACHE))
                    .charset(timedCache.get(CHARSET_CACHE))
                    .format(timedCache.get(FORMAT_CACHE))
                    .signType(timedCache.get(SIGN_TYPE_CACHE))
                    .build();

            AlipayTradePayRequest alipayTradePayRequest = new AlipayTradePayRequest();
            alipayTradePayRequest.setApiVersion(timedCache.get(VERSION_CACHE));

            AlipayTradePayModel alipayTradePayModel = new AlipayTradePayModel();
            alipayTradePayModel.setOutTradeNo(OrderNoUtil.generate());
            alipayTradePayModel.setScene("bar_code");
            alipayTradePayModel.setAuthCode(barcodeValue);
            alipayTradePayModel.setSubject(subject);
            alipayTradePayModel.setTotalAmount(amount);
            alipayTradePayModel.setProductCode("FACE_TO_FACE_PAYMENT");
            alipayTradePayRequest.setBizModel(alipayTradePayModel);

            AlipayTradePayResponse response = client.execute(alipayTradePayRequest);
            log.info("支付宝条码支付响应参数：{}", JSONUtil.toJsonStr(response));
            if (response.isSuccess()) {
                return ResponseWrapper.ok();
            } else {
                return ResponseWrapper.fail().setData(response.getMsg());
            }
        } catch (AlipayApiException e) {
            log.error("调用支付宝条码支付接口异常：{}", JSONUtil.toJsonStr(e));
            return ResponseWrapper.error();
        }
    }
}
