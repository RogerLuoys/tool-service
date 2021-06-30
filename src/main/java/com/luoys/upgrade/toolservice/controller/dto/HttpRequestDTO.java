package com.luoys.upgrade.toolservice.controller.dto;

import com.luoys.upgrade.toolservice.controller.vo.ParamVO;
import lombok.Data;

import java.util.List;

@Data
public class HttpRequestDTO {

    private String httpType;
    private String httpURL;
    private List<ParamVO> httpHeaderList;
    private String httpBody;
}
