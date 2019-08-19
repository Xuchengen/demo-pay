package com.demo.pay.support.response;

public enum DefaultResponseEnum implements AbsResponseEnum {

    SUCCESS("200", "成功"),
    FAIL("400", "失败"),
    ERROR("500", "异常");

    /**
     * 响应编码
     */
    private String code;

    /**
     * 响应消息
     */
    private String message;

    DefaultResponseEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "DefaultResponseEnum{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                "} " + super.toString();
    }
}
