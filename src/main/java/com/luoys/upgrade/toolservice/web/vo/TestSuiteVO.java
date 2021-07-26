package com.luoys.upgrade.toolservice.web.vo;

import com.luoys.upgrade.toolservice.service.dto.ParamDTO;
import lombok.Data;

import java.util.List;

@Data
public class TestSuiteVO {
    private Integer testSuiteId;

    private String title;

    private String description;

    private List<ParamDTO> configList;

    private List<ParamDTO> caseList;

    private Integer passed;

    private Integer failed;

}
