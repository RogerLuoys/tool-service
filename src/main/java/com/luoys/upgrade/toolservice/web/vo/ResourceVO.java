package com.luoys.upgrade.toolservice.web.vo;

import com.luoys.upgrade.toolservice.service.dto.DataSourceDTO;
import com.luoys.upgrade.toolservice.service.dto.DeviceDTO;
import com.luoys.upgrade.toolservice.service.dto.SlaveDTO;
import lombok.Data;

/**
 * 全量的资源类
 *
 * @author luoys
 */
@Data
public class ResourceVO {

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
     * 数据源
     */
    private DataSourceDTO dataSource;

    /**
     * 执行自动化的机器
     */
    private SlaveDTO slave;

    /**
     * 所属项目id
     */
    private Integer projectId;

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
