package com.luoys.upgrade.toolservice.web.vo;

import lombok.Data;

@Data
public class CommonQuery {
    private Integer type;
    private String name;
    private Integer pageIndex;
    private Boolean isTestStep;
}
