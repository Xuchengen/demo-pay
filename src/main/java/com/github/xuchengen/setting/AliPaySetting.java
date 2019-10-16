package com.github.xuchengen.setting;

/**
 * 支付宝配置
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/9/17
 */
public class AliPaySetting {

    /**
     * 网关地址
     */
    private String gateway;

    /**
     * 商户ID
     */
    private String merchantId;

    /**
     * 商户应用ID
     */
    private String appId;

    /**
     * 报文格式
     */
    private String format;

    /**
     * 字符编码
     */
    private String charset;

    /**
     * 签名方式
     */
    private String signType;

    /**
     * 商户私钥
     */
    private String privateKey;

    /**
     * 支付宝公钥
     */
    private String publicKey;

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public AliPaySetting setMerchantId(String merchantId) {
        this.merchantId = merchantId;
        return this;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
