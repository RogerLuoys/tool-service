package com.luoys.upgrade.toolservice.dao.po;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * auto_suite
 *
 * @author luoys
 */
@Data
public class AutoSuitePO {

    /**
     * 业务id
     */
    private Integer id;

//    /**
//     * 业务id
//     */
//    private String suiteId;

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
     * 套件状态：1 空闲，2 执行中，3 排队中
     */
    private Integer status;

//    /**
//     * 用例执行的环境
//     */
//    private String resource;

//    /**
//     * 入参列表，List<CommonDTO>类型
//     */
//    private String parameter;

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

//    /**
//     * ui用例是否执行完成
//     */
//    private Boolean isUiCompleted;
//
//    /**
//     * api用例是否执行完成
//     */
//    private Boolean isApiCompleted;

}
