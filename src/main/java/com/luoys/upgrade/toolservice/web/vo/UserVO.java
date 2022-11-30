package com.luoys.upgrade.toolservice.web.vo;

import lombok.Data;

@Data
public class UserVO {

    /**
     * uuid，即owner_id
     */
    private Integer userId;

    /**
     * 登录名
     */
    private String username;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 手机号码
     */
    private String phone;

//    /**
//     * 用户昵称
//     */
//    private String userName;

    /**
     * 1--管理员；2--普通账号
     */
    private Integer type;

    /**
     * 用户昵称
     */
    private String nickname;

//    /**
//     * 1-正常；
//     */
//    private Integer status;

}
