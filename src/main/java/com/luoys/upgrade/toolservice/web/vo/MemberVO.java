package com.luoys.upgrade.toolservice.web.vo;

import lombok.Data;

@Data
public class MemberVO {

    /**
     * 项目id
     */
    private Integer projectId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 登录名
     */
    private String username;

    /**
     * 1--管理员；2--成员；3--项目负责人
     */
    private Integer type;

    /**
     * 用户昵称
     */
    private String nickname;

}
