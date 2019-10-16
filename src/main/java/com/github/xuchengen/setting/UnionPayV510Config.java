package com.github.xuchengen.setting;

/**
 * 银联全渠道V5.1.0配置
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/9/26
 */
public class UnionPayV510Config {

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
     * 根证书
     */
    private String rootKey;

    /**
     * 中级证书
     */
    private String middelKey;

    public String getMerchantPublicKey() {
        return merchantPublicKey;
    }

    public UnionPayV510Config setMerchantPublicKey(String merchantPublicKey) {
        this.merchantPublicKey = merchantPublicKey;
        return this;
    }

    public String getMerchantPrivateKey() {
        return merchantPrivateKey;
    }

    public UnionPayV510Config setMerchantPrivateKey(String merchantPrivateKey) {
        this.merchantPrivateKey = merchantPrivateKey;
        return this;
    }

    public String getCertId() {
        return certId;
    }

    public UnionPayV510Config setCertId(String certId) {
        this.certId = certId;
        return this;
    }

    public String getRootKey() {
        return rootKey;
    }

    public UnionPayV510Config setRootKey(String rootKey) {
        this.rootKey = rootKey;
        return this;
    }

    public String getMiddelKey() {
        return middelKey;
    }

    public UnionPayV510Config setMiddelKey(String middelKey) {
        this.middelKey = middelKey;
        return this;
    }
}
