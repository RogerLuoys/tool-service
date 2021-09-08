package com.luoys.upgrade.toolservice.web.vo;

import lombok.Data;

@Data
public class UserVO {

    private Integer id;

    /**
     * uuid，即owner_id
     */
    private String userId;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 登录密码
     */
    private String passWord;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 1--管理员；2--普通账号
     */
    private Integer type;

    /**
     * 1-正常；
     */
    private Integer status;

}
