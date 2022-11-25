package com.luoys.upgrade.toolservice.service.dto;

import lombok.Data;

import java.util.List;

/**
 * http对象
 *
 * @author luoys
 */
@Data
public class HttpDTO {

    private String httpType;
    private String httpURL;
    private List<ParameterDTO> httpHeaderList;
    private String httpBody;

}
