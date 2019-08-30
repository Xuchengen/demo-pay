package com.demo.pay.web.unionpay;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.demo.pay.unionpay.*;
import com.demo.pay.unionpay.internal.SecureHelper;
import com.demo.pay.unionpay.request.UnionPayFrontTransRequest;
import com.demo.pay.unionpay.response.UnionPayFrontTransResponse;
import com.demo.pay.utils.OrderNoUtil;
import com.demo.pay.web.BaseCtl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Map;

/**
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/8/28
 */
@Controller
@RequestMapping(value = "/unionpay/frontTrans")
public class FrontTransCtl extends BaseCtl {

    private static final Log log = LogFactory.get(FrontTransCtl.class);

    @GetMapping(value = {"", "/"})
    @ResponseBody
    public String index() {
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

            UnionPayFrontTransRequest unionpayFrontTransRequest = new UnionPayFrontTransRequest();
            unionpayFrontTransRequest.setTxnAmt("100");
            unionpayFrontTransRequest.setTxnType("01");
            unionpayFrontTransRequest.setTxnSubType("01");
            unionpayFrontTransRequest.setChannelType("07");
            unionpayFrontTransRequest.setAccessType("0");
            unionpayFrontTransRequest.setOrderId(OrderNoUtil.generate());
            unionpayFrontTransRequest.setCurrencyCode("156");
            unionpayFrontTransRequest.setFrontUrl(getHostUrl() + "/unionpay/frontTrans/return");
            unionpayFrontTransRequest.setBackUrl(getHostUrl() + "/unionpay/frontTrans/notify");
            unionpayFrontTransRequest.setPayTimeout(DateUtil.format(DateUtil.offsetMinute(new Date(), 15), UnionPayConstants.DEFAULT_DATE_TIME_FORMAT));
            unionpayFrontTransRequest.setRiskRateInfo("测试商品名称");

            UnionPayFrontTransResponse unionpayFrontTransResponse = client.pageExecute(unionpayFrontTransRequest);

            return unionpayFrontTransResponse.getBody();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(value = "/return")
    @ResponseBody
    public String syncNotify() {
        log.debug("接收银联同步回调通知：消费接口");
        UnionPayCertInfo certInfo = SecureHelper.getCertInfoFromInputStream(FileUtil.getInputStream("/users/xuchengen/z_dev/files/certs/acp_test_sign.pfx"), "000000");

        UnionPayV510Signture unionpayV510Signture = new UnionPayV510Signture()
                .setRootCertStr(SecureHelper.getCertStrFromInputStream(FileUtil.getInputStream("/users/xuchengen/z_dev/files/certs/acp_test_root.cer")))
                .setMiddleCertStr(SecureHelper.getCertStrFromInputStream(FileUtil.getInputStream("/users/xuchengen/z_dev/files/certs/acp_test_middle.cer")))
                .setEncryCertStr(SecureHelper.getCertStrFromInputStream(FileUtil.getInputStream("/users/xuchengen/z_dev/files/certs/acp_test_enc.cer")))
                .setMerchantCertInfo(certInfo);

        Map<String, String> paramMap = ServletUtil.getParamMap(getRequest());

        boolean verify = unionpayV510Signture.verify(paramMap, paramMap.get(UnionPayConstants.VAR_ENCODING));

        if (verify) {
            log.debug("接收银联同步回调通知：消费接口[验签成功]");
            return "OK";
        }

        return "验签失败";
    }

    @PostMapping(value = "/notify")
    @ResponseBody
    public String asyncNotify() {

        log.debug("接收银联异步回调通知：消费接口");

        UnionPayCertInfo certInfo = SecureHelper.getCertInfoFromInputStream(FileUtil.getInputStream("/users/xuchengen/z_dev/files/certs/acp_test_sign.pfx"), "000000");

        UnionPayV510Signture unionpayV510Signture = new UnionPayV510Signture()
                .setRootCertStr(SecureHelper.getCertStrFromInputStream(FileUtil.getInputStream("/users/xuchengen/z_dev/files/certs/acp_test_root.cer")))
                .setMiddleCertStr(SecureHelper.getCertStrFromInputStream(FileUtil.getInputStream("/users/xuchengen/z_dev/files/certs/acp_test_middle.cer")))
                .setEncryCertStr(SecureHelper.getCertStrFromInputStream(FileUtil.getInputStream("/users/xuchengen/z_dev/files/certs/acp_test_enc.cer")))
                .setMerchantCertInfo(certInfo);

        Map<String, String> paramMap = ServletUtil.getParamMap(getRequest());

        boolean verify = unionpayV510Signture.verify(paramMap, paramMap.get(UnionPayConstants.VAR_ENCODING));

        if (verify) {
            log.debug("接收银联异步回调通知：消费接口[验签成功]");
            return "OK";
        }

        return "验签失败";
    }
}
