package com.demo.pay.unionpay;

import java.util.Map;

/**
 * 银联签名接口
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/8/28
 */
public interface UnionPaySignature {

    /**
     * 摘要
     *
     * @param paramStr 参数字符串
     * @param charset  字符编码
     * @return 摘要字符串
     */
    String digest(String paramStr, String charset);

    /**
     * 将字符串参数进行签名
     *
     * @param paramStr 待签名字符串
     * @return 签名字符串
     */
    String sign(String paramStr);

    /**
     * 验证签名
     *
     * @param paramMap Map参数
     * @param charset  字符编码
     * @return boolean
     */
    boolean verify(Map<String, String> paramMap, String charset);

    /**
     * 获取版本号
     *
     * @return 版本号
     */
    String getVersion();

    /**
     * 获取证书ID号
     *
     * @return 证书ID号
     */
    String getCertId();

    /**
     * 获取签名方法
     *
     * @return 签名方法
     */
    String getSignMethod();
}
