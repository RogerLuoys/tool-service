package com.luoys.upgrade.toolservice.dao.po;

import lombok.Data;

/**
 * auto_step
 *
 * @author luoys
 */
@Data
public class AutoStepPO {

    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 模块类型：1 PO，2 SQL，3 HTTP，4 RPC，5 UI，6 ASSERT，7 UTIL
     */
    private Integer moduleType;

    /**
     * 方法类型，含义跟module_type有关
     */
    private Integer methodType;

    /**
     * 方法名，当module_type=1或7时，分别对应resource.name和auto_case.name
     */
    private String methodName;

    /**
     * 方法关联的id，当module_type=1或7时，分别对应resource.id和auto_case.id
     */
    private Integer methodId;

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
     * 执行结果
     */
    private String result;

}
