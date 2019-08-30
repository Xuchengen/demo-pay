package com.demo.pay.unionpay.response;

import com.demo.pay.unionpay.UnionPayResponse;

/**
 * 在线网关支付--加密公钥更新查询接口响应模型
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/8/30
 */
public class UnionPayEncryPubKeyQueryResponse extends UnionPayResponse {

    private static final long serialVersionUID = 6128008140995549589L;

    /**
     * 加密公钥证书
     */
    private String encryptPubKeyCert;

    /**
     * 产品类型
     */
    private String bizType;

    /**
     * 订单发送时间
     */
    private String txnTime;

    /**
     * 交易类型
     */
    private String txnType;

    /**
     * 交易子类
     */
    private String txnSubType;

    /**
     * 接入类型
     */
    private String accessType;

    /**
     * 请求方保留域
     */
    private String reqReserved;

    /**
     * 商户代码
     */
    private String merId;

    /**
     * 商户订单号
     */
    private String orderId;

    /**
     * 收单机构代码
     */
    private String acqInsCode;

    /**
     * 证书类型
     */
    private String certType;

    /**
     * 保留域
     */
    private String reserved;

    public String getEncryptPubKeyCert() {
        return encryptPubKeyCert;
    }

    public void setEncryptPubKeyCert(String encryptPubKeyCert) {
        this.encryptPubKeyCert = encryptPubKeyCert;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getTxnTime() {
        return txnTime;
    }

    public void setTxnTime(String txnTime) {
        this.txnTime = txnTime;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getTxnSubType() {
        return txnSubType;
    }

    public void setTxnSubType(String txnSubType) {
        this.txnSubType = txnSubType;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getReqReserved() {
        return reqReserved;
    }

    public void setReqReserved(String reqReserved) {
        this.reqReserved = reqReserved;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAcqInsCode() {
        return acqInsCode;
    }

    public void setAcqInsCode(String acqInsCode) {
        this.acqInsCode = acqInsCode;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }
}
