package com.github.xuchengen.tool;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;

import java.util.Date;

/**
 * 订单号工具类
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/9/17
 */
public class OrderNoTool {

    public static String generate() {
        return DateUtil.format(new Date(), "yyyyMMddHHmmsss") + RandomUtil.randomNumbers(5);
    }

}
