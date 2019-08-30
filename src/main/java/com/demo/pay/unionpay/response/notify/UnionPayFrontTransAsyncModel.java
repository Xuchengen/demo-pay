package com.demo.pay.unionpay.response.notify;

import com.demo.pay.unionpay.UnionPayResponse;

/**
 * 消费接口异步通知模型
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/8/29
 */
public class UnionPayFrontTransAsyncModel extends UnionPayResponse {

    private static final long serialVersionUID = -2885226330044985251L;

    /**
     * 查询流水号
     */
    private String queryId;

    /**
     * 交易币种
     */
    private String currencyCode;

    /**
     * 交易传输时间
     */
    private String traceTime;

    /**
     * 清算币种
     */
    private String settleCurrencyCode;

    /**
     * 清算金额
     */
    private String settleAmt;

    /**
     * 清算日期
     */
    private String settleDate;

    /**
     * 系统跟踪号
     */
    private String traceNo;

    /**
     * 兑换日期
     */
    private String exchangeDate;

    /**
     * 清算汇率
     */
    private String exchangeRate;

    /**
     * 账号
     */
    private String accNo;

    /**
     * 支付方式
     */
    private String payType;

    /**
     * 支付卡标识
     */
    private String payCardNo;

    /**
     * 支付卡类型
     */
    private String payCardType;

    /**
     * 支付卡名称
     */
    private String payCardIssueName;

    /**
     * 绑定标识号
     */
    private String bindId;

    /**
     * 产品类型
     */
    private String bizType;

    /**
     * 订单发送时间
     */
    private String txnTime;

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

    /**
     * 分账域
     */
    private String accSplitData;

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getTraceTime() {
        return traceTime;
    }

    public void setTraceTime(String traceTime) {
        this.traceTime = traceTime;
    }

    public String getSettleCurrencyCode() {
        return settleCurrencyCode;
    }

    public void setSettleCurrencyCode(String settleCurrencyCode) {
        this.settleCurrencyCode = settleCurrencyCode;
    }

    public String getSettleAmt() {
        return settleAmt;
    }

    public void setSettleAmt(String settleAmt) {
        this.settleAmt = settleAmt;
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

    public String getExchangeDate() {
        return exchangeDate;
    }

    public void setExchangeDate(String exchangeDate) {
        this.exchangeDate = exchangeDate;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
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

    public String getPayCardNo() {
        return payCardNo;
    }

    public void setPayCardNo(String payCardNo) {
        this.payCardNo = payCardNo;
    }

    public String getPayCardType() {
        return payCardType;
    }

    public void setPayCardType(String payCardType) {
        this.payCardType = payCardType;
    }

    public String getPayCardIssueName() {
        return payCardIssueName;
    }

    public void setPayCardIssueName(String payCardIssueName) {
        this.payCardIssueName = payCardIssueName;
    }

    public String getBindId() {
        return bindId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
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

    public String getAccSplitData() {
        return accSplitData;
    }

    public void setAccSplitData(String accSplitData) {
        this.accSplitData = accSplitData;
    }
}
