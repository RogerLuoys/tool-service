package com.luoys.upgrade.toolservice.controller.vo;

import lombok.Data;

import java.util.List;

@Data
public class ToolDetailVO {
    Integer toolId;
    String title;
    String description;
    Integer type;
    Integer status;
    List<String> sqlList;
    String httpType;
    String httpURL;
    List<ParamVO> httpHeaderList;
    String httpBody;
    List<ParamVO> paramList;
}
