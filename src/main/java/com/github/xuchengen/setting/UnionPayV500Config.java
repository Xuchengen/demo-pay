package com.github.xuchengen.setting;

/**
 * 银联全渠道V5.0.0配置
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/9/26
 */
public class UnionPayV500Config {

    /**
     * 商户公钥证书
     */
    private String merchantPublicKey;

    /**
     * 商户私钥证书
     */
    private String merchantPrivateKey;

    /**
     * 证书ID
     */
    private String certId;

    /**
     * 银联公钥证书（签名使用）
     */
    private String unionPayPublicKey;

    public String getMerchantPublicKey() {
        return merchantPublicKey;
    }

    public UnionPayV500Config setMerchantPublicKey(String merchantPublicKey) {
        this.merchantPublicKey = merchantPublicKey;
        return this;
    }

    public String getMerchantPrivateKey() {
        return merchantPrivateKey;
    }

    public UnionPayV500Config setMerchantPrivateKey(String merchantPrivateKey) {
        this.merchantPrivateKey = merchantPrivateKey;
        return this;
    }

    public String getCertId() {
        return certId;
    }

    public UnionPayV500Config setCertId(String certId) {
        this.certId = certId;
        return this;
    }

    public String getUnionPayPublicKey() {
        return unionPayPublicKey;
    }

    public UnionPayV500Config setUnionPayPublicKey(String unionPayPublicKey) {
        this.unionPayPublicKey = unionPayPublicKey;
        return this;
    }
}
