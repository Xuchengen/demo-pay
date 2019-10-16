package com.github.xuchengen.web.config;

import com.github.xuchengen.setting.SettingTool;
import com.github.xuchengen.setting.UnionPaySetting;
import com.github.xuchengen.web.BaseCtl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 银联配置
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/9/26
 */
@Controller
@RequestMapping(value = "/config/unionPay")
public class UnionPayConfigCtl extends BaseCtl {

    @GetMapping(value = {"", "/"})
    public String index(ModelMap modelMap) {
        UnionPaySetting unionPaySetting = SettingTool.getUnionPaySetting();
        modelMap.put("bean", unionPaySetting);
        return "/config/unionPay.html";
    }

    @PostMapping(value = "/doSave")
    public String doSave(UnionPaySetting unionPaySetting) {

        String env = unionPaySetting.getEnv();

        if ("test".equals(env)) {
            unionPaySetting.setFileDownloadUrl("https://filedownload.test.95516.com");
            unionPaySetting.setApiHost("https://gateway.test.95516.com");
        } else if ("prod".equals(env)) {
            unionPaySetting.setFileDownloadUrl("https://filedownload.95516.com");
            unionPaySetting.setApiHost("https://gateway.95516.com");
        }

        SettingTool.save("unionPay.json", unionPaySetting);

        return "redirect:/config/unionPay";
    }

}
