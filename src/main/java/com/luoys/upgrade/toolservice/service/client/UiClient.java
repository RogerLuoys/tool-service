package com.luoys.upgrade.toolservice.service.client;

import com.luoys.upgrade.toolservice.service.common.StringUtil;
import com.luoys.upgrade.toolservice.service.dto.StepDTO;
import com.luoys.upgrade.toolservice.service.enums.autoStep.methodType.UiEnum;
import io.appium.java_client.android.AndroidDriver;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverLogLevel;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.*;

/**
 * ui操作客户端，用于自动化步骤的UI类型实现
 *
 * @author luoys
 */
@Slf4j
public class UiClient {

//    private final Long DEFAULT_WAIT_TIME = 30L;
//    private WebDriver driver = null;
//    private final int forceTimeOut = 1;

    // 显式等待最大时间(有问题，偶尔会失效)
    private final Duration explicitDuration = Duration.ofSeconds(30L);
    // 隐式等待最大时间
    private final Duration implicitlyDuration = Duration.ofSeconds(10L);
    // 流畅等待最大时间
    private final Duration fluentDuration = Duration.ofSeconds(30L);
    private WebDriverWait webDriverWait = null;
    private Wait<WebDriver> wait = null;
    private WebDriver driver = null;
    private Actions actions = null;

    /**
     * 执行ui步骤，有同步锁
     *
     * @param stepDTO -
     * @return 执行结果
     */
    public String execute(StepDTO stepDTO) {
        String result = "步骤成功";
        switch (UiEnum.fromCode(stepDTO.getMethodType())) {
            case OPEN_URL:
                this.openUrl(stepDTO.getParameter1());
                break;
            case CLICK:
                this.click(stepDTO.getParameter1(), StringUtil.isBlank(stepDTO.getParameter2()) ? "0" : stepDTO.getParameter2());
                break;
            case CLICK_BY_JS:
                this.clickByJs(stepDTO.getParameter1());
                break;
            case CLICK_BY_MOVE:
                this.clickByMove(stepDTO.getParameter1());
                break;
            case SEND_KEY:
                if (StringUtil.isBlank(stepDTO.getParameter2()) && StringUtil.isBlank(stepDTO.getParameter3())) {
                    this.sendKey(stepDTO.getParameter1());
                } else if (StringUtil.isBlank(stepDTO.getParameter3())) {
                    this.sendKey(stepDTO.getParameter1(), stepDTO.getParameter2());
                } else {
                    this.sendKey(stepDTO.getParameter1(), stepDTO.getParameter2(), stepDTO.getParameter3());
                }
                break;
            case SEND_KEY_BY_ENTER:
                this.sendKeyByEnter(stepDTO.getParameter1(), stepDTO.getParameter2());
                break;
            case MOVE:
                this.move(stepDTO.getParameter1());
                break;
            case DRAG:
                this.drag(stepDTO.getParameter1(), stepDTO.getParameter2());
                break;
            case EXECUTE_JS:
                if (StringUtil.isBlank(stepDTO.getParameter2())) {
                    this.executeJs(stepDTO.getParameter1());
                } else {
                    this.executeJs(stepDTO.getParameter1(), stepDTO.getParameter2());
                }
                break;
            case SWITCH_TAB:
                this.switchTab();
                break;
            case CLEAR_COOKIES:
                this.clearCookies();
                break;
            default:
                result = "步骤失败";
        }
        return result;
    }

    /**
     * 进程睡眠，强制等待
     *
     * @param millis 等待的时间-单位豪秒
     */
    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取快捷键枚举值，不是快捷键则直接返回key本身
     * @param key 字符
     * @return 枚举值
     */
    protected CharSequence getKey(String key) {
        // 快捷键
        if (key.equalsIgnoreCase("<ENTER>")) {
            return Keys.ENTER;
        } else if (key.equalsIgnoreCase("<TAB>")) {
            return Keys.TAB;
        }
        // 普通输入
        return key;
    }

    protected void settings() {
        // 初始化actions
        this.actions = new Actions(this.driver);
        // 隐式等待设置
        this.driver.manage().timeouts().implicitlyWait(this.implicitlyDuration);
        // 显示等待初始化
        this.webDriverWait = new WebDriverWait(this.driver, this.explicitDuration);
        // 流畅等待初始化
        this.wait = new FluentWait<WebDriver>(this.driver)
                .withTimeout(this.fluentDuration)
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
    }

    /**
     * 返回driver实例
     */
    public WebDriver getDriver() {
        return this.driver;
    }

