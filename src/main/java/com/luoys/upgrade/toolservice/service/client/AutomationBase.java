package com.luoys.upgrade.toolservice.service.client;

import com.luoys.upgrade.toolservice.common.ProcessUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

// 主要解决AssertionClient和UIClient要且必须要使用同一个webdriver的问题
public class AutomationBase {
    // UiClient和AssertionClient使用
    private static WebDriver driver = null;
    // HttpClient 使用
    private static RestTemplate restTemplate = null;
    // DbClient 使用
    private static JdbcTemplate jdbcTemplate = null;


    public static WebDriver getChromeDriver() {
        if (driver == null) {
            // 设置启动参数
            ChromeOptions chromeOptions = new ChromeOptions();
            if (System.getProperty("os.name").toLowerCase().contains("linux")) {
                ProcessUtil.killLinuxProcess("chromedriver");
                chromeOptions.addArguments("--kiosk");
                chromeOptions.addArguments("--disable-dev-shm-usage");
            } else if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                ProcessUtil.killWindowsProcess("chromedriver");
            }
//            chromeOptions.addArguments("window-size=1920*1080");
            // 解决DevToolsActivePort文件不存在的报错
            chromeOptions.addArguments("--no-sandbox");
            // 设置启动浏览器空白页
            chromeOptions.addArguments("url=data:,");
            // 最大化
            chromeOptions.addArguments("--start-maximized");
            // 谷歌禁用GPU加速
            chromeOptions.addArguments("--disable-gpu");
            // 隐藏滚动条
            chromeOptions.addArguments("--hide-scrollbars");
            // 后台运行
            chromeOptions.addArguments("--headless");
            // 去掉Chrome提示受到自动软件控制
            chromeOptions.addArguments("disable-infobars");
            driver = new ChromeDriver(chromeOptions);
        }
        return driver;
    }

//    public static RestTemplateBuilder getRestTemplateBuilder() {
//        if (restTemplateBuilder == null) {
//            restTemplateBuilder = new RestTemplateBuilder();
//        }
//        return restTemplateBuilder;
//    }

    public static RestTemplate getRestTemplate() {
        if (restTemplate == null) {
            restTemplate = new RestTemplateBuilder().build();
        }
        return restTemplate;
    }

    public static JdbcTemplate getJdbcTemplate() {
        if (jdbcTemplate == null) {
            jdbcTemplate = new JdbcTemplate();
        }
        return jdbcTemplate;
    }

}
