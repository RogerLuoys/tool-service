package com.luoys.upgrade.toolservice.service.client;

import com.luoys.upgrade.toolservice.service.dto.AssertionDTO;
import com.luoys.upgrade.toolservice.service.enums.AssertTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 断言客户端，用于自动化步骤断言类型的实现
 */
@Slf4j
@Component
public class AssertionClient {

    /**
     * 执行断言
     *
     * @param assertionDTO 断言对象
     * @return 成功返回true，失败返回false
     */
    public synchronized Boolean execute(AssertionDTO assertionDTO) {
        switch (AssertTypeEnum.fromCode(assertionDTO.getType())) {
            case EQUALS:
                return isEquals(assertionDTO.getActual(), assertionDTO.getExpect());
            case CONTAINS:
                return isContains(assertionDTO.getActual(), assertionDTO.getExpect());
            case IS_TRUE:
                return isTrue(assertionDTO.getActual());
            case IS_FALSE:
                return isFalse(assertionDTO.getActual());
            case IS_ELEMENT_EXIST:
                return isElementExist(assertionDTO.getActual());
            case IS_ELEMENT_NOT_EXIST:
                return isElementNotExist(assertionDTO.getActual());
            default:
                return false;
        }
    }

    private Boolean isContains(String actual, String expect) {
        return actual.contains(expect);
    }

    private Boolean isEquals(String actual, String expect) {
        return actual.equals(expect);
    }

    private Boolean isTrue(String actual) {
        return actual.equalsIgnoreCase("true");
    }

    private Boolean isFalse(String actual) {
        return actual.equalsIgnoreCase("false");
    }

    /**
     * 判断页面元素是否存在
     * 如果跟进xpath找到的元素大于0个，则为存在
     *
     * @param xpath 元素对应的xpath
     * @return 存在为true，不存在为false
     */
    private Boolean isElementExist(String xpath) {
        List<WebElement> elements = AutomationBase.getChromeDriver().findElements(By.xpath(xpath));
        return elements.size() > 0;
    }

    /**
     * 判断页面元素是否不存在
     * 如果跟进xpath找到的元素等于0个，则为不存在
     *
     * @param xpath 元素对应的xpath
     * @return 存在为true，不存在为false
     */
    private Boolean isElementNotExist(String xpath) {
        List<WebElement> elements = AutomationBase.getChromeDriver().findElements(By.xpath(xpath));
        return elements.size() == 0;
    }
}
