package com.luoys.upgrade.toolservice.dao.po;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * user
 * @author luoys
 */
@Data
public class UserPO {

    private Integer id;

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

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 1--管理员；2--普通账号
     */
    private Integer type;

    /**
     * 登录信息(密文)
     */
    private String loginInfo;

}
