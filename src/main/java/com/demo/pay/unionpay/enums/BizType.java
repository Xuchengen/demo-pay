package com.demo.pay.unionpay.enums;

/**
 * 产品类型枚举
 */
public enum BizType {

    B2C_GETWAY_PAYMENT("000201", "B2C网关支付"),
    AUTH_PAYMENT("000301", "认证支付2.0"),
    RATING_PAYMENT("000302", "评级支付"),
    AGENT_PAYMENT("000401", "代付"),
    AGENT_RECEIVE("000501", "代收"),
    BILL_PAYMENT("000601", "账单支付"),
    ACROSS_BANK_RECEIVE("000801", "跨行收单"),
    BINDING_PAYMENT("000901", "绑定支付"),
    SUBSCRIBE("001001", "订购"),
    B2B("000202", "B2B");

    private String code;

    private String desc;

    public String getCode() {
        return code;
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

    BizType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
