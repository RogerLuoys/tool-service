package com.luoys.upgrade.toolservice.controller.vo;

import com.luoys.upgrade.toolservice.controller.dto.ParamDTO;
import lombok.Data;

import java.util.List;

@Data
public class CaseVO {
    private Integer caseId;

    private String title;

    private String description;

    private List<ParamDTO> preStepList;

    private List<ParamDTO> mainStepList;

    private List<ParamDTO> afterStepList;

    private String ownerId;

    private String ownerName;

}
