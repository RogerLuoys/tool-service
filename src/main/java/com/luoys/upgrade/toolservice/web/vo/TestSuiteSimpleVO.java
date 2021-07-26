package com.luoys.upgrade.toolservice.web.vo;

import lombok.Data;

@Data
public class TestSuiteSimpleVO {
    private Integer testSuiteId;

    private String title;

    private String description;

    private Integer passed;

    private Integer failed;

}
