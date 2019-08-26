package com.demo.pay.unionpay;

public class GetwayRequest {

    /**
     * 版本号
     */
    private String version;

    /**
     * 编码方式
     */
    private String encoding;

    /**
     * 签名
     */
    private String signature;

    /**
     * 签名方法
     */
    private String signMethod;

    /**
     * 交易类型
     */
    private String txnType;

    /**
     * 交易子类
     */
    private String txnSubType;

    /**
     * 产品类型
     */
    private String bizType;

    /**
     * 渠道类型
     */
    private String channelType;

    /**
     * 前台通知地址
     */
    private String frontUrl;

    /**
     * 后台通知地址
     */
    private String backUrl;

    /**
     * 接入类型
     */
    private String accessType;

    /**
     * 收单机构代码
     */
    private String acqInsCode;

    /**
     * 商户类别
     */
    private String merCatCode;

    /**
     * 商户代码
     */
    private String merId;

    /**
     * 商户名称
     */
    private String merName;

    /**
     * 商户简称
     */
    private String merAbbr;

    /**
     * 二级商户代码
     */
    private String subMerId;

    /**
     * 二级商户全称
     */
    private String subMerName;

    /**
     * 二级商户简称
     */
    private String subMerAbbr;

    /**
     * 商户订单号
     */
    private String orderId;

    /**
     * 订单发送时间
     */
    private String txnTime;

    /**
     * 账号类型
     */
    private String accType;

    /**
     * 账号
     */
    private String accNo;

    /**
     * 交易金额
     */
    private String txnAmt;

    /**
     * 交易币种
     */
    private String currencyCode;

    /**
     * 银行卡验证信息及身份信息
     */
    private String customerInfo;

    /**
     * 订单接收超时时间（防钓鱼使用）
     */
    private String orderTimeout;

    /**
     * 订单支付超时时间
     */
    private String payTimeout;

    /**
     * 终端号
     */
    private String termId;

    /**
     * 请求方保留域
     */
    private String reqReserved;

    /**
     * 保留域
     */
    private String reserved;

    /**
     * 风险信息域
     */
    private String riskRateInfo;

    /**
     * 加密证书ID
     */
    private String encryptCertId;

    /**
     * 失败交易前台跳转地址
     */
    private String frontFailUrl;

    /**
     * 分期付款信息域
     */
    private String instalTransInfo;

    /**
     * 默认支付方式
     */
    private String defaultPayType;

    /**
     * 发卡机构代码
     */
    private String issInsCode;

    /**
     * 支持支付方式
     */
    private String supPayType;

    /**
     * 终端信息域
     */
    private String userMac;

    /**
     * 持卡人IP
     */
    private String customerIp;

    /**
     * 有卡交易信息域
     */
    private String cardTransData;

    /**
     * 订单描述
     */
    private String orderDesc;

    /**
     * 预付卡通道
     */
    private String accountPayChannel;

    /**
     * 分账域
     */
    private String accSplitData;

    /**
     * 控制规则
     */
    private String ctrlRule;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSignMethod() {
        return signMethod;
    }

    public void setSignMethod(String signMethod) {
        this.signMethod = signMethod;
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

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getFrontUrl() {
        return frontUrl;
    }

    public void setFrontUrl(String frontUrl) {
        this.frontUrl = frontUrl;
    }

    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getAcqInsCode() {
        return acqInsCode;
    }

    public void setAcqInsCode(String acqInsCode) {
        this.acqInsCode = acqInsCode;
    }

    public String getMerCatCode() {
        return merCatCode;
    }

    public void setMerCatCode(String merCatCode) {
        this.merCatCode = merCatCode;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
    }

    public String getMerAbbr() {
        return merAbbr;
    }

    public void setMerAbbr(String merAbbr) {
        this.merAbbr = merAbbr;
    }

    public String getSubMerId() {
        return subMerId;
    }

    public void setSubMerId(String subMerId) {
        this.subMerId = subMerId;
    }

    public String getSubMerName() {
        return subMerName;
    }

    public void setSubMerName(String subMerName) {
        this.subMerName = subMerName;
    }

    public String getSubMerAbbr() {
        return subMerAbbr;
    }

    public void setSubMerAbbr(String subMerAbbr) {
        this.subMerAbbr = subMerAbbr;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTxnTime() {
        return txnTime;
    }

    public void setTxnTime(String txnTime) {
        this.txnTime = txnTime;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(String txnAmt) {
        this.txnAmt = txnAmt;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(String customerInfo) {
        this.customerInfo = customerInfo;
    }

    public String getOrderTimeout() {
        return orderTimeout;
    }

    public void setOrderTimeout(String orderTimeout) {
        this.orderTimeout = orderTimeout;
    }

    public String getPayTimeout() {
        return payTimeout;
    }

    public void setPayTimeout(String payTimeout) {
        this.payTimeout = payTimeout;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getReqReserved() {
        return reqReserved;
    }

    public void setReqReserved(String reqReserved) {
        this.reqReserved = reqReserved;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public String getRiskRateInfo() {
        return riskRateInfo;
    }

    public void setRiskRateInfo(String riskRateInfo) {
        this.riskRateInfo = riskRateInfo;
    }

    public String getEncryptCertId() {
        return encryptCertId;
    }

    public void setEncryptCertId(String encryptCertId) {
        this.encryptCertId = encryptCertId;
    }

    public String getFrontFailUrl() {
        return frontFailUrl;
    }

    public void setFrontFailUrl(String frontFailUrl) {
        this.frontFailUrl = frontFailUrl;
    }

    public String getInstalTransInfo() {
        return instalTransInfo;
    }

    public void setInstalTransInfo(String instalTransInfo) {
        this.instalTransInfo = instalTransInfo;
    }

    public String getDefaultPayType() {
        return defaultPayType;
    }

    public void setDefaultPayType(String defaultPayType) {
        this.defaultPayType = defaultPayType;
    }

    public String getIssInsCode() {
        return issInsCode;
    }

    public void setIssInsCode(String issInsCode) {
        this.issInsCode = issInsCode;
    }

    public String getSupPayType() {
        return supPayType;
    }

    public void setSupPayType(String supPayType) {
        this.supPayType = supPayType;
    }

    public String getUserMac() {
        return userMac;
    }

    public void setUserMac(String userMac) {
        this.userMac = userMac;
    }

    public String getCustomerIp() {
        return customerIp;
    }

    public void setCustomerIp(String customerIp) {
        this.customerIp = customerIp;
    }

    public String getCardTransData() {
        return cardTransData;
    }

    public void setCardTransData(String cardTransData) {
        this.cardTransData = cardTransData;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public String getAccountPayChannel() {
        return accountPayChannel;
    }

    public void setAccountPayChannel(String accountPayChannel) {
        this.accountPayChannel = accountPayChannel;
    }

    public String getAccSplitData() {
        return accSplitData;
    }

    public void setAccSplitData(String accSplitData) {
        this.accSplitData = accSplitData;
    }

    public String getCtrlRule() {
        return ctrlRule;
    }

    public void setCtrlRule(String ctrlRule) {
        this.ctrlRule = ctrlRule;
    }
}
