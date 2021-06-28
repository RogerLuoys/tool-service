package com.luoys.upgrade.toolservice.controller.vo;

import lombok.Data;

import java.util.List;

@Data
public class ToolDetailVO {
    private Long toolId;
    private String title;
    private String description;
    private Integer type;
    private Integer status;
    private String ownerId;
    private Integer permission;
    private List<String> sqlList;
    private HttpRequest httpRequest;
    private String rpcProvider;
    private List<ParamVO> paramList;
}
