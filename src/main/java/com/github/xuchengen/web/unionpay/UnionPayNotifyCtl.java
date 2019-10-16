package com.github.xuchengen.web.unionpay;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.github.xuchengen.DefaultUnionPayClient;
import com.github.xuchengen.UnionPayConstants;
import com.github.xuchengen.web.BaseCtl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 银联全渠道异步通知控制器
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/10/15
 */
@Controller
@RequestMapping(value = "/unionPay")
public class UnionPayNotifyCtl extends BaseCtl {

    private static final Log log = LogFactory.get(UnionPayNotifyCtl.class);

    @PostMapping(value = "/doNotify")
    @ResponseBody
    public String doNotify() {
        log.debug("接收银联异步回调通知");

        DefaultUnionPayClient client = getUnionPayClient();

        Map<String, String> paramMap = ServletUtil.getParamMap(getRequest());

        boolean result = client.getConfig().getSigner().verify(paramMap, paramMap.get(UnionPayConstants.VAR_ENCODING));

        if (result) {
            log.debug("接收银联异步回调通知验签结果：[验签成功]");
            return "OK";
        }

        return "验签失败";
    }

}
