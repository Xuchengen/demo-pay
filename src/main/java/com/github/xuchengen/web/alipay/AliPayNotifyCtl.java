package com.github.xuchengen.web.alipay;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alipay.api.internal.util.AlipaySignature;
import com.github.xuchengen.setting.AliPaySetting;
import com.github.xuchengen.setting.SettingTool;
import com.github.xuchengen.web.BaseCtl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 支付宝异步通知控制器
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/9/23
 */
@Controller
@RequestMapping("/alipay")
public class AliPayNotifyCtl extends BaseCtl {

    private static final Log log = LogFactory.get(AliPayNotifyCtl.class);

    /**
     * 异步通知并进行验签
     */
    @PostMapping(value = "/doNotify")
    @ResponseBody
    public String doNotify() {
        try {
            Map<String, String> paramMap = ServletUtil.getParamMap(getRequest());
            log.info("支付宝异步通知回调参数：{}", JSONUtil.toJsonStr(paramMap));

            if (CollUtil.isEmpty(paramMap)) {
                return "fail";
            }

            AliPaySetting aliPaySetting = SettingTool.getAliPaySetting();

            boolean signResult = AlipaySignature.rsaCheckV1(paramMap, aliPaySetting.getPublicKey(),
                    aliPaySetting.getCharset(), aliPaySetting.getSignType());

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
            log.error("支付宝异步通知回调处理异常：{}", e);
        }
        return "fail";
    }

}
