package com.luoys.upgrade.toolservice.service.client;

import com.luoys.upgrade.toolservice.service.dto.StepDTO;
import com.luoys.upgrade.toolservice.service.enums.autoStep.methodType.AssertionEnum;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * 断言客户端，用于自动化步骤断言类型的实现
 */
@Slf4j
//@Component
public class AssertionClient {

    private UiClient ui;

    public AssertionClient() {}

    public AssertionClient (UiClient ui) {
        this.ui = ui;
    }

//    /**
//     * 执行断言
//     *
//     * @param autoStepPO 断言对象
//     * @return 成功返回true，失败返回false
//     */
//    public Boolean execute(AutoStepPO autoStepPO) {
//        switch (AssertionEnum.fromCode(autoStepPO.getModuleType())) {
//            case IS_EQUALS:
//                return isEquals(autoStepPO.getParameter1(), autoStepPO.getParameter2());
//            case IS_CONTAINS:
//                return isContains(autoStepPO.getParameter1(), autoStepPO.getParameter2());
//            case IS_BE_CONTAINS:
//                return isBeContains(autoStepPO.getParameter1(), autoStepPO.getParameter2());
//            case IS_DELETED:
//                return isDeleted(autoStepPO.getParameter1());
//            case IS_NOT_DELETED:
//                return isNotDeleted(autoStepPO.getParameter1());
//            case IS_GREATER:
//                return isGreater(autoStepPO.getParameter1(), autoStepPO.getParameter2());
//            case IS_SMALLER:
//                return isSmaller(autoStepPO.getParameter1(), autoStepPO.getParameter2());
//            case IS_XPATH_EXIST:
//                return isXpathExist(autoStepPO.getParameter1());
//            case IS_XPATH_NOT_EXIST:
//                return isXpathNotExist(autoStepPO.getParameter1());
//            default:
//                return false;
//        }
//    }

    /**
     * 执行断言
     *
     * @param stepDTO 断言对象
     * @return 成功返回true，失败返回false
     */
    public String execute(StepDTO stepDTO) {
        switch (AssertionEnum.fromCode(stepDTO.getMethodType())) {
            case IS_EQUALS:
                return isEquals(stepDTO.getParameter1(), stepDTO.getParameter2()).toString();
            case IS_CONTAINS:
                return isContains(stepDTO.getParameter1(), stepDTO.getParameter2()).toString();
            case IS_BE_CONTAINS:
                return isBeContains(stepDTO.getParameter1(), stepDTO.getParameter2()).toString();
            case IS_DELETED:
                return isDeleted(stepDTO.getParameter1()).toString();
            case IS_NOT_DELETED:
                return isNotDeleted(stepDTO.getParameter1()).toString();
            case IS_GREATER:
                return isGreater(stepDTO.getParameter1(), stepDTO.getParameter2()).toString();
            case IS_SMALLER:
                return isSmaller(stepDTO.getParameter1(), stepDTO.getParameter2()).toString();
            case IS_XPATH_EXIST:
                return isXpathExist(stepDTO.getParameter1()).toString();
            case IS_XPATH_NOT_EXIST:
                return isXpathNotExist(stepDTO.getParameter1()).toString();
            default:
                return "false";
        }
    }

    /**
     * 校验actual是否存包含了expect
     *
     * @param actual 实际结果
     * @param expect 预期结果
     */
    private Boolean isContains(String actual, String expect) {
        return actual.contains(expect);
    }

    /**
     * 校验expect是否存包含了actual
     *
     * @param actual 实际结果
     * @param expect 预期结果
     */
    private Boolean isBeContains(String actual, String expect) {
        return expect.contains(actual);
    }

    /**
     * 校验字符串是否相等
     *
     * @param actual 实际结果
     * @param expect 预期结果
     */
    private Boolean isEquals(String actual, String expect) {
        return actual.equals(expect);
    }

    /**
     * 校验数据是否被逻辑删除，删除则校验通过，未删除则校验失败
     *
     * @param actual 实际结果，0或false表示未删除
     */
    private Boolean isDeleted(String actual) {
        if (actual.equalsIgnoreCase("0") || actual.equalsIgnoreCase("false")) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 校验数据是否未被逻辑删除，删除则校验失败，未删除则校验通过
     *
     * @param actual 实际结果，0或false表示未删除
     */
    private Boolean isNotDeleted(String actual) {
        if (actual.equalsIgnoreCase("0") || actual.equalsIgnoreCase("false")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 校验实际结果是否大于预期结果
     *
     * @param actual 实际结果，需要是数字字符
     * @param expect 预期结果，需要是数字字符
     */
    private Boolean isGreater(String actual, String expect) {
        double actual1 = Double.parseDouble(actual);
        double expect1 = Double.parseDouble(expect);
        return actual1 > expect1;
    }

    /**
     * 校验实际结果是否小于预期结果
     *
     * @param actual 实际结果，需要是数字字符
     * @param expect 预期结果，需要是数字字符
     */
    private Boolean isSmaller(String actual, String expect) {
        double actual1 = Double.parseDouble(actual);
        double expect1 = Double.parseDouble(expect);
        return actual1 < expect1;

    }

    /**
     * 判断指定元素是否存在 (UI专用)
     * 如果找到多个元素，也算成功
     *
     * @param xpath 元素的xpath
     */
    private Boolean isXpathExist(String xpath) {
        if (this.ui.getDriver() == null) {
            return false;
        }
        List<WebElement> webElements = this.ui.getElements(xpath);
        return webElements.size() > 0;
    }

    /**
     * 判断指定元素是否存在 (UI专用)
     * xpath格式不对导致找不到，也算成功
     *
     * @param xpath 元素的xpath
     */
    private Boolean isXpathNotExist(String xpath) {
        if (this.ui.getDriver() == null) {
            return false;
        }
        List<WebElement> webElements = this.ui.getElements(xpath);
        return webElements.size() == 0;
    }

//    private Boolean isContains(String actual, String expect) {
//        return actual.contains(expect);
//    }
//
//    private Boolean isEquals(String actual, String expect) {
//        return actual.equals(expect);
//    }
//
//    private Boolean isTrue(String actual) {
//        return actual.equalsIgnoreCase("true");
//    }
//
//    private Boolean isFalse(String actual) {
//        return actual.equalsIgnoreCase("false");
//    }
//
//    /**
//     * 判断页面元素是否存在
//     * 如果跟进xpath找到的元素大于0个，则为存在
//     *
//     * @param xpath 元素对应的xpath
//     * @return 存在为true，不存在为false
//     */
//    private Boolean isElementExist(String xpath) {
//        List<WebElement> elements = AutomationBase.getChromeDriver().findElements(By.xpath(xpath));
//        return elements.size() > 0;
//    }
//
//    /**
//     * 判断页面元素是否不存在
//     * 如果跟进xpath找到的元素等于0个，则为不存在
//     *
//     * @param xpath 元素对应的xpath
//     * @return 存在为true，不存在为false
//     */
//    private Boolean isElementNotExist(String xpath) {
//        List<WebElement> elements = AutomationBase.getChromeDriver().findElements(By.xpath(xpath));
//        return elements.size() == 0;
//    }
}
