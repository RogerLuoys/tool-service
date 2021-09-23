package com.luoys.upgrade.toolservice.service.client;

import com.luoys.upgrade.toolservice.service.dto.UiDTO;
import com.luoys.upgrade.toolservice.service.enums.UiTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

/**
 * ui操作客户端
 *
 * @author luoys
 */
@Slf4j
@Component
public class UIClient {

    private final Long DEFAULT_WAIT_TIME = 30L;
    private WebDriver driver = null;
    private final int forceTimeOut = 1;

    /**
     * 执行ui步骤，有同步锁
     * @param uiDTO -
     * @return 执行结果
     */
    public synchronized String execute(UiDTO uiDTO) {
        switch (UiTypeEnum.fromCode(uiDTO.getType())) {
            case OPEN_URL:
                openUrl(uiDTO.getUrl());
                return "true";
            case CLICK:
                click(uiDTO.getElement());
                return "true";
            case SEND_KEY:
                sendKey(uiDTO.getElement(), uiDTO.getKey());
                return "true";
            case IS_EXIST:
                return isElementExist(uiDTO.getElement()).toString();
            default:
                return "false";
        }
    }

    /**
     * 退出webdriver
     */
    public synchronized void quit() {
        if (driver == null) {
            return;
        }
        driver.quit();
        forceWait(3);
    }

    /**
     * webdriver初始化，默认chrome
     */
    public void init() {
        this.driver = new ChromeDriver();
    }

    /**
     * 访问指定url
     * @param url 要访问的url
     */
    private void openUrl(String url) {
        this.driver.get(url);
        this.driver.manage().window().maximize();
        forceWait(5);
    }

    /**
     * 强制等待
     * @param second
     */
    private void forceWait(int second) {
        try {
            Thread.sleep((long) second * 1000);
        } catch (InterruptedException e) {
            log.error("--->线程睡眠异常");
        }
    }

    /**
     * 获取元素列表
     * @param locator 元素位置
     * @return 元素列表
     */
    private List<WebElement> getElements(By locator) {
        forceWait(forceTimeOut);
        return driver.findElements(locator);
    }

    /**
     * 先根据xpath找到所有符合条件的元素，再点击对应序号的元素
     * @param xpath 元素的xpath
     */
    private void click(String xpath) {
        forceWait(forceTimeOut);
        WebElement webElement = driver.findElement(By.xpath(xpath));
        WebDriverWait webDriverWait = new WebDriverWait(driver, DEFAULT_WAIT_TIME);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement));
        Actions actions = new Actions(driver);
        actions.click(webElement);
        actions.perform();
    }

    /**
     * 在元素中输入执行字符
     * @param xpath 元素的xpath
     * @param key 要输入的字符
     */
    private void sendKey(String xpath, CharSequence key) {
        forceWait(forceTimeOut);
        WebElement webElement = driver.findElement(By.xpath(xpath));
        WebDriverWait webDriverWait = new WebDriverWait(driver, DEFAULT_WAIT_TIME);
        webDriverWait.until(ExpectedConditions.visibilityOf(webElement));
        webElement.clear();
        Actions actions = new Actions(driver);
        actions.sendKeys(webElement, key);
        actions.perform();
    }

//    private void moveToElement(String xpath) {
//        forceWait(forceTimeOut);
//        WebElement webElement = driver.findElement(By.xpath(xpath));
//        WebDriverWait webDriverWait = new WebDriverWait(driver, DEFAULT_WAIT_TIME);
//        webDriverWait.until(ExpectedConditions.visibilityOf(webElement));
//        Actions actions = new Actions(driver);
//        actions.moveToElement(webElement);
//        actions.perform();
//    }

    private void moveAndClick(By locator) {
        forceWait(forceTimeOut);
        WebElement webElement = driver.findElement(locator);
        WebDriverWait webDriverWait = new WebDriverWait(driver, DEFAULT_WAIT_TIME);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement));
        Actions actions = new Actions(driver);
        actions.moveToElement(webElement).click();
        actions.perform();
    }

    private void moveAndClick(String xpath) {
        moveAndClick(By.xpath(xpath));
    }

    private Boolean isElementExist(By locator) {
        List<WebElement> webElementList = getElements(locator);
        return webElementList.size() > 0;
    }

    private Boolean isElementExist(String xpath) {
        return isElementExist(By.xpath(xpath));
    }

}
