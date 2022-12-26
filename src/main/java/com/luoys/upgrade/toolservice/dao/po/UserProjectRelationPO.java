package com.luoys.upgrade.toolservice.dao.po;

import lombok.Data;

/**
 * user_project_relation
 * @author luoys
 */
@Data
public class UserProjectRelationPO {
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 项目id
     */
    private Integer projectId;

    /**
     * 1 管理员，2 普通
     */
    private Integer type;

    /**
     * 登录名(账号)
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

}