package com.github.xuchengen.setting;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;

import java.io.File;

/**
 * 设置工具
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/9/19
 */
public class SettingTool {

    public static void save(String fileName, Object setting) {
        File file = new File(System.getProperty("user.home") + File.separator + ".demo-pay" + File.separator + fileName);
        if (file.exists()) {
            FileUtil.del(file);
        }

        FileUtil.touch(file);

        String jsonStr = JSONUtil.toJsonStr(setting);

        FileUtil.writeUtf8String(JSONUtil.toJsonStr(jsonStr), file);
    }

    public static <T> T get(String fileName, Class<T> clazz) {
        File file = new File(System.getProperty("user.home") + File.separator + ".demo-pay" + File.separator + fileName);
        if (file.exists()) {
            String jsonStr = FileUtil.readUtf8String(file);
            if (JSONUtil.isJson(jsonStr)) {
                return JSONUtil.toBean(jsonStr, clazz);
            }
        }
        return null;
    }

    public static AliPaySetting getAliPaySetting() {
        AliPaySetting aliPaySetting = get("alipay.json", AliPaySetting.class);
        if (null != aliPaySetting) {
            return aliPaySetting;
        }
        return new AliPaySetting();
    }

}
