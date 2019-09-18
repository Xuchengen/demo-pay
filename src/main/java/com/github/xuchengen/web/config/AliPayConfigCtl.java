package com.github.xuchengen.web.config;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.setting.Setting;
import com.github.xuchengen.setting.AliPaySetting;
import com.github.xuchengen.web.BaseCtl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;

/**
 * 支付宝配置
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/9/17
 */
@Controller
@RequestMapping(value = "/config/alipay")
public class AliPayConfigCtl extends BaseCtl {

    private static final String userDir = System.getProperty("user.home");

    @GetMapping(value = {"", "/"})
    public String index(ModelMap modelMap) {
        File file = new File(userDir + File.separator + ".demo-pay" + File.separator + "alipay.ini");

        if (file.exists()) {
            Setting setting = new Setting(file.getAbsolutePath());
            AliPaySetting aliPaySetting = new AliPaySetting();
            setting.toBean("alipay", aliPaySetting);
            modelMap.put("bean", aliPaySetting);
        }
        return "/config/alipay.html";
    }

    @PostMapping(value = "/doSave")
    public String doSave(String gateway,
                         String appId,
                         String format,
                         String charset,
                         String signType,
                         String privateKey,
                         String publicKey) {

        File file = new File(userDir + File.separator + ".demo-pay" + File.separator + "alipay.ini");

        if (file.exists()) {
            FileUtil.del(file);
        }

        FileUtil.touch(file);

        Setting setting = new Setting(file, CharsetUtil.CHARSET_UTF_8, false);
        setting.set("alipay", "gateway", gateway);
        setting.set("alipay", "appId", appId);
        setting.set("alipay", "format", format);
        setting.set("alipay", "charset", charset);
        setting.set("alipay", "signType", signType);
        setting.set("alipay", "privateKey", privateKey);
        setting.set("alipay", "publicKey", publicKey);
        setting.store(file.getAbsolutePath());

        return "redirect:/";
    }

}
