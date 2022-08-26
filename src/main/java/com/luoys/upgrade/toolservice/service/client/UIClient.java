package com.luoys.upgrade.toolservice.service.client;

import com.luoys.upgrade.toolservice.service.dto.UiDTO;
import com.luoys.upgrade.toolservice.service.enums.UiTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.springframework.stereotype.Component;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;

/**
 * ui操作客户端，用于自动化步骤的UI类型实现
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
     *
     * @param uiDTO -
     * @return 执行结果
     */
    public synchronized String execute(UiDTO uiDTO) {

        switch (UiTypeEnum.fromCode(uiDTO.getType())) {
            case OPEN_URL:
                return openUrl(uiDTO.getUrl());
            case CLICK:
                return click(uiDTO.getElement(), uiDTO.getElementId());
            case SEND_KEY:
                return sendKey(uiDTO.getElement(), uiDTO.getElementId(), uiDTO.getKey());
            case IS_EXIST:
                return isElementExist(uiDTO.getElement()).toString();
            case SWITCH_FRAME:
                return switchToFrame(uiDTO.getElement());
            case HOVER:
                return moveToElement(uiDTO.getElement(), uiDTO.getElementId());
            default:
                return "false";
        }
    }

//    /**
//     * 退出webdriver
//     */
//    public synchronized void quit() {
//        if (driver == null) {
//            return;
//        }
//        driver.quit();
//        forceWait(3);
//    }
//
//    /**
//     * webdriver初始化，默认chrome
//     */
//    public void init() {
//        this.driver = new ChromeDriver();
//    }
//
//    public void initChromeDriver() {
//        this.driver = AutomationBase.getChromeDriver();
//    }

    /**
     * 访问指定url
     *
     * @param url 要访问的url
     * @return 成功为true
     */
    private String openUrl(String url) {
        this.driver.get(url);
        this.driver.manage().window().maximize();
        forceWait(5);
        return "true";
    }

    /**
     * 强制等待
     *
     * @param second 单位秒
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
     *
     * @param xpath 元素xpath
     * @return 元素列表
     */
    private List<WebElement> getElements(String xpath) {
        forceWait(forceTimeOut);
        return driver.findElements(By.xpath(xpath));
    }

    /**
     * 先获取元素列表，再根据序号获取对应元素，并等待元素可点击
     *
     * @param xpath 元素xpath
     * @param index 元素在列表的序号，从1开始
     * @return 元素列表
     */
    private WebElement getElement(String xpath, int index) {
        forceWait(forceTimeOut);
        List<WebElement> webElement = driver.findElements(By.xpath(xpath));
        if (webElement.size() < index) {
            log.error("--->未找到对应自动化元素：xpath={}, index={}", xpath, index);
            return null;
        }
        WebDriverWait webDriverWait = new WebDriverWait(driver, DEFAULT_WAIT_TIME);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement.get(index - 1)));
        return webElement.get(index - 1);
    }

//    /**
//     * 先根据xpath找到所有符合条件的元素，再点击对应序号的元素
//     *
//     * @param xpath 元素的xpath
//     */
//    private void click(String xpath) {
//        forceWait(forceTimeOut);
//        WebElement webElement = driver.findElement(By.xpath(xpath));
//        WebDriverWait webDriverWait = new WebDriverWait(driver, DEFAULT_WAIT_TIME);
//        webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement));
//        Actions actions = new Actions(driver);
//        actions.click(webElement).perform();
//    }

    /**
     * 先根据xpath找到所有符合条件的元素，再点击对应序号的元素
     *
     * @param xpath 元素的xpath
     * @param index 元素序号，从1开始
     */
    private String click(String xpath, int index) {
        WebElement webElement = getElement(xpath, index);
        if (webElement == null) {
            return "false";
        }
        Actions actions = new Actions(driver);
        actions.click(webElement).perform();
        return "true";
    }

//    /**
//     * 在元素中输入执行字符
//     *
//     * @param xpath 元素的xpath
//     * @param key   要输入的字符
//     */
//    private void sendKey(String xpath, CharSequence key) {
//        forceWait(forceTimeOut);
//        WebElement webElement = driver.findElement(By.xpath(xpath));
//        WebDriverWait webDriverWait = new WebDriverWait(driver, DEFAULT_WAIT_TIME);
//        webDriverWait.until(ExpectedConditions.visibilityOf(webElement));
//        webElement.clear();
//        Actions actions = new Actions(driver);
//        actions.sendKeys(webElement, key).perform();
//    }

    /**
     * 在元素中输入执行字符
     *
     * @param xpath 元素的xpath
     * @param index 元素的序号
     * @param key   要输入的字符
     * @return 成功为true
     */
    private String sendKey(String xpath, int index, CharSequence key) {
        WebElement webElement = getElement(xpath, index);
        if (webElement == null) {
            return "false";
        }
        webElement.clear();
        Actions actions = new Actions(driver);
        actions.sendKeys(webElement, key).perform();
        return "true";
    }

    /**
     * 切换至对应的frame中
     *
     * @param frame iframe的id、name或xpath
     * @return 成功返回true
     */
    private String switchToFrame(String frame) {
        forceWait(forceTimeOut);
        if (frame.startsWith("\\\\")) {
            WebElement iFrameElement = driver.findElement(By.xpath(frame));
            driver.switchTo().frame(iFrameElement);
        } else {
            driver.switchTo().frame(frame);
        }
        return "true";
    }

    /**
     * 鼠标悬停对应元素上
     *
     * @param xpath 元素的xpath
     * @param index 元素的序号
     * @return 成功为true
     */
    private String moveToElement(String xpath, int index) {
        WebElement webElement = getElement(xpath, index);
        Actions actions = new Actions(driver);
        actions.moveToElement(webElement).perform();
        return "true";
    }

    /**
     * 判断对应元素是否存在
     * @param xpath 元素xpath
     * @return 存在返回true
     */
    private Boolean isElementExist(String xpath) {
        List<WebElement> webElementList = getElements(xpath);
        return webElementList.size() > 0;
    }


    /**
     * 切换到指定标签页
     * @param index 数组下标，0是初始页，1是新标签页
     */
    public void switchToWindow(Integer index) {
        Set<String> windows = driver.getWindowHandles();
        driver.switchTo().window(windows.toArray()[index].toString());
    }

    /**
     * 删除其他标签页，并切换到第0个标签页
     */
    public void closeOtherWindow() {
        Set<String> windows = driver.getWindowHandles();
        for (int i = 1; i < windows.size(); i++) {
            switchToWindow(i);
            driver.close();
        }
        switchToWindow(0);
    }

    /**
     * 删除浏览器cookies
     */
    public void deleteCookies() {
        driver.manage().deleteAllCookies();
    }


    public void execJs(String jsExecString) {
        ((JavascriptExecutor)driver).executeScript(jsExecString, new Object[0]);
    }


}
