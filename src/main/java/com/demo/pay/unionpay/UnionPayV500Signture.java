package com.demo.pay.unionpay;

import com.demo.pay.unionpay.internal.SecureHelper;

import java.util.Map;

/**
 * 银联V5.0.0签名
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/8/28
 */
public class UnionPayV500Signture implements UnionPaySignature {

    /**
     * 版本
     */
    private static final String version = UnionPayConstants.V500_VERSION;

    /**
     * 签名方法
     */
    private static final String signMethod = UnionPayConstants.SIGN_METHOD_RSA;

    /**
     * 商户证书信息
     */
    private UnionPayCertInfo merchantCertInfo;

    public UnionPayCertInfo getMerchantCertInfo() {
        return merchantCertInfo;
    }

    public UnionPayV500Signture setMerchantCertInfo(UnionPayCertInfo merchantCertInfo) {
        this.merchantCertInfo = merchantCertInfo;
        return this;
    }

    @Override
    public String digest(String paramStr, String charset) {
        return SecureHelper.getSHA1Digest(paramStr, charset);
    }

    @Override
    public String sign(String paramStr) {
        return SecureHelper.signBySHA1withRSA(paramStr, merchantCertInfo.getPrivateKeyStr());
    }

    @Override
    public boolean verify(Map<String, String> paramMap, String charset) {
        return false;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getCertId() {
        return merchantCertInfo.getSerialNo();
    }

    @Override
    public String getSignMethod() {
        return signMethod;
    }
}
