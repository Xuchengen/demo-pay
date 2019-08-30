package com.demo.pay.unionpay;

/**
 * 请求基类
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019-08-26
 */
public interface UnionPayRequest<T extends UnionPayResponse> {

    /**
     * 获取接口请求路径
     *
     * @return 接口路径
     */
    String getApiPath();

    /**
     * 获取响应类型
     *
     * @return 响应类型
     */
    Class<T> getResponseClass();

}
