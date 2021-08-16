package com.luoys.upgrade.toolservice.service.dto;

import lombok.Data;

@Data
public class AssertDTO {
    private Integer type;

    private String actual;

    private String expect;

    private Boolean result;
}
