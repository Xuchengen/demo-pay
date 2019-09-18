package com.github.xuchengen.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 首页控制器
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/9/11
 */
@Controller
public class IndexCtl extends BaseCtl {

    @GetMapping(value = {"", "/"})
    public String index() {
        return "index.html";
    }

}
