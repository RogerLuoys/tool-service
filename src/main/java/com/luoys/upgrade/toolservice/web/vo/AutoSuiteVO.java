package com.luoys.upgrade.toolservice.web.vo;

import com.luoys.upgrade.toolservice.service.dto.SlaveDTO;
import lombok.Data;

import java.util.List;

/**
 * 全量的套件类
 *
 * @author luoys
 */
@Data
public class AutoSuiteVO {

    /**
     * 业务id
     */
    private Integer suiteId;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 1 localhost，2 任意机器，3指定机器
     */
    private Integer slaveType;

    /**
     * slaveList
     */
    private List<SlaveDTO> slaveList;

    /**
     * 套件状态：1 空闲，2 执行中，3 排队中
     */
    private Integer status;

    /**
     * 套件执行超时时间
     */
    private Integer timeOut;

    /**
     * 用例执行超时时间
     */
    private Integer caseTimeOut;

    /**
     * 用例总数
     */
    private Integer total;

    /**
     * 成功用例数
     */
    private Integer passed;

    /**
     * 失败用例数
     */
    private Integer failed;

    /**
     * 所属项目id
     */
    private Integer projectId;

    /**
     * 所属责任人id
     */
    private Integer ownerId;

    /**
     * 所属人昵称
     */
    private String ownerName;

    /**
     * 用例列表，PageInfo类型，通过getList取列表
     */
    private PageInfo<SuiteCaseVO> relatedCase;

    /**
     * 是否重试(仅执行时使用)
     */
    private Boolean retry;
}
