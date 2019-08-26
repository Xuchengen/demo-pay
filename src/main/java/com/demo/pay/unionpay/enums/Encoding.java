package com.demo.pay.unionpay.enums;

/**
 * 编码方式枚举
 */
public enum Encoding {

    UTF8("UTF-8"),
    GBK("GBK"),
    GB2312("GB2312"),
    GB18030("GB18030");

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    Encoding(String code) {
        this.code = code;
    }
}
