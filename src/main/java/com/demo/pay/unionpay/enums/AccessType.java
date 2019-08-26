package com.demo.pay.unionpay.enums;

/**
 * 接入类型
 */
public enum AccessType {

    MERCHANT("0", "商户直接接入"),
    RECEIVING_INSTITUTION("1", "收单机构接入"),
    PLATFORM_MERCHANT("2", "平台商户接入");

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

    AccessType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
