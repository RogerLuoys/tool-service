package com.luoys.upgrade.toolservice.service.client;

import com.luoys.upgrade.toolservice.service.dto.AssertionDTO;
import com.luoys.upgrade.toolservice.service.enums.AssertTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 断言客户端，用于自动化步骤断言类型的实现
 */
@Slf4j
@Component
public class AssertionClient {

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

    private Boolean isElementExist(String xpath) {
        return null;
    }

    private Boolean isElementNotExist(String xpath) {
        return null;
    }
}
