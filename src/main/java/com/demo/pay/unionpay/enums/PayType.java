package com.demo.pay.unionpay.enums;

/**
 * 支付类型枚举
 */
public enum PayType {

    AUTH_PAYMENT("0001", "认证支付"),
    QUICK_PAYMENT("0002", "快捷支付"),
    STORAGE_CARD_PAYMENT("0004", "储值卡支付"),
    IC_CARD_PAYMENT("0005", "IC卡支付"),
    ONLINE_BANK_PAYMENT("0201", " 网银支付"),
    MU_DAN_CHANG_TONG_CARD_PAYMENT("1001", "牡丹畅通卡支付"),
    ZHONG_TIE_YIN_TONG_CARD_PAYMENT("1002", "中铁银通卡支付"),
    CREDIT_CARD_PAYMENT("0401", "信用卡支付"),
    SMALL_AMOUNT_TEMPORARY_PAYMENT("0402", "小额临时支付"),
    AUTH_PAYMENT_V2("0403", "认证支付2.0"),
    INTERNET_ORDER_MOBILE_PAYMENT("0404", "互联网订单手机支付"),
    OTHER_NO_CARD_PAYMENT("9000", "其它无卡支付");

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

    PayType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
