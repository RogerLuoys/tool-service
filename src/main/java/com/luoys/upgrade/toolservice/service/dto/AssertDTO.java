package com.luoys.upgrade.toolservice.service.dto;

import lombok.Data;

/**
 * 断言类，用于自动化断言
 *
 * @author luoys
 */
@Data
public class AssertDTO {
    private Integer type;

    private String actual;

    private String expect;

    private Boolean result;
}
