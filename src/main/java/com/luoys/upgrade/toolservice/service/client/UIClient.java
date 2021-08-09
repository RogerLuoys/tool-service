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

@Slf4j
@Component
public class UIClient {

    private final Long DEFAULT_WAIT_TIME = 30L;
    private WebDriver driver = null;
    private final int forceTimeOut = 1;

    public synchronized String execute(UiDTO uiDTO) {
        switch (UiTypeEnum.fromCode(uiDTO.getType())) {
            case START:
                init(uiDTO.getUrl());
                return "true";
            case CLICK:
                click(uiDTO.getElement());
                return "true";
            case SEND_KEY:
                //todo
                sendKey(uiDTO.getElement(), "");
                return "true";
            case IS_EXIST:
                return isElementExist(uiDTO.getElement()).toString();
            default:
                return "false";
        }
    }

    public synchronized void quit() {
        if (driver == null) {
            return;
        }
        driver.close();
        driver.quit();
        log.info("--->关闭UI：{}", driver);
    }

    private void init(String url) {
        this.driver = new ChromeDriver();
        this.driver.get(url);
        this.driver.manage().window().maximize();
    }

    private void refresh(String url) {
        this.driver.get(url);
        forceWait(3);
    }

    private void forceWait(int second) {
        try {
            Thread.sleep((long) second * 1000);
        } catch (InterruptedException e) {
            log.error("\n---->线程睡眠异常");
        }
    }

    private List<WebElement> getElements(By locator) {
        forceWait(forceTimeOut);
        return driver.findElements(locator);
    }

    private List<WebElement> getElements(String xpath) {
        return getElements(By.xpath(xpath));
    }

    private void click(By locator) {
        forceWait(forceTimeOut);
        WebElement webElement = driver.findElement(locator);
        WebDriverWait webDriverWait = new WebDriverWait(driver, DEFAULT_WAIT_TIME);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement));
        Actions actions = new Actions(driver);
        actions.click(webElement);
        actions.perform();
    }

    private void click(String xpath) {
        click(By.xpath(xpath));
    }

    private void sendKey(String xpath, CharSequence key) {
        sendKey(By.xpath(xpath), key);
    }

    private void sendKey(By locator, CharSequence key) {
        forceWait(forceTimeOut);
        WebElement webElement = driver.findElement(locator);
        WebDriverWait webDriverWait = new WebDriverWait(driver, DEFAULT_WAIT_TIME);
        webDriverWait.until(ExpectedConditions.visibilityOf(webElement));
        webElement.clear();
        Actions actions = new Actions(driver);
        actions.sendKeys(webElement, key);
        actions.perform();
    }

    private void moveToElement(By locator) {
        forceWait(forceTimeOut);
        WebElement webElement = driver.findElement(locator);
        WebDriverWait webDriverWait = new WebDriverWait(driver, DEFAULT_WAIT_TIME);
        webDriverWait.until(ExpectedConditions.visibilityOf(webElement));
        Actions actions = new Actions(driver);
        actions.moveToElement(webElement);
        actions.perform();
    }

    private void moveToElement(String xpath) {
        moveToElement(By.xpath(xpath));
    }

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
