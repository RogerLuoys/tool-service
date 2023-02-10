package com.luoys.upgrade.toolservice.service.dto;


import com.luoys.upgrade.toolservice.web.vo.PageInfo;
import com.luoys.upgrade.toolservice.web.vo.SuiteCaseVO;
import lombok.Data;

import java.util.List;

/**
 * 可执行的套件
 */
@Data
public class SuiteDTO {

    /**
     * 业务id
     */
    private Integer suiteId;

    private Integer resourceId;

    /**
     * 1 localhost，2 任意机器，3指定机器
     */
    private Integer slaveType;

    /**
     * slaveList
     */
    private List<SlaveDTO> slaveList;

    /**
     * 所属项目id
     */
    private Integer projectId;

    /**
     * 待执行的用例列表
     */
    private List<SuiteCaseVO> relatedCase;

    /**
     * 是否重试(仅执行时使用)
     */
    private Boolean retry;
}
