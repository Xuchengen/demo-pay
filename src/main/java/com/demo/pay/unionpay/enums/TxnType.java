package com.demo.pay.unionpay.enums;

/**
 * 交易类型枚举
 */
public enum TxnType {

    QUERY_TRANSACTION("00", "查询交易"),
    CONSUME("01", "消费"),
    PRE_AUTHORIZATION("02", "预授权"),
    PRE_AUTHORIZATION_FINISH("03", "预授权完成"),
    RETURNED_PURCHASE("04", "退货"),
    CIRCLE("05", "圈存"),
    AGENT_RECEIVE("11", "代收"),
    AGENT_PAYMENT("12", "代付"),
    BILL_PAYMENT("13", "账单支付"),
    TRANSFER("14", "转账"),
    BATCH_TRANSACTION("21", "批量交易"),
    BATCH_QUERY("22", "批量查询"),
    CONSUME_CANCEL("31", "消费撤销"),
    PRE_AUTHORIZATION_CANCEL("32", "预授权撤销"),
    PRE_AUTHORIZATION_CANCEL_FINISH("33", "预授权撤销完成"),
    BALANCE_QUERY("71", "余额查询"),
    REAL_NAME_AUTHENTICATE("72", "实名认证"),
    BILL_QUERY("73", "账单查询"),
    UNBINDING_RELATIONSHIP("74", "解除绑定关系"),
    QUERY_BINDING_RELATIONSHIP("75", "查询绑定关系"),
    FILE_TRANSFER("76", "文件传输"),
    SEND_SMS_VERIFY_CODE("77", "发送短信验证码"),
    OPEN_QUERY("78", "开通查询"),
    OPEN_TRANSACTION("79", "开通交易"),
    IC_CARD_SCRIPT_NOTIFY("94", "IC卡脚本通知"),
    QUERY_UPDATE_ENCRYPT_PUBLIC_CERT("95", "查询更新加密公钥证书");

    private String code;

    private String desc;

    public String getCode() {
        return code;
    }

    TxnType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
