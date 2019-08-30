package com.demo.pay.unionpay.internal;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.CryptoException;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.demo.pay.unionpay.UnionPayCertInfo;
import sun.security.provider.X509Factory;

import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

/**
 * 安全工具类
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019-08-27
 */
public class SecureHelper {

    private static final String PKCS12 = "PKCS12";

    private static final String X509 = "X.509";

    /**
     * 从输入流获取密钥信息
     *
     * @param inputStream 输入流
     * @param password    密码
     * @return UnionpayCertInfo
     */
    public static UnionPayCertInfo getCertInfoFromInputStream(InputStream inputStream, String password) {
        try {
            char[] passwordCharArray = password.toCharArray();
            KeyStore keyStore = KeyUtil.readKeyStore(PKCS12, inputStream, passwordCharArray);
            Enumeration enumas = keyStore.aliases();
            String keyAlias = null;
            if (enumas.hasMoreElements()) {
                keyAlias = (String) enumas.nextElement();
            }
            KeyPair keyPair = KeyUtil.getKeyPair(keyStore, passwordCharArray, keyAlias);
            X509Certificate certificate = (X509Certificate) keyStore.getCertificate(keyAlias);

            return new UnionPayCertInfo()
                    .setKeyPair(keyPair)
                    .setSerialNo(certificate.getSerialNumber().toString())
                    .setPublicKeyStr(Base64.encode(keyPair.getPublic().getEncoded()))
                    .setPrivateKeyStr(Base64.encode(keyPair.getPrivate().getEncoded()));
        } catch (Exception e) {
            throw new CryptoException(e);
        } finally {
            IoUtil.close(inputStream);
        }
    }

    /**
     * 获取证书的CN
     *
     * @param certificate 证书
     * @return
     */
    public static String getIdentitiesFromCertficate(X509Certificate certificate) {
        String tDN = certificate.getSubjectDN().toString();
        String tPart = "";
        if ((tDN != null)) {
            String tSplitStr[] = tDN.substring(tDN.indexOf("CN=")).split("@");
            if (tSplitStr != null && tSplitStr.length > 2
                    && tSplitStr[2] != null)
                tPart = tSplitStr[2];
        }
        return tPart;
    }

    /**
     * 从输入流中读取证书字符串
     *
     * @param inputStream 输入流
     * @return 证书字符串
     */
    public static String getCertStrFromInputStream(InputStream inputStream) {
        try {
            Certificate certificate = KeyUtil.readCertificate(X509, inputStream);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(X509Factory.BEGIN_CERT)
                    .append(System.getProperty("line.separator"))
                    .append(Base64.encode(certificate.getEncoded()))
                    .append(System.getProperty("line.separator"))
                    .append(X509Factory.END_CERT);
            return stringBuilder.toString();
        } catch (Exception e) {
            throw new CryptoException(e);
        } finally {
            IoUtil.close(inputStream);
        }
    }

    /**
     * 获取SHA1摘要字符串
     *
     * @param paramStr 参数字符串
     * @param charset  字符编码
     * @return 摘要字符串
     */
    public static String getSHA1Digest(String paramStr, String charset) {
        return new Digester(DigestAlgorithm.SHA1).digestHex(paramStr, charset);
    }

    /**
     * 获取SHA256摘要字符串
     *
     * @param paramStr 参数字符串
     * @param charset  字符编码
     * @return 摘要字符串
     */
    public static String getSHA256Digest(String paramStr, String charset) {
        return new Digester(DigestAlgorithm.SHA256).digestHex(paramStr, charset);
    }

    /**
     * 通过SHA1withRSA算法进行签名
     *
     * @param paramStr   参数字符串
     * @param privateKey 私钥
     * @return 签名字符串
     */
    public static String signBySHA1withRSA(String paramStr, String privateKey) {
        Sign sign = SecureUtil.sign(SignAlgorithm.SHA1withRSA, privateKey, null);
        return Base64.encode(sign.sign(StrUtil.bytes(paramStr)));
    }

    /**
     * 通过SHA256withRSA算法进行签名
     *
     * @param paramStr   参数字符串
     * @param privateKey 私钥
     * @return 签名字符串
     */
    public static String signBySHA256withRSA(String paramStr, String privateKey) {
        Sign sign = SecureUtil.sign(SignAlgorithm.SHA256withRSA, privateKey, null);
        return Base64.encode(sign.sign(StrUtil.bytes(paramStr)));
    }

    /**
     * 验证签名
     *
     * @param digestData 摘要数据
     * @param signData   签名数据
     * @param publicKey  公钥
     * @return 验证结果
     */
    public static boolean verifyBySHA256withRSA(byte[] digestData, byte[] signData, byte[] publicKey) {
        Sign _sign = SecureUtil.sign(SignAlgorithm.SHA256withRSA, null, publicKey);
        return _sign.verify(digestData, signData);
    }
}
