package com.demo.pay.support.response;

import java.io.Serializable;

public class ResponseWrapper<T> implements Serializable {

    private static final long serialVersionUID = -3373681610836130988L;

    private String code;

    private String msg;

    private T data;

    private boolean success;

    /**
     * 私有构造器
     *
     * @param code    响应编码
     * @param message 响应消息
     */
    private ResponseWrapper(String code, String message) {
        this.code = code;
        this.msg = message;
        assert DefaultResponseEnum.SUCCESS.getCode() != null;
        this.success = DefaultResponseEnum.SUCCESS.getCode().equals(code);
    }

    /**
     * 实例化
     *
     * @param absResponseEnum 抽象响应枚举
     * @return ResponseWrapper
     */
    public static ResponseWrapper newInstance(AbsResponseEnum absResponseEnum) {
        if (null == absResponseEnum) {
            absResponseEnum = DefaultResponseEnum.SUCCESS;
        }
        return new ResponseWrapper(absResponseEnum.getCode(), absResponseEnum.getMessage());
    }

    /**
     * 实例化
     *
     * @param code    响应编码
     * @param message 响应消息
     * @return ResponseWrapper
     */
    public static ResponseWrapper newInstance(String code, String message) {
        assert code != null && message != null;
        return new ResponseWrapper(code, message);
    }

    /**
     * 成功
     *
     * @return ResponseWrapper
     */
    public static ResponseWrapper ok() {
        return new ResponseWrapper(DefaultResponseEnum.SUCCESS.getCode(), DefaultResponseEnum.SUCCESS.getMessage());
    }

    public static ResponseWrapper ok(Object data) {
        ResponseWrapper responseWrapper = new ResponseWrapper(DefaultResponseEnum.SUCCESS.getCode(), DefaultResponseEnum.SUCCESS.getMessage());
        responseWrapper.setData(data);
        return responseWrapper;
    }

    /**
     * 失败
     *
     * @return ResponseWrapper
     */
    public static ResponseWrapper fail() {
        return new ResponseWrapper(DefaultResponseEnum.FAIL.getCode(), DefaultResponseEnum.FAIL.getMessage());
    }

    /**
     * 异常
     *
     * @return ResponseWrapper
     */
    public static ResponseWrapper error() {
        return new ResponseWrapper(DefaultResponseEnum.ERROR.getCode(), DefaultResponseEnum.ERROR.getMessage());
    }

    /**
     * 其它
     *
     * @param message 响应消息
     * @return ResponseWrapper
     */
    public static ResponseWrapper other(String message) {
        return new ResponseWrapper(DefaultResponseEnum.FAIL.getCode(), message);
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public ResponseWrapper<T> setData(T data) {
        this.data = data;
        return this;
    }

    public boolean getSuccess() {
        return success;
    }
}
