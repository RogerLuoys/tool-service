package com.luoys.upgrade.toolservice.service.client;

import com.luoys.upgrade.toolservice.service.dto.AssertionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 断言客户端，用于自动化步骤断言类型的实现
 */
@Slf4j
@Component
public class AssertionClient {

    public synchronized Boolean execute(AssertionDTO assertionDTO) {
        return null;
    }
}
