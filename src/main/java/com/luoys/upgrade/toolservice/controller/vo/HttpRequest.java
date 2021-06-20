package com.luoys.upgrade.toolservice.controller.vo;

import lombok.Data;

import java.util.List;

@Data
public class HttpRequest {

    private String httpType;
    private String httpURL;
    private List<ParamVO> httpHeaderList;
    private String httpBody;
}
