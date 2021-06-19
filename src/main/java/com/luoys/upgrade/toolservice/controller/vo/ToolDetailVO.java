package com.luoys.upgrade.toolservice.controller.vo;

import lombok.Data;

import java.util.List;

@Data
public class ToolDetailVO {
    private Integer toolId;
    private String title;
    private String description;
    private Integer type;
    private Integer status;
    private Integer permission;
    private List<String> sqlList;
    private String rpcProvider;
    private String httpType;
    private String httpURL;
    private List<ParamVO> httpHeaderList;
    private String httpBody;
    private List<ParamVO> paramList;
}
