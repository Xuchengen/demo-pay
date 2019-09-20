package com.github.xuchengen.config;

import cn.hutool.cache.impl.TimedCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 全局Bean配置
 * 作者：徐承恩
 * 邮箱：xuchengen@gmail.com
 * 日期：2019/9/18
 */
@Configuration
public class GlobalConfig {

    @Bean
    public TimedCache<String, Object> timedCache() {
        return new TimedCache<>(3600 * 24);
    }

}
