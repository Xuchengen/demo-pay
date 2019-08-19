package com.demo.pay.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexCtl extends BaseCtl {

    @GetMapping(value = {"", "/"})
    public String index() {
        return "index.html";
    }

}
