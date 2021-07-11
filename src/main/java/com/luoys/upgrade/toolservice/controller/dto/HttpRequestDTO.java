package com.luoys.upgrade.toolservice.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class HttpRequestDTO {

    private Integer httpType;
    private String httpURL;
    private List<ParamDTO> httpHeaderList;
    private String httpBody;
}
