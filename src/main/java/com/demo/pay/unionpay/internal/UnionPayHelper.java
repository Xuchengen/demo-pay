package com.demo.pay.unionpay.internal;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.demo.pay.unionpay.UnionPayConstants;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 银联支付工具类
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/8/29
 */
public class UnionPayHelper {

    /**
     * 构建提交表单
     *
     * @param apiUrl   接口地址
     * @param hiddens  隐藏域参数
     * @param encoding 字符编码
     * @return html表单字符串
     */
    public static String buildSubmitFormStr(String apiUrl, Map<String, String> hiddens, String encoding) {
        StringBuffer sf = new StringBuffer();
        sf.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=" + encoding + "\"/></head><body>");
        sf.append("<form id = \"unionpay_form\" action=\"" + apiUrl
                + "\" method=\"post\">");
        if (null != hiddens && 0 != hiddens.size()) {
            Set<Map.Entry<String, String>> set = hiddens.entrySet();
            Iterator<Map.Entry<String, String>> it = set.iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> ey = it.next();
                String key = ey.getKey();
                String value = ey.getValue();
                sf.append("<input type=\"hidden\" name=\"" + key + "\" id=\""
                        + key + "\" value=\"" + value + "\"/>");
            }
        }
        sf.append("</form>");
        sf.append("</body>");
        sf.append("<script type=\"text/javascript\">");
        sf.append("document.all.unionpay_form.submit();");
        sf.append("</script>");
        sf.append("</html>");
        return sf.toString();
    }

    /**
     * 构建参数字符串
     *
     * @param data Map参数
     * @return 类似K1=V1&K2=V2字符串
     */
    public static String buildKVPairStr(Map<String, String> data) {
        TreeMap<String, String> tree = new TreeMap<>();
        Iterator<Map.Entry<String, String>> iter = data.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next();
            if (UnionPayConstants.VAR_SIGNATURE.equals(entry.getKey().trim())) {
                continue;
            }
            tree.put(entry.getKey(), entry.getValue());
        }
        iter = tree.entrySet().iterator();
        StringBuilder stringBuilder = new StringBuilder();
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next();
            stringBuilder.append(entry.getKey())
                    .append(UnionPayConstants.EQUAL)
                    .append(entry.getValue())
                    .append(UnionPayConstants.AMPERSAND);
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    /**
     * 构建键值对参数字符串使用URLEncode编码value值
     *
     * @param paramMap Map参数
     * @param charset  字符编码
     * @return 类似K1=V1&K2=V2字符串
     */
    public static String buildKVPairStrWithURLEncode(Map<String, String> paramMap, String charset) {
        StringBuffer sf = new StringBuffer();
        String reqstr = "";
        if (CollUtil.isNotEmpty(paramMap)) {
            for (Map.Entry<String, String> en : paramMap.entrySet()) {
                sf.append(en.getKey())
                        .append(UnionPayConstants.EQUAL)
                        .append(StrUtil.isBlank(en.getValue()) ? "" : URLUtil.encodeQuery(en.getValue(), charset))
                        .append(UnionPayConstants.AMPERSAND);
            }
            reqstr = sf.substring(0, sf.length() - 1);
        }
        return reqstr;
    }

    /**
     * 根据键值对参数字符串构建Map
     */
    public static Map<String, String> buildMapByKVPairStr(String result) {
        Map<String, String> map = null;
        if (StrUtil.isNotBlank(result)) {
            if (result.startsWith("{") && result.endsWith("}")) {
                result = result.substring(1, result.length() - 1);
            }
            map = parseQString(result);
        }
        return map;
    }

    /**
     * 解析应答字符串，生成应答要素
     *
     * @param str 需要解析的字符串
     * @return 解析的结果map
     */
    private static Map<String, String> parseQString(String str) {
        Map<String, String> map = new TreeMap<>();
        int len = str.length();
        StringBuilder temp = new StringBuilder();
        char curChar;
        String key = null;
        boolean isKey = true;
        boolean isOpen = false;//值里有嵌套
        char openName = 0;
        if (len > 0) {
            for (int i = 0; i < len; i++) {// 遍历整个带解析的字符串
                curChar = str.charAt(i);// 取当前字符
                if (isKey) {// 如果当前生成的是key
                    if (curChar == '=') {// 如果读取到=分隔符
                        key = temp.toString();
                        temp.setLength(0);
                        isKey = false;
                    } else {
                        temp.append(curChar);
                    }
                } else {// 如果当前生成的是value
                    if (isOpen) {
                        if (curChar == openName) {
                            isOpen = false;
                        }
                    } else {//如果没开启嵌套
                        if (curChar == '{') {//如果碰到，就开启嵌套
                            isOpen = true;
                            openName = '}';
                        }
                        if (curChar == '[') {
                            isOpen = true;
                            openName = ']';
                        }
                    }
                    if (curChar == '&' && !isOpen) {// 如果读取到&分割符,同时这个分割符不是值域，这时将map里添加
                        putKeyValueToMap(temp, isKey, key, map);
                        temp.setLength(0);
                        isKey = true;
                    } else {
                        temp.append(curChar);
                    }
                }
            }
            putKeyValueToMap(temp, isKey, key, map);
        }
        return map;
    }

    private static void putKeyValueToMap(StringBuilder temp, boolean isKey, String key, Map<String, String> map) {
        if (isKey) {
            key = temp.toString();
            if (key.length() == 0) {
                throw new RuntimeException("QString format illegal");
            }
            map.put(key, "");
        } else {
            if (key.length() == 0) {
                throw new RuntimeException("QString format illegal");
            }
            map.put(key, temp.toString());
        }
    }
}
