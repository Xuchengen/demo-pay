package com.demo.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * 启动类
 */
@SpringBootApplication(scanBasePackages = "com.demo.pay", exclude = WebSocketServletAutoConfiguration.class)
@ServletComponentScan
public class Launcher {

    public static void main(String[] args) {
        SpringApplication.run(Launcher.class, args);
    }

}
