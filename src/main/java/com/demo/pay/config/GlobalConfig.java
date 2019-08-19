package com.demo.pay.config;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalConfig {

    @Bean
    public TimedCache<String, String> timedCache() {
        TimedCache<String, String> cache = CacheUtil.newTimedCache(1000L * 60L * 60L);
        cache.schedulePrune(5000L);
        return cache;
    }

}
