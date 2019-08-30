package com.demo.pay.unionpay.response.notify;

import com.demo.pay.unionpay.UnionPayResponse;

/**
 * 消费接口同步通知模型
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/8/29
 */
public class UnionPayFrontTransSyncModel extends UnionPayResponse {

    private static final long serialVersionUID = -8895229553910100474L;

    /**
     * 查询流水号
     */
    private String queryId;

    /**
     * 银联受理订单号
     */
    private String tn;

    /**
     * 账号
     */
    private String accNo;

    /**
     * 支付方式
     */
    private String payType;

    /**
     * 支付卡类型
     */
    private String payCardType;

    /**
     * 产品类型
     */
    private String bizType;

    /**
     * 订单发送时间
     */
    private String txnTime;

    /**
     * 交易币种
     */
    private String currencyCode;

    /**
     * 清算汇率
     */
    private String exchangeRate;

    /**
     * 清算金额
     */
    private String settleAmt;

    /**
     * 清算币种
     */
    private String settleCurrencyCode;

    /**
     * 清算日期
     */
    private String settleDate;

    /**
     * 交易金额
     */
    private String txnAmt;

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
     * 保留域
     */
    private String reserved;

    private String traceNo;

    private String traceTime;

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public String getTn() {
        return tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayCardType() {
        return payCardType;
    }

    public void setPayCardType(String payCardType) {
        this.payCardType = payCardType;
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

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
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

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getSettleAmt() {
        return settleAmt;
    }

    public void setSettleAmt(String settleAmt) {
        this.settleAmt = settleAmt;
    }

    public String getSettleCurrencyCode() {
        return settleCurrencyCode;
    }

    public void setSettleCurrencyCode(String settleCurrencyCode) {
        this.settleCurrencyCode = settleCurrencyCode;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }

    public String getTraceTime() {
        return traceTime;
    }

    public void setTraceTime(String traceTime) {
        this.traceTime = traceTime;
    }
}
