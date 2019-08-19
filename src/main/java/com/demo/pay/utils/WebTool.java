package com.demo.pay.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WebTool {

    private static final Logger log = LoggerFactory.getLogger(WebTool.class);

    private static final String UTF_8 = "UTF-8";

    private static final String GBK = "GBK";

    /**
     * 获取HttpServletRequest
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取HttpSession
     *
     * @return HttpSession
     */
    public static HttpSession getSession() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
    }

    /**
     * 获取HttpServletResponse
     *
     * @return HttpServletResponse
     */
    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * 获取WebApplicationContext
     *
     * @return WebApplicationContext
     */
    public static WebApplicationContext getApplication() {
        return ContextLoader.getCurrentWebApplicationContext();
    }

    /**
     * 获取ServletContext
     *
     * @return ServletContext
     */
    public static ServletContext getServletContext() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getServletContext();
    }

    /**
     * 获取客户端真实IP地址
     *
     * @return String
     */
    public static String getIpAddr() {
        String ipAddress;
        HttpServletRequest request = getRequest();
        ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    log.error("未知主机", e);
                }
                ipAddress = inet.getHostAddress();
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    /**
     * 获取refererUrl-来路URL
     *
     * @return String
     */
    public static String getRefererUrl() {
        return getRequest().getHeader("referer");
    }

    /**
     * 使用Response输出
     *
     * @param str 字符串
     */
    public static void write(String str) {
        HttpServletResponse response = getResponse();
        response.setContentType("text/html;charset=utf-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(str);
        } catch (IOException e) {
            log.error("使用Response输出异常", e);
        }
    }

    /**
     * 使用Response输出
     *
     * @param object 字符串
     */
    public static void writeJson(Object object) {
        HttpServletResponse response = getResponse();
        response.setContentType("application/json;charset=utf-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(JSONUtil.toJsonStr(object));
        } catch (IOException e) {
            log.error("使用Response输出异常", e);
        }
    }

    /**
     * 是否Ajax请求
     *
     * @return boolean
     */
    public static boolean isAjax() {
        HttpServletRequest request = getRequest();
        if (!StrUtil.isBlank(request.getHeader("x-requested-with"))
                && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
            return true;
        }
        return false;
    }

    /**
     * 获取主机URL
     *
     * @return String
     */
    public static String getHostUrl() {
        HttpServletRequest request = getRequest();
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        String contextPath = request.getContextPath();
        int serverPort = request.getServerPort();

        StringBuilder sb = new StringBuilder();
        sb.append(scheme)
                .append("://")
                .append(serverName);

        if (80 != serverPort && 443 != serverPort) {
            sb.append(":").append(serverPort);
        }

        sb.append(contextPath);

        return sb.toString();
    }

}
