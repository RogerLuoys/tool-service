package com.luoys.upgrade.toolservice.controller.vo;

import lombok.Data;

import java.util.List;

@Data
public class ToolVO {

    private Long toolId;
    private String title;
    private String description;
    private Integer type;
    private Integer permission;
    private String owner;
}
