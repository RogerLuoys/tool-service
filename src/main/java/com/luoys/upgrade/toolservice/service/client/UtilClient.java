package com.luoys.upgrade.toolservice.service.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 工具客户端，用于自动化步骤的工具类型实现
 *
 * @author luoys
 */
@Slf4j
@Component
public class UtilClient {


    private void sleep(int sec) {
        try {
            Thread.sleep(sec * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // "":"","":[],"":null,"":{}
    private String getJsonValue(String key, String json) {
//        String mode = "\"\\w+\":(\"\\S*\"|[\\S*]|\\)|{\\S*}";
        String strMode = "\"\\w+\":\"\\S*\",|";
        return null;
    }
}
