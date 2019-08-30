package com.demo.pay.unionpay;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.demo.pay.unionpay.internal.SecureHelper;
import com.demo.pay.unionpay.internal.UnionPayHelper;

import java.io.ByteArrayInputStream;
import java.security.cert.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 银联V5.1.0签名
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/8/28
 */
public class UnionPayV510Signture implements UnionPaySignature {

    private static final Log log = LogFactory.get(UnionPayConstants.UNIONPAY_LOG);

    /**
     * 版本
     */
    private static final String version = UnionPayConstants.V510_VERSION;

    /**
     * 签名方法
     */
    private static final String signMethod = UnionPayConstants.SIGN_METHOD_RSA;

    /**
     * 根证书字符串
     */
    private String rootCertStr;

    /**
     * 中间证书字符串
     */
    private String middleCertStr;

    /**
     * 加密证书字符串
     */
    private String encryCertStr;

    /**
     * 商户证书信息
     */
    private UnionPayCertInfo merchantCertInfo;

    /**
     * 验证证书是否属于银联
     */
    private boolean verifyCNName;

    public String getRootCertStr() {
        return rootCertStr;
    }

    public UnionPayV510Signture setRootCertStr(String rootCertStr) {
        this.rootCertStr = rootCertStr;
        return this;
    }

    public String getMiddleCertStr() {
        return middleCertStr;
    }

    public UnionPayV510Signture setMiddleCertStr(String middleCertStr) {
        this.middleCertStr = middleCertStr;
        return this;
    }

    public String getEncryCertStr() {
        return encryCertStr;
    }

    public UnionPayV510Signture setEncryCertStr(String encryCertStr) {
        this.encryCertStr = encryCertStr;
        return this;
    }

    public UnionPayCertInfo getMerchantCertInfo() {
        return merchantCertInfo;
    }

    public UnionPayV510Signture setMerchantCertInfo(UnionPayCertInfo merchantCertInfo) {
        this.merchantCertInfo = merchantCertInfo;
        return this;
    }

    public boolean getVerifyCNName() {
        return verifyCNName;
    }

    public void setVerifyCNName(boolean verifyCNName) {
        this.verifyCNName = verifyCNName;
    }

    @Override
    public String digest(String paramStr, String charset) {
        return SecureHelper.getSHA256Digest(paramStr, charset);
    }

    @Override
    public String sign(String paramStr) {
        return SecureHelper.signBySHA256withRSA(paramStr, merchantCertInfo.getPrivateKeyStr());
    }

    public boolean verify(Map<String, String> paramMap, String charset) {
        if (!(paramMap instanceof TreeMap)) {
            paramMap = new TreeMap<>(paramMap);
        }

        if (log.isDebugEnabled()) {
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                log.debug("[{}]<=====>[{}]", entry.getKey(), entry.getValue());
            }
        }

        String signStr = "";
        if (paramMap.containsKey(UnionPayConstants.VAR_SIGNATURE)) {
            signStr = paramMap.remove(UnionPayConstants.VAR_SIGNATURE);
        }

        String signPublicKeyCert = paramMap.get(UnionPayConstants.VAR_SIGN_PUBLIC_KEY_CERT);

        if (StrUtil.isBlank(signPublicKeyCert)) {
            log.warn("签名公钥证书为空");
            return false;
        }

        X509Certificate signCert;
        ByteArrayInputStream signPublicKeyCertStream = IoUtil.toStream(signPublicKeyCert, charset);
        try {
            signCert = (X509Certificate) KeyUtil.readX509Certificate(signPublicKeyCertStream);
        } catch (Exception e) {
            log.warn("签名公钥证书读取错误：[{}]", signPublicKeyCert, e);
            return false;
        } finally {
            IoUtil.close(signPublicKeyCertStream);
        }

        try {
            signCert.checkValidity();
        } catch (CertificateExpiredException e) {
            log.warn("签名公钥证书已过期：[{}]", signPublicKeyCert, e);
            return false;
        } catch (CertificateNotYetValidException e) {
            log.warn("签名公钥证书尚未生效：[{}]", signPublicKeyCert, e);
            return false;
        }

        ByteArrayInputStream middleCertStream = IoUtil.toUtf8Stream(middleCertStr);
        X509Certificate middleCert;
        try {
            middleCert = (X509Certificate) KeyUtil.readX509Certificate(middleCertStream);
        } catch (Exception e) {
            log.warn("中间证书读取错误：[{}]", middleCertStr, e);
            return false;
        } finally {
            IoUtil.close(middleCertStream);
        }

        ByteArrayInputStream rootCertStream = IoUtil.toUtf8Stream(rootCertStr);
        X509Certificate rootCert;
        try {
            rootCert = (X509Certificate) KeyUtil.readX509Certificate(rootCertStream);
        } catch (Exception e) {
            log.warn("根证书读取错误：[{}]", rootCertStr, e);
            return false;
        } finally {
            IoUtil.close(rootCertStream);
        }

        try {
            X509CertSelector selector = new X509CertSelector();
            selector.setCertificate(signCert);

            Set<TrustAnchor> trustAnchors = new HashSet<>();
            trustAnchors.add(new TrustAnchor(rootCert, null));
            PKIXBuilderParameters pkixParams = new PKIXBuilderParameters(
                    trustAnchors, selector);

            Set<X509Certificate> intermediateCerts = new HashSet<>();
            intermediateCerts.add(rootCert);
            intermediateCerts.add(middleCert);
            intermediateCerts.add(signCert);

            pkixParams.setRevocationEnabled(false);

            CertStore intermediateCertStore = CertStore.getInstance("Collection",
                    new CollectionCertStoreParameters(intermediateCerts));
            pkixParams.addCertStore(intermediateCertStore);

            CertPathBuilder builder = CertPathBuilder.getInstance("PKIX");

            builder.build(pkixParams);

        } catch (java.security.cert.CertPathBuilderException e) {
            log.warn("验证证书链失败", e);
            return false;
        } catch (Exception e) {
            log.warn("验证证书链异常", e);
            return false;
        }

        String identitiesFromCertficate = SecureHelper.getIdentitiesFromCertficate(signCert);
        if (verifyCNName) {
            // 验证公钥是否属于银联
            if (!UnionPayConstants.UNIONPAY_CNNAME.equals(identitiesFromCertficate)) {
                log.warn("该证书所有者不是中国银联:[{}]", identitiesFromCertficate);
                return false;
            }
        } else {
            // 验证公钥是否属于银联
            if (!UnionPayConstants.UNIONPAY_CNNAME.equals(identitiesFromCertficate)
                    && !"00040000:SIGN".equals(identitiesFromCertficate)) {
                log.warn("该证书所有者不是中国银联:[{}]", identitiesFromCertficate);
                return false;
            }
        }

        String kvPairStr = UnionPayHelper.buildKVPairStr(paramMap);

        String digest = SecureHelper.getSHA256Digest(kvPairStr, charset);

        byte[] publicKey = signCert.getPublicKey().getEncoded();

        byte[] signData = Base64.decode(signStr);

        byte[] digestData = StrUtil.bytes(digest);

        boolean result = SecureHelper.verifyBySHA256withRSA(digestData, signData, publicKey);

        if (log.isDebugEnabled()) {
            log.debug("银联签名参数字符串：[{}]", signStr);

            log.debug("待验签键值对参数字符串：[{}]", kvPairStr);

            log.debug("待验签摘要字符串：[{}]", digest);

            log.debug("验签结果：[{}]", result);
        }

        return result;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getCertId() {
        return merchantCertInfo.getSerialNo();
    }

    @Override
    public String getSignMethod() {
        return signMethod;
    }
}
