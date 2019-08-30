package com.demo.pay.web.unionpay;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.demo.pay.support.response.ResponseWrapper;
import com.demo.pay.unionpay.*;
import com.demo.pay.unionpay.internal.SecureHelper;
import com.demo.pay.unionpay.request.UnionPayRefundTransRequest;
import com.demo.pay.unionpay.response.UnionPayRefundTransResponse;
import com.demo.pay.utils.OrderNoUtil;
import com.demo.pay.web.BaseCtl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/8/30
 */
@Controller
@RequestMapping(value = "/unionpay/preAuthBack")
public class PreAuthBackCtl extends BaseCtl {

    private static final Log log = LogFactory.get(PreAuthBackCtl.class);

    @GetMapping(value = {"", "/"})
    @ResponseBody
    public ResponseWrapper index() {
        try {
            UnionPayCertInfo certInfo = SecureHelper.getCertInfoFromInputStream(FileUtil.getInputStream("/users/xuchengen/z_dev/files/certs/acp_test_sign.pfx"), "000000");

            UnionPayV510Signture unionpayV510Signture = new UnionPayV510Signture()
                    .setRootCertStr(SecureHelper.getCertStrFromInputStream(FileUtil.getInputStream("/users/xuchengen/z_dev/files/certs/acp_test_root.cer")))
                    .setMiddleCertStr(SecureHelper.getCertStrFromInputStream(FileUtil.getInputStream("/users/xuchengen/z_dev/files/certs/acp_test_middle.cer")))
                    .setEncryCertStr(SecureHelper.getCertStrFromInputStream(FileUtil.getInputStream("/users/xuchengen/z_dev/files/certs/acp_test_enc.cer")))
                    .setMerchantCertInfo(certInfo);

            UnionPayConfig unionPayConfig = new UnionPayConfig()
                    .setEncoding(UnionPayConstants.DEFAULT_UTF8)
                    .setHost("https://gateway.test.95516.com")
                    .setMerchantId("777290058172475")
                    .setSignature(unionpayV510Signture)
                    .setFileDownloadHost("https://filedownload.test.95516.com/");

            DefaultUnionPayClient client = new DefaultUnionPayClient(unionPayConfig);

            UnionPayRefundTransRequest unionPayRefundTransRequest = new UnionPayRefundTransRequest();
            unionPayRefundTransRequest.setOrderId(OrderNoUtil.generate());
            unionPayRefundTransRequest.setOrigQryId("601908301652404056288");
            unionPayRefundTransRequest.setTxnAmt("100");
            unionPayRefundTransRequest.setTxnType("32");
            unionPayRefundTransRequest.setTxnSubType("00");
            unionPayRefundTransRequest.setAccessType("0");
            unionPayRefundTransRequest.setChannelType("07");
            unionPayRefundTransRequest.setBackUrl(getHostUrl() + "/unionpay/preBack/notify");

            UnionPayRefundTransResponse response = client.execute(unionPayRefundTransRequest);

            return ResponseWrapper.ok(response);
        } catch (UnionPayException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(value = "/notify")
    @ResponseBody
    public String doNotify() {
        log.debug("接收银联异步回调通知：预授权撤销接口");

        UnionPayCertInfo certInfo = SecureHelper.getCertInfoFromInputStream(FileUtil.getInputStream("/users/xuchengen/z_dev/files/certs/acp_test_sign.pfx"), "000000");

        UnionPayV510Signture unionpayV510Signture = new UnionPayV510Signture()
                .setRootCertStr(SecureHelper.getCertStrFromInputStream(FileUtil.getInputStream("/users/xuchengen/z_dev/files/certs/acp_test_root.cer")))
                .setMiddleCertStr(SecureHelper.getCertStrFromInputStream(FileUtil.getInputStream("/users/xuchengen/z_dev/files/certs/acp_test_middle.cer")))
                .setEncryCertStr(SecureHelper.getCertStrFromInputStream(FileUtil.getInputStream("/users/xuchengen/z_dev/files/certs/acp_test_enc.cer")))
                .setMerchantCertInfo(certInfo);

        Map<String, String> paramMap = ServletUtil.getParamMap(getRequest());

        boolean verify = unionpayV510Signture.verify(paramMap, paramMap.get(UnionPayConstants.VAR_ENCODING));

        if (verify) {
            log.debug("接收银联异步回调通知：预授权撤销接口[验签成功]");
            return "OK";
        }

        return "验签失败";
    }
}