    /**
     * 用ChromeDriver初始化webdriver，参数默认，web模式；
     * 如果已初始化过，则跳过
     */
    public void initChromeWeb() {
        // 不为null则表示已初始化
        if (this.driver != null) {
            return;
        }
        // 设置启动参数
        ChromeOptions chromeOptions = new ChromeOptions();
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
//        chromeOptions.addArguments("log-level=3");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.setLogLevel(ChromeDriverLogLevel.OFF);
        this.driver = new ChromeDriver(chromeOptions);
        this.settings();
//        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.DISABLED);
    }

    /**
     * 用ChromeDriver初始化webdriver，自定义参数
     * 如果已初始化过，则跳过
     */
    public void initChrome(String... options) {
        if (this.driver != null) {
            return;
        }
        Map<String, String> mobileEmulationMap = new HashMap<>();
        List<String> cOptions = new ArrayList<>();
        for (String option :  options) {
            // 带“,”的是H5配置项，键值对
            if (option.matches(".+,.+")) {
                String[] var = option.split(",");
                mobileEmulationMap.put(var[0], var[1]);
            } else {
                cOptions.add(option);
            }
        }
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments(cOptions);
        if (mobileEmulationMap.size() > 0) {
            chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulationMap);
        }
        this.driver = new ChromeDriver(chromeOptions);
        this.settings();

    }

    /**
     * 用ChromeDriver初始化webdriver，参数默认，H5模式；
     * 如果已初始化过，则跳过
     */
    public void initChromeH5() {
        if (this.driver != null) {
            return;
        }
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--disable-gpu");
        Map<String, String> mobileEmulationMap = new HashMap<>();
        mobileEmulationMap.put("deviceName", "Samsung Galaxy S8+");
        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulationMap);
        chromeOptions.addArguments("--headless");
        this.driver = new ChromeDriver(chromeOptions);
        this.settings();

    }

    /**
     * 用AndroidDriver初始化webdriver，参数默认；
     * 如果已初始化过，则跳过
     */
    public void initAndroid() {
        // 连模拟器命令 adb connect 127.0.0.1:7555
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "Android"); //指定测试平台
        desiredCapabilities.setCapability("deviceName", "127.0.0.1:7555"); //指定测试机的ID,通过adb命令`adb devices`获取

//        cap.setCapability("app", "C:\\other\\q391m11market01xtg101s.apk");
        desiredCapabilities.setCapability("appPackage", "com.qmxsppa.novelreader");
        desiredCapabilities.setCapability("appActivity", "com.marketplaceapp.novelmatthew.mvp.ui.activity.main.AndroidAppActivity");

        //A new session could not be created的解决方法
