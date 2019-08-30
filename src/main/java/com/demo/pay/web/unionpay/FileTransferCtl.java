package com.demo.pay.web.unionpay;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.demo.pay.support.response.ResponseWrapper;
import com.demo.pay.unionpay.*;
import com.demo.pay.unionpay.internal.SecureHelper;
import com.demo.pay.unionpay.request.UnionPayFileTransferRequest;
import com.demo.pay.unionpay.response.UnionPayFileTransferResponse;
import com.demo.pay.web.BaseCtl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/8/30
 */
@Controller
@RequestMapping(value = "/unionpay/fileTransfer")
public class FileTransferCtl extends BaseCtl {

    private static final Log log = LogFactory.get(FileTransferCtl.class);

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
                    .setMerchantId("700000000000001")
                    .setSignature(unionpayV510Signture)
                    .setFileDownloadHost("https://filedownload.test.95516.com/");

            DefaultUnionPayClient client = new DefaultUnionPayClient(unionPayConfig);

            UnionPayFileTransferRequest unionPayFileTransferRequest = new UnionPayFileTransferRequest();
            unionPayFileTransferRequest.setTxnType("76");
            unionPayFileTransferRequest.setTxnSubType("01");
            unionPayFileTransferRequest.setAccessType("0");
            unionPayFileTransferRequest.setSettleDate("0119");
            unionPayFileTransferRequest.setFileType("00");

            UnionPayFileTransferResponse response = client.execute(unionPayFileTransferRequest);

            return ResponseWrapper.ok(response);
        } catch (UnionPayException e) {
            throw new RuntimeException(e);
        }
    }

}
