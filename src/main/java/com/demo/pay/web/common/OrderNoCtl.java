package com.demo.pay.web.common;

import com.demo.pay.utils.OrderNoUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/common")
public class OrderNoCtl {

    @PostMapping(value = "/getOrderNo")
    @ResponseBody
    public ResponseEntity<String> getOrderNo() {
        return new ResponseEntity<>(OrderNoUtil.generate(), HttpStatus.OK);
    }

}
