package com.github.xuchengen.setting;

/**
 * 银联支付全渠道配置
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/9/26
 */
public class UnionPaySetting {

    /**
     * 证书版本
     */
    private String certVersion;

    /**
     * 环境
     */
    private String env;

    /**
     * 敏感加密证书
     */
    private String encryptKey;

    /**
     * 商户ID
     */
    private String merchantId;

    /**
     * 字符编码
     */
    private String charset;

    /**
     * 接口主机地址
     */
    private String apiHost;

    /**
     * 文件下载地址
     */
    private String fileDownloadUrl;

    /**
     * 银联全渠道V5.0.0版本
     */
    private UnionPayV500Config unionPayV500Config;

    /**
     * 银联全渠道V5.1.0版本
     */
    private UnionPayV510Config unionPayV510Config;

    public String getCertVersion() {
        return certVersion;
    }

    public UnionPaySetting setCertVersion(String certVersion) {
        this.certVersion = certVersion;
        return this;
    }

    public String getEnv() {
        return env;
    }

    public UnionPaySetting setEnv(String env) {
        this.env = env;
        return this;
    }

    public String getEncryptKey() {
        return encryptKey;
    }

    public UnionPaySetting setEncryptKey(String encryptKey) {
        this.encryptKey = encryptKey;
        return this;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public UnionPaySetting setMerchantId(String merchantId) {
        this.merchantId = merchantId;
        return this;
    }

    public String getCharset() {
        return charset;
    }

    public UnionPaySetting setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    public String getApiHost() {
        return apiHost;
    }

    public UnionPaySetting setApiHost(String apiHost) {
        this.apiHost = apiHost;
        return this;
    }

    public String getFileDownloadUrl() {
        return fileDownloadUrl;
    }

    public UnionPaySetting setFileDownloadUrl(String fileDownloadUrl) {
        this.fileDownloadUrl = fileDownloadUrl;
        return this;
    }

    public UnionPayV500Config getUnionPayV500Config() {
        return unionPayV500Config;
    }

    public UnionPaySetting setUnionPayV500Config(UnionPayV500Config unionPayV500Config) {
        this.unionPayV500Config = unionPayV500Config;
        return this;
    }

    public UnionPayV510Config getUnionPayV510Config() {
        return unionPayV510Config;
    }

    public UnionPaySetting setUnionPayV510Config(UnionPayV510Config unionPayV510Config) {
        this.unionPayV510Config = unionPayV510Config;
        return this;
    }
}
