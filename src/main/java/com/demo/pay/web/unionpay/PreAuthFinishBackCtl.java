package com.demo.pay.web.unionpay;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.demo.pay.support.response.ResponseWrapper;
import com.demo.pay.unionpay.*;
import com.demo.pay.unionpay.internal.SecureHelper;
import com.demo.pay.unionpay.request.UnionPayPreAuthFinishRequest;
import com.demo.pay.unionpay.response.UnionPayPreAuthFinishResponse;
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
 * 日期：2019/8/28
 */
@Controller
@RequestMapping(value = "/unionpay/preAuthFinishBack")
public class PreAuthFinishBackCtl extends BaseCtl {

    private static final Log log = LogFactory.get(PreAuthFinishBackCtl.class);

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

            UnionPayPreAuthFinishRequest unionPayPreAuthFinishRequest = new UnionPayPreAuthFinishRequest();
            unionPayPreAuthFinishRequest.setOrderId("20190830172202829688");
            unionPayPreAuthFinishRequest.setOrigQryId("731908301724394158788");
            unionPayPreAuthFinishRequest.setTxnAmt("100");
            unionPayPreAuthFinishRequest.setTxnType("33");
            unionPayPreAuthFinishRequest.setTxnSubType("00");
            unionPayPreAuthFinishRequest.setChannelType("07");
            unionPayPreAuthFinishRequest.setAccessType("0");
            unionPayPreAuthFinishRequest.setOrderId(OrderNoUtil.generate());
            unionPayPreAuthFinishRequest.setBackUrl(getHostUrl() + "/unionpay/preAuthFinishBack/notify");

            UnionPayPreAuthFinishResponse response = client.execute(unionPayPreAuthFinishRequest);

            return ResponseWrapper.ok(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(value = "/notify")
    @ResponseBody
    public String asyncNotify() {

        log.debug("接收银联异步回调通知：预授权完成撤销接口");

        UnionPayCertInfo certInfo = SecureHelper.getCertInfoFromInputStream(FileUtil.getInputStream("/users/xuchengen/z_dev/files/certs/acp_test_sign.pfx"), "000000");

        UnionPayV510Signture unionpayV510Signture = new UnionPayV510Signture()
                .setRootCertStr(SecureHelper.getCertStrFromInputStream(FileUtil.getInputStream("/users/xuchengen/z_dev/files/certs/acp_test_root.cer")))
                .setMiddleCertStr(SecureHelper.getCertStrFromInputStream(FileUtil.getInputStream("/users/xuchengen/z_dev/files/certs/acp_test_middle.cer")))
                .setEncryCertStr(SecureHelper.getCertStrFromInputStream(FileUtil.getInputStream("/users/xuchengen/z_dev/files/certs/acp_test_enc.cer")))
                .setMerchantCertInfo(certInfo);

        Map<String, String> paramMap = ServletUtil.getParamMap(getRequest());

        boolean verify = unionpayV510Signture.verify(paramMap, paramMap.get(UnionPayConstants.VAR_ENCODING));

        if (verify) {
            log.debug("接收银联异步回调通知：预授权完成撤销接口[验签成功]");
            return "OK";
        }

        return "验签失败";
    }
}