//        cap.setCapability("appWaitActivity", "com.qmxsppa.novelreader");
//        //每次启动时覆盖session，否则第二次后运行会报错不能新建session
//        cap.setCapability("sessionOverride", true);
        try {
            this.driver = new AndroidDriver(new URL("http://localhost:4723/wd/hub"), desiredCapabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.settings();

    }

    /**
     * 刷新页面
     *
     * @param url 被测网站主页或登录页
     */
    public void openUrl(String url) {
        this.driver.get(url);
    }

    /**
     * 关闭浏览器且关闭资源
     */
    public void quit() {
        if (this.driver == null) {
            return;
        }
        this.driver.quit();
        this.driver = null;
    }

    /**
     * 获取同类别的自动化元素列表
     *
     * @param xpath 元素的xpath
     * @return 所有符合条件的元素
     */
    public List<WebElement> getElements(String xpath) {
        return this.driver.findElements(By.xpath(xpath));
    }

    /**
     * 鼠标点击指定元素
     *
     * @param xpath 元素的xpath
     */
    public void click(String xpath) {
        this.click(xpath, "0");
    }

    /**
     * 鼠标点击指定元素
     *
     * @param xpath 元素的xpath
     * @param index list下标，从0开始
     */
    public void click(String xpath, String index) {
        // 这里不能用显式等待，有的控件等不到，却可以点击
        WebElement webElement = this.wait.until(driver -> driver.findElements(By.xpath(xpath)).get(Integer.parseInt(index)));
        this.actions.click(webElement).perform();

    }

    /**
     * 通过js的形式点击页面元素
     * @param xpath 页面元素的xpath
     */
    public void clickByJs(String xpath) {
        this.executeJs("arguments[0].click();", xpath);
    }

    /**
     * 先鼠标移动到指定元素上，然后鼠标点击
     *
     * @param xpath 元素的xpath
     */
    public void clickByMove(String xpath) {
        WebElement webElement = this.wait.until(driver -> driver.findElement(By.xpath(xpath)));
        this.actions.moveToElement(webElement).click().perform();
    }

    /**
     * 先清除输入框的内容，再往指定元素中输入字符
     *
     * @param key   输入的字符串或快捷键
     */
    public void sendKey(String key) {
        this.actions.sendKeys(this.getKey(key)).perform();
    }

    /**
     * 先清除输入框的内容，再往指定元素中输入字符
     *
     * @param xpath 元素的xpath
     * @param key   输入的字符串或按键
     */
    public void sendKey(String xpath, String key) {
        WebElement webElement = this.wait.until(driver -> driver.findElement(By.xpath(xpath)));
        // 再强制等0.5s会更稳定(显示、隐式、流畅等待都有小概率操作失败)
        this.sleep(500L);
        webElement.clear();
        this.actions.sendKeys(webElement, this.getKey(key)).perform();
    }

    /**
     * 先清除输入框的内容，再往指定元素中输入字符
     *
     * @param xpath 元素的xpath
     * @param key   输入的字符串
     * @param shortcutKey 快捷键
     */
    public void sendKey(String xpath, String key, String shortcutKey) {
        WebElement webElement = this.wait.until(driver -> driver.findElement(By.xpath(xpath)));
        // 再强制等0.5s会更稳定(显示、隐式、流畅等待都有小概率操作失败)
        this.sleep(500L);
        webElement.clear();
        this.actions.sendKeys(webElement, this.getKey(key), this.getKey(shortcutKey)).perform();
    }

    /**
     * 先清除输入框的内容，再往指定元素中输入字符，再按Enter键
     *
     * @param xpath 元素的xpath
     * @param key   输入的字符串
     */
    public void sendKeyByEnter(String xpath, String key) {
        WebElement webElement = this.wait.until(driver -> driver.findElement(By.xpath(xpath)));
        // 再强制等0.5s会更稳定(显示、隐式、流畅等待都有小概率操作失败)
        this.sleep(500L);
        webElement.clear();
        this.actions.sendKeys(webElement, key, Keys.ENTER).perform();
    }

    /**
     * 鼠标移动到指定元素上
     *
     * @param xpath 元素的xpath
     */
    public void move(String xpath) {
        WebElement webElement = this.wait.until(driver -> driver.findElement(By.xpath(xpath)));
        this.actions.moveToElement(webElement).perform();
    }

    /**
     * 从一个元素位置拖拽到另一个元素位置
     * @param fromXpath
     * @param toXpath
     */
    public void drag(String fromXpath, String toXpath) {
        WebElement from = this.wait.until(driver -> driver.findElement(By.xpath(fromXpath)));
        WebElement to = this.wait.until(driver -> driver.findElement(By.xpath(toXpath)));
        this.actions.dragAndDrop(from, to).perform();

    }

    /**
     * 执行java script脚本
     *
     * @param javaScript 脚本
     */
    public void executeJs(String javaScript) {
        ((JavascriptExecutor) this.driver).executeScript(javaScript);
    }

    /**
     * 执行java script脚本
     *
     * @param javaScript 脚本
     * @param xpath 元素xpath
     */
    public void executeJs(String javaScript, String xpath) {
        WebElement webElement = this.driver.findElement(By.xpath(xpath));
        ((JavascriptExecutor) this.driver).executeScript(javaScript, webElement);
    }


    /**
     * 切换到最后一个标签页，并关闭其它；
     * 如果只有一个标签页，则不处理
     */
    public void switchTab() {
        Set<String> windows = this.driver.getWindowHandles();
        if (windows.size() <= 1) {
            return;
        }
        // 先关闭前面所有标签页
        for (int i = windows.size() - 2; i >= 0; i--) {
            this.driver.switchTo().window(windows.toArray()[i].toString());
            this.driver.close();
        }
        // 再切换至最后标签页
        this.driver.switchTo().window(windows.toArray()[windows.size() - 1].toString());
    }

    /**
     * 删除所有cookies
     */
    public void clearCookies() {
        this.driver.manage().deleteAllCookies();
    }

//    /**
//     * 访问指定url
//     *
//     * @param url 要访问的url
//     * @return 成功为true
//     */
//    private String openUrl(String url) {
//        this.driver.get(url);
//        this.driver.manage().window().maximize();
//        forceWait(5);
//        return "true";
//    }
//
//    /**
//     * 强制等待
//     *
//     * @param second 单位秒
//     */
//    private void forceWait(int second) {
//        try {
//            Thread.sleep((long) second * 1000);
//        } catch (InterruptedException e) {
//            log.error("--->线程睡眠异常");
//        }
//    }
//
//    /**
//     * 获取元素列表
//     *
//     * @param xpath 元素xpath
//     * @return 元素列表
//     */
//    private List<WebElement> getElements(String xpath) {
//        forceWait(forceTimeOut);
//        return driver.findElements(By.xpath(xpath));
//    }
//
//    /**
//     * 先获取元素列表，再根据序号获取对应元素，并等待元素可点击
//     *
//     * @param xpath 元素xpath
//     * @param index 元素在列表的序号，从1开始
//     * @return 元素列表
//     */
//    private WebElement getElement(String xpath, int index) {
//        forceWait(forceTimeOut);
//        List<WebElement> webElement = driver.findElements(By.xpath(xpath));
//        if (webElement.size() < index) {
//            log.error("--->未找到对应自动化元素：xpath={}, index={}", xpath, index);
//            return null;
//        }
//        WebDriverWait webDriverWait = new WebDriverWait(driver, DEFAULT_WAIT_TIME);
//        webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement.get(index - 1)));
//        return webElement.get(index - 1);
//    }
//
//    /**
//     * 先根据xpath找到所有符合条件的元素，再点击对应序号的元素
//     *
//     * @param xpath 元素的xpath
//     * @param index 元素序号，从1开始
//     */
//    private String click(String xpath, int index) {
//        WebElement webElement = getElement(xpath, index);
//        if (webElement == null) {
//            return "false";
//        }
//        Actions actions = new Actions(driver);
//        actions.click(webElement).perform();
//        return "true";
//    }
//
//    /**
//     * 在元素中输入执行字符
//     *
//     * @param xpath 元素的xpath
//     * @param index 元素的序号
//     * @param key   要输入的字符
//     * @return 成功为true
//     */
//    private String sendKey(String xpath, int index, CharSequence key) {
//        WebElement webElement = getElement(xpath, index);
//        if (webElement == null) {
//            return "false";
//        }
//        webElement.clear();
//        Actions actions = new Actions(driver);
//        actions.sendKeys(webElement, key).perform();
//        return "true";
//    }
//
//    /**
//     * 切换至对应的frame中
//     *
//     * @param frame iframe的id、name或xpath
//     * @return 成功返回true
//     */
//    private String switchToFrame(String frame) {
//        forceWait(forceTimeOut);
//        if (frame.startsWith("\\\\")) {
//            WebElement iFrameElement = driver.findElement(By.xpath(frame));
//            driver.switchTo().frame(iFrameElement);
//        } else {
//            driver.switchTo().frame(frame);
//        }
//        return "true";
//    }
//
//    /**
//     * 鼠标悬停对应元素上
//     *
//     * @param xpath 元素的xpath
//     * @param index 元素的序号
//     * @return 成功为true
//     */
//    private String moveToElement(String xpath, int index) {
//        WebElement webElement = getElement(xpath, index);
//        Actions actions = new Actions(driver);
//        actions.moveToElement(webElement).perform();
//        return "true";
//    }
//
//    /**
//     * 判断对应元素是否存在
//     * @param xpath 元素xpath
//     * @return 存在返回true
//     */
//    private Boolean isElementExist(String xpath) {
//        List<WebElement> webElementList = getElements(xpath);
//        return webElementList.size() > 0;
//    }
//
//
//    /**
//     * 切换到指定标签页
//     * @param index 数组下标，0是初始页，1是新标签页
//     */
//    public void switchToWindow(Integer index) {
//        Set<String> windows = driver.getWindowHandles();
//        driver.switchTo().window(windows.toArray()[index].toString());
//    }
//
//    /**
//     * 删除其他标签页，并切换到第0个标签页
//     */
//    public void closeOtherWindow() {
//        Set<String> windows = driver.getWindowHandles();
//        for (int i = 1; i < windows.size(); i++) {
//            switchToWindow(i);
//            driver.close();
//        }
//        switchToWindow(0);
//    }
//
//    /**
//     * 删除浏览器cookies
//     */
//    public void deleteCookies() {
//        driver.manage().deleteAllCookies();
//    }
//
//
//    public void execJs(String jsExecString) {
//        ((JavascriptExecutor)driver).executeScript(jsExecString, new Object[0]);
//    }


}
