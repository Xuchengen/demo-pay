package com.github.xuchengen.web.config;

import com.github.xuchengen.setting.AliPaySetting;
import com.github.xuchengen.setting.SettingTool;
import com.github.xuchengen.web.BaseCtl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 支付宝配置
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/9/17
 */
@Controller
@RequestMapping(value = "/config/alipay")
public class AliPayConfigCtl extends BaseCtl {

    @GetMapping(value = {"", "/"})
    public String index(ModelMap modelMap) {
        AliPaySetting aliPaySetting = SettingTool.getAliPaySetting();
        modelMap.put("bean", aliPaySetting);
        return "/config/alipay.html";
    }

    @PostMapping(value = "/doSave")
    public String doSave(AliPaySetting aliPaySetting) {

        SettingTool.save("alipay.json", aliPaySetting);

        return "redirect:/config/alipay";
    }

}
