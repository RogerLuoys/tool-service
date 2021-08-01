package com.luoys.upgrade.toolservice.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class HttpRequestDTO {

    private String httpType;
    private String httpURL;
    private List<ParameterDTO> httpHeaderList;
    private String httpBody;
}
