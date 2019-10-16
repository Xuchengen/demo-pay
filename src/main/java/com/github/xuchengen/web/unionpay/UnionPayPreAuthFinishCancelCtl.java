package com.github.xuchengen.web.unionpay;

import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.github.xuchengen.DefaultUnionPayClient;
import com.github.xuchengen.request.UnionPayBackTransRequest;
import com.github.xuchengen.response.UnionPayBackTransResponse;
import com.github.xuchengen.support.response.ResponseResult;
import com.github.xuchengen.web.BaseCtl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * 银联-在线网关支付消费预授权完成撤销接口
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/10/15
 */
@Controller
@RequestMapping(value = "/unionPay/onlineGatewayPay/preAuthFinishCancel")
public class UnionPayPreAuthFinishCancelCtl extends BaseCtl {

    private static final Log log = LogFactory.get(UnionPayPreAuthFinishCancelCtl.class);

    @GetMapping(value = {"", "/"})
    public String index(ModelMap modelMap) {
        modelMap.put("notifyUrl", getHostUrl() + "/unionPay/doNotify");
        return "/unionpay/onlineGateway/preAuthFinishCancel.html";
    }

    @PostMapping(value = "/doCancel")
    @ResponseBody
    public ResponseResult doCancel(String orderNo,
                                   String origNo,
                                   BigDecimal totalAmount,
                                   String notifyUrl) {
        try {
            DefaultUnionPayClient client = getUnionPayClient();

            UnionPayBackTransRequest unionPayBackTransRequest = new UnionPayBackTransRequest();
            unionPayBackTransRequest.setTxnAmt(totalAmount.multiply(new BigDecimal("100")).toString());
            unionPayBackTransRequest.setTxnType("33");
            unionPayBackTransRequest.setTxnSubType("00");
            unionPayBackTransRequest.setChannelType("07");
            unionPayBackTransRequest.setAccessType("0");
            unionPayBackTransRequest.setOrderId(orderNo);
            unionPayBackTransRequest.setOrigQryId(origNo);
            unionPayBackTransRequest.setBackUrl(notifyUrl);

            UnionPayBackTransResponse response = client.execute(unionPayBackTransRequest);

            if (response.isOk()) {
                return ResponseResult.ok();
            }
            return ResponseResult.fail();
        } catch (Exception e) {
            log.error("调用银联在线网关支付预授权完成撤销接口异常：{}", JSONUtil.toJsonStr(e));
            return ResponseResult.error();
        }
    }

}
