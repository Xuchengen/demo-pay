package com.demo.pay.unionpay;

/**
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/8/30
 */
public class UnionPayConfig {

    /**
     * 主机地址
     */
    private String host;

    /**
     * 对账文件下载地址
     */
    private String fileDownloadHost;

    /**
     * 商户号
     */
    private String merchantId;

    /**
     * 字符编码
     */
    private String encoding;

    /**
     * 签名
     */
    private UnionPaySignature signature;

    public String getHost() {
        return host;
    }

    public UnionPayConfig setHost(String host) {
        this.host = host;
        return this;
    }

    public String getFileDownloadHost() {
        return fileDownloadHost;
    }

    public UnionPayConfig setFileDownloadHost(String fileDownloadHost) {
        this.fileDownloadHost = fileDownloadHost;
        return this;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public UnionPayConfig setMerchantId(String merchantId) {
        this.merchantId = merchantId;
        return this;
    }

    public String getEncoding() {
        return encoding;
    }

    public UnionPayConfig setEncoding(String encoding) {
        this.encoding = encoding;
        return this;
    }

    public UnionPaySignature getSignature() {
        return signature;
    }

    public UnionPayConfig setSignature(UnionPaySignature signature) {
        this.signature = signature;
        return this;
    }
}
