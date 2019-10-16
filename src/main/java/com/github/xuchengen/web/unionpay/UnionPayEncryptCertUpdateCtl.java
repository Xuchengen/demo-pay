package com.github.xuchengen.web.unionpay;

import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.github.xuchengen.DefaultUnionPayClient;
import com.github.xuchengen.request.UnionPayEncryPubKeyQueryRequest;
import com.github.xuchengen.response.UnionPayEncryPubKeyQueryResponse;
import com.github.xuchengen.support.response.ResponseResult;
import com.github.xuchengen.web.BaseCtl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 银联-在线网关支付加密证书更新
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/10/9
 */
@Controller
@RequestMapping(value = "/unionPay/onlineGatewayPay/encryptCertUpdate")
public class UnionPayEncryptCertUpdateCtl extends BaseCtl {

    public static final Log log = LogFactory.get(UnionPayEncryptCertUpdateCtl.class);

    @GetMapping(value = {"", "/"})
    public String index() {
        return "/unionpay/onlineGateway/encryptCertUpdate.html";
    }

    @PostMapping(value = "/doUpdate")
    @ResponseBody
    public ResponseResult doUpdate(String orderNo) {
        try {
            DefaultUnionPayClient client = getUnionPayClient();

            UnionPayEncryPubKeyQueryRequest request = new UnionPayEncryPubKeyQueryRequest();
            request.setOrderId(orderNo);
            request.setTxnType("95");
            request.setTxnSubType("00");
            request.setAccessType("0");
            request.setChannelType("07");
            request.setCertType("01");

            UnionPayEncryPubKeyQueryResponse response = client.execute(request);

            if (response.isOk()) {
                return ResponseResult.ok(response.getEncryptPubKeyCert());
            }
            return ResponseResult.fail();
        } catch (Exception e) {
            log.error("调用银联在线网关支付加密证书更新接口异常：{}", JSONUtil.toJsonStr(e));
            throw new RuntimeException("银联在线网关支付加密证书更新接口异常", e);
        }
    }
}
