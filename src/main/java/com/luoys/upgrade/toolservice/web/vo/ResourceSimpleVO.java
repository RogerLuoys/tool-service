package com.luoys.upgrade.toolservice.web.vo;

import lombok.Data;

/**
 * 简单的资源类，用于列表展示
 *
 * @author luoys
 */
@Deprecated
@Data
public class ResourceSimpleVO {

    /**
     * 业务id
     */
    private Integer resourceId;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 资源类型：1 数据库，2 设备，3 测试环境，4 从节点
     */
    private Integer type;

    /**
     * 查看权限：1 公开，2 仅自己可见合
     */
    private Integer permission;

    /**
     * 所属人id
     */
    private Integer ownerId;

    /**
     * 所属人昵称
     */
    private String ownerName;

//    /**
//     * 使用人id
//     */
//    private Integer userId;
//
//    /**
//     * 使用人昵称
//     */
//    private String userName;

}
