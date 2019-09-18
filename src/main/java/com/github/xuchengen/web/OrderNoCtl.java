package com.github.xuchengen.web;

import com.github.xuchengen.tool.OrderNoTool;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 订单控制器
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/9/17
 */
@Controller
@RequestMapping(value = "/common/order")
public class OrderNoCtl extends BaseCtl {

    @PostMapping(value = "/getOrderNo")
    @ResponseBody
    public String getOrderNo() {
        return OrderNoTool.generate();
    }

}
