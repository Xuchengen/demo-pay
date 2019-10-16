package com.github.xuchengen.web.unionpay;

import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.github.xuchengen.DefaultUnionPayClient;
import com.github.xuchengen.request.UnionPayQueryTransRequest;
import com.github.xuchengen.response.UnionPayQueryTransResponse;
import com.github.xuchengen.support.response.ResponseResult;
import com.github.xuchengen.web.BaseCtl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 银联-在线网关支付交易查询
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/10/8
 */
@Controller
@RequestMapping(value = "/unionPay/onlineGatewayPay/query")
public class UnionPayQueryCtl extends BaseCtl {

    public static final Log log = LogFactory.get(UnionPayQueryCtl.class);

    @GetMapping(value = {"", "/"})
    public String index() {
        return "/unionpay/onlineGateway/query.html";
    }

    @PostMapping(value = "/doQuery")
    @ResponseBody
    public ResponseResult doQuery(String orderNo) {
        try {
            DefaultUnionPayClient client = getUnionPayClient();

            UnionPayQueryTransRequest request = new UnionPayQueryTransRequest();
            request.setOrderId(orderNo);
            request.setTxnType("00");
            request.setTxnSubType("00");
            request.setAccessType("0");

            UnionPayQueryTransResponse response = client.execute(request);

            if (response.isOk()) {
                return ResponseResult.ok(JSONUtil.formatJsonStr(JSONUtil.toJsonStr(response)));
            }
            return ResponseResult.fail();
        } catch (Exception e) {
            log.error("调用银联在线网关支付交易查询接口异常：{}", JSONUtil.toJsonStr(e));
            throw new RuntimeException("银联在线网关支付交易查询接口异常", e);
        }
    }
}
