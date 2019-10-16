package com.github.xuchengen.web.alipay;

import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeCloseModel;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.github.xuchengen.support.response.ResponseResult;
import com.github.xuchengen.web.BaseCtl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 支付宝-交易关闭
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/9/24
 */
@Controller
@RequestMapping(value = "/alipay/close")
public class AliPayCloseCtl extends BaseCtl {

    private static final Log log = LogFactory.get(AliPayCloseCtl.class);

    @GetMapping(value = {"", "/"})
    public String index(ModelMap modelMap) {
        modelMap.put("notifyUrl", getHostUrl() + "/alipay/doNotify");
        return "/alipay/close.html";
    }

    @PostMapping(value = "/doClose")
    @ResponseBody
    public ResponseResult doClose(String tradeNo,
                                  String outTradeNo,
                                  String notifyUrl) {
        try {
            AlipayClient client = getAlipayClient();

            AlipayTradeCloseRequest alipayTradeCloseRequest = new AlipayTradeCloseRequest();
            alipayTradeCloseRequest.setNotifyUrl(notifyUrl);

            AlipayTradeCloseModel alipayTradeCloseModel = new AlipayTradeCloseModel();
            alipayTradeCloseModel.setOutTradeNo(outTradeNo);
            alipayTradeCloseModel.setTradeNo(tradeNo);

            alipayTradeCloseRequest.setBizModel(alipayTradeCloseModel);

            AlipayTradeCloseResponse response = client.execute(alipayTradeCloseRequest);

            log.info("支付宝交易关闭接口响应参数：{}", JSONUtil.toJsonStr(response));

            if (response.isSuccess()) {
                return ResponseResult.ok();
            } else {
                return ResponseResult.fail(response.getSubMsg());
            }

        } catch (Exception e) {
            log.error("调用支付宝交易关闭接口异常：{}", JSONUtil.toJsonStr(e));
            throw new RuntimeException("支付宝交易关闭异常", e);
        }
    }

}
