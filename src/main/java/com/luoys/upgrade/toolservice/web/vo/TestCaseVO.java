package com.luoys.upgrade.toolservice.web.vo;

import com.luoys.upgrade.toolservice.service.dto.ParamDTO;
import com.luoys.upgrade.toolservice.service.dto.StepDTO;
import lombok.Data;

import java.util.List;

@Data
public class TestCaseVO {
    private Integer testCaseId;

    private String title;

    private String description;

    private List<StepDTO> preStepList;

    private List<StepDTO> mainStepList;

    private List<StepDTO> afterStepList;

    private String ownerId;

    private String ownerName;

    private Integer status;

}
