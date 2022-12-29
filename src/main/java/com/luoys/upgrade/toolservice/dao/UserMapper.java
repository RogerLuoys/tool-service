package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.UserPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户dao层接口
 *
 * @author luoys
 */
@Repository
public interface UserMapper {

    /**
     * 插入数据
     *
     * @param record 表对应的pojo对象
     * @return 插入成功为1
     */
    int insert(UserPO record);

    /**
     * 逻辑删除数据
     *
     * @param userId 业务id
     * @return 删除成功为1
     */
    int remove(@Param("userId") Integer userId);

    /**
     * 更新数据
     *
     * @param record 表对应的pojo对象
     * @return 成功为1
     */
    int update(UserPO record);

    /**
     * 用户登录
     *
     * @param username 登录账号
     * @param phone     手机号
     * @param passWord  登录密码
     * @return 账号信息
     */
    UserPO selectByLoginInfo(@Param("username") String username,
                             @Param("phone") String phone,
                             @Param("passWord") String passWord);

    /**
     * 通过登录账号查询
     *
     * @param username 登录账号
     * @return 账号信息
     */
    UserPO selectByLoginName(String username);

    /**
     * 通过业务id查询
     *
     * @param userId 业务id
     * @return 账号信息
     */
    UserPO selectById(Integer userId);

    /**
     * 通过其它条件查询
     *
     * @param record 账号信息
     * @return 账号信息
     */
    UserPO select(UserPO record);

    /**
     * 通过登录名或昵称查询
     *
     * @param name username或nickname
     * @return 账号信息
     */
    List<UserPO> listByName(@Param("name") String name);

}
