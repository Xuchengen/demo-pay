package com.demo.pay.web.alipay;

import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.demo.pay.web.BaseCtl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 支付宝-电脑网站支付
 */
@Controller
@RequestMapping(value = "/alipay/pagePay")
public class AlipayPagePayCtl extends BaseCtl {

    private static final Log log = LogFactory.get(AlipayPagePayCtl.class);

    private static final String APP_ID_CACHE = "alipay:page_pay:app_id";
    private static final String CHARSET_CACHE = "alipay:page_pay:charset";
    private static final String SIGN_TYPE_CACHE = "alipay:page_pay:sign_type";
    private static final String MERCHANT_PRIVATE_KEY_CACHE = "alipay:page_pay:merchant_private_key";
    private static final String ALIPAY_PUBLIC_KEY_CACHE = "alipay:page_pay:alipay_public_key";

    @Resource
    private TimedCache<String, String> timedCache;

    /**
     * 页面
     */
    @GetMapping(value = {"", "/"})
    public String page(ModelMap modelMap) {
        modelMap.put("returnUrl", getHostUrl() + "/alipay/pagePay");
        modelMap.put("notifyUrl", getHostUrl() + "/alipay/pagePay/notify");
        return "alipay/pagePay.html";
    }

    /**
     * 发起支付动作
     */
    @PostMapping(value = "/doPay")
    @ResponseBody
    public String doPay(String server_url,
                        String app_id,
                        String merchant_private_key,
                        String alipay_public_key,
                        String charset,
                        String format,
                        String sign_type,
                        String version,
                        String notify_url,
                        String return_url,
                        String out_trade_no,
                        String subject,
                        String total_amount) {
        try {
            //会话中暂存
            HttpSession session = getSession();
            session.setAttribute("app_id", app_id);
            session.setAttribute("merchant_private_key", merchant_private_key);
            session.setAttribute("alipay_public_key", alipay_public_key);

            //缓存中暂存提供回调通知验签
            timedCache.put(APP_ID_CACHE, app_id);
            timedCache.put(MERCHANT_PRIVATE_KEY_CACHE, merchant_private_key);
            timedCache.put(ALIPAY_PUBLIC_KEY_CACHE, alipay_public_key);
            timedCache.put(CHARSET_CACHE, charset);
            timedCache.put(SIGN_TYPE_CACHE, sign_type);

            DefaultAlipayClient client = DefaultAlipayClient.builder(server_url, app_id, merchant_private_key)
                    .alipayPublicKey(alipay_public_key)
                    .charset(charset)
                    .format(format)
                    .signType(sign_type)
                    .build();

            AlipayTradePagePayRequest alipayTradePagePayRequest = new AlipayTradePagePayRequest();
            alipayTradePagePayRequest.setApiVersion(version);
            alipayTradePagePayRequest.setNotifyUrl(notify_url);
            alipayTradePagePayRequest.setReturnUrl(return_url);

            AlipayTradePagePayModel alipayTradePagePayModel = new AlipayTradePagePayModel();
            alipayTradePagePayModel.setOutTradeNo(out_trade_no);
            alipayTradePagePayModel.setSubject(subject);
            alipayTradePagePayModel.setTotalAmount(total_amount);
            alipayTradePagePayModel.setProductCode("FAST_INSTANT_TRADE_PAY");

            alipayTradePagePayRequest.setBizModel(alipayTradePagePayModel);

            AlipayTradePagePayResponse alipayTradePagePayResponse = client.pageExecute(alipayTradePagePayRequest);

            if (alipayTradePagePayResponse.isSuccess()) {
                return alipayTradePagePayResponse.getBody();
            } else {
                log.warn("调用支付宝电脑网站支付接口失败：{}", JSONUtil.toJsonStr(alipayTradePagePayResponse));
                return REDIRECT + "/alipay/pagePay";
            }
        } catch (Exception e) {
            log.error("调用支付宝电脑网站支付接口异常：{}", JSONUtil.toJsonStr(e));
            throw new RuntimeException(e);
        }
    }

    /**
     * 异步通知并进行验签
     */
    @PostMapping(value = "/notify")
    @ResponseBody
    public String doNotify() {
        try {
            Map<String, String> paramMap = ServletUtil.getParamMap(getRequest());
            log.info("支付宝电脑网站支付接口回调参数：{}", JSONUtil.toJsonStr(paramMap));

            if (CollUtil.isEmpty(paramMap)) {
                return "fail";
            }

            boolean signResult = AlipaySignature.rsaCheckV1(paramMap, timedCache.get(ALIPAY_PUBLIC_KEY_CACHE),
                    timedCache.get(CHARSET_CACHE), timedCache.get(SIGN_TYPE_CACHE));

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
            log.error("支付宝电脑网站支付接口回调处理异常：{}", e);
        }
        return "fail";
    }

}
