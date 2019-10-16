package com.github.xuchengen.web;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.github.xuchengen.*;
import com.github.xuchengen.setting.*;
import com.github.xuchengen.tool.WebTool;

/**
 * 控制器基类
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/9/11
 */
public class BaseCtl extends WebTool {

    /**
     * 获取银联客户端
     *
     * @return DefaultUnionPayClient
     */
    protected DefaultUnionPayClient getUnionPayClient() {
        UnionPaySetting unionPaySetting = SettingTool.getUnionPaySetting();

        Signer signer = null;

        if (UnionPayConstants.V510_VERSION.equals(unionPaySetting.getCertVersion())) {
            UnionPayV510Config unionPayV510Config = unionPaySetting.getUnionPayV510Config();
            signer = new V510Signer()
                    .setEncryCertStr(unionPaySetting.getEncryptKey())
                    .setMiddleCertStr(unionPayV510Config.getMiddelKey())
                    .setRootCertStr(unionPayV510Config.getRootKey())
                    .setMerchantPrivateKey(unionPayV510Config.getMerchantPrivateKey())
                    .setSerialNo(unionPayV510Config.getCertId());
        } else if (UnionPayConstants.V500_VERSION.equals(unionPaySetting.getCertVersion())) {
            UnionPayV500Config unionPayV500Config = unionPaySetting.getUnionPayV500Config();
            signer = new V500Signer()
                    .setMerchantPrivateKey(unionPayV500Config.getMerchantPrivateKey())
                    .setSerialNo(unionPayV500Config.getCertId())
                    .setUnionPayPublicKey(unionPayV500Config.getUnionPayPublicKey());
        }

        UnionPayConfig unionPayConfig = new UnionPayConfig()
                .setApiHost(unionPaySetting.getApiHost())
                .setFileDownloadHost(unionPaySetting.getFileDownloadUrl())
                .setEncoding(unionPaySetting.getCharset())
                .setMerchantId(unionPaySetting.getMerchantId())
                .setSigner(signer);

        return new DefaultUnionPayClient(unionPayConfig);
    }

    /**
     * 获取支付宝客户端
     *
     * @return AlipayClient
     */
    public AlipayClient getAlipayClient() {
        AliPaySetting aliPaySetting = SettingTool.getAliPaySetting();

        DefaultAlipayClient client = DefaultAlipayClient.builder(aliPaySetting.getGateway(), aliPaySetting.getAppId(), aliPaySetting.getPrivateKey())
                .alipayPublicKey(aliPaySetting.getPublicKey())
                .charset(aliPaySetting.getCharset())
                .format(aliPaySetting.getFormat())
                .signType(aliPaySetting.getSignType())
                .build();
        return client;
    }
}
