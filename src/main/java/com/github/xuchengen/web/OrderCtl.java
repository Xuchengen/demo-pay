package com.github.xuchengen.web;

import cn.hutool.cache.impl.TimedCache;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.github.xuchengen.support.response.ResponseResult;
import com.github.xuchengen.tool.OrderNoTool;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 订单控制器
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/9/17
 */
@Controller
@RequestMapping(value = "/common/order")
public class OrderCtl extends BaseCtl {

    private static final Log log = LogFactory.get(OrderCtl.class);

    @Resource(name = "timedCache")
    public TimedCache<String, Object> timedCache;

    @PostMapping(value = "/getOrderNo")
    @ResponseBody
    public String getOrderNo() {
        return OrderNoTool.generate();
    }

    @PostMapping(value = "/getOrder")
    @ResponseBody
    public ResponseResult getOrder(@RequestParam String orderNo) {
        try {
            if (timedCache.containsKey(orderNo)) {
                return ResponseResult.ok(timedCache.get(orderNo));
            }
            return ResponseResult.fail("订单信息不存在");
        } catch (Exception e) {
            return ResponseResult.error();
        }
    }
}
