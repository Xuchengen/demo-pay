package com.github.xuchengen.web.unionpay;

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.github.xuchengen.DefaultUnionPayClient;
import com.github.xuchengen.UnionPayConstants;
import com.github.xuchengen.request.UnionPayFrontTransRequest;
import com.github.xuchengen.response.UnionPayFrontTransResponse;
import com.github.xuchengen.web.BaseCtl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 银联-在线网关支付预授权接口
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/10/15
 */
@Controller
@RequestMapping(value = "/unionPay/onlineGatewayPay/preAuth")
public class UnionPayPreAuthCtl extends BaseCtl {

    private static final Log log = LogFactory.get(UnionPayPreAuthCtl.class);

    @GetMapping(value = {"", "/"})
    public String index(ModelMap modelMap) {
        modelMap.put("returnUrl", getHostUrl() + "/unionPay/onlineGatewayPay/preAuth/doReturn");
        modelMap.put("notifyUrl", getHostUrl() + "/unionPay/doNotify");
        return "/unionpay/onlineGateway/preAuth.html";
    }

    @PostMapping(value = "/doPay")
    @ResponseBody
    public String doPay(String orderNo,
                        BigDecimal totalAmount,
                        String returnUrl,
                        String notifyUrl) {
        try {
            DefaultUnionPayClient client = getUnionPayClient();

            UnionPayFrontTransRequest unionpayFrontTransRequest = new UnionPayFrontTransRequest();
            unionpayFrontTransRequest.setTxnAmt(totalAmount.multiply(new BigDecimal("100")).toString());
            unionpayFrontTransRequest.setTxnType("02");
            unionpayFrontTransRequest.setTxnSubType("01");
            unionpayFrontTransRequest.setChannelType("07");
            unionpayFrontTransRequest.setAccessType("0");
            unionpayFrontTransRequest.setOrderId(orderNo);
            unionpayFrontTransRequest.setCurrencyCode("156");
            unionpayFrontTransRequest.setFrontUrl(returnUrl);
            unionpayFrontTransRequest.setBackUrl(notifyUrl);
            unionpayFrontTransRequest.setPayTimeout(DateUtil.format(DateUtil.offsetMinute(new Date(), 15), UnionPayConstants.DEFAULT_DATE_TIME_FORMAT));
            unionpayFrontTransRequest.setRiskRateInfo("测试商品名称");

            UnionPayFrontTransResponse unionpayFrontTransResponse = client.pageExecute(unionpayFrontTransRequest);

            return unionpayFrontTransResponse.getBody();
        } catch (Exception e) {
            log.error("调用银联在线网关支付预授权接口异常：{}", JSONUtil.toJsonStr(e));
            throw new RuntimeException("银联在线网关支付预授权接口异常", e);
        }
    }

    @PostMapping(value = "/doReturn")
    public String doReturn() {
        log.debug("接收银联同步回调通知：预授权接口");
        DefaultUnionPayClient client = getUnionPayClient();

        Map<String, String> paramMap = ServletUtil.getParamMap(getRequest());

        boolean verify = client.getConfig().getSigner().verify(paramMap, paramMap.get(UnionPayConstants.VAR_ENCODING));

        if (verify) {
            log.debug("接收银联同步回调通知：预授权接口[验签成功]");
        } else {
            log.debug("接收银联同步回调通知：预授权接口[验签失败]");
        }

        return "redirect:/unionPay/onlineGatewayPay/preAuth";
    }

}
