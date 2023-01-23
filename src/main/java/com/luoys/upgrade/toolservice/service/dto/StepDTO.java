package com.luoys.upgrade.toolservice.service.dto;

import lombok.Data;

/**
 * 聚合步骤类
 *
 * @author luoys
 */
@Data
public class StepDTO {

    private Integer stepId;

    /**
     * 模块类型：1 PO，2 SQL，3 HTTP，4 RPC，5 UI，6 ASSERT，7 UTIL
     */
    private Integer moduleType;

    /**
     * 方法类型，含义跟module_type有关
     */
    private Integer methodType;

//    /**
//     * 方法名，当module_type=1或2时，分别对应auto_case.name和resource.name
//     */
//    private String methodName;
//
//    /**
//     * 方法关联的id，当module_type=1或2时，分别对应auto_case.id和resource.id
//     */
//    private Integer methodId;

    /**
     * 执行sql的数据源
     */
    private DataSourceDTO dataSource;

    /**
     * 入参1
     */
    private String parameter1;

    /**
     * 入参2
     */
    private String parameter2;

    /**
     * 入参3
     */
    private String parameter3;

    /**
     * 变量名
     */
    private String varName;

    /**
     * 步骤执行结果
     */
    private String result;

}
