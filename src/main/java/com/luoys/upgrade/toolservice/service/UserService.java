package com.luoys.upgrade.toolservice.service;

import com.luoys.upgrade.toolservice.dao.po.UserPO;
import com.luoys.upgrade.toolservice.service.common.NumberSender;
import com.luoys.upgrade.toolservice.dao.UserMapper;
import com.luoys.upgrade.toolservice.service.enums.KeywordEnum;
import com.luoys.upgrade.toolservice.service.enums.UserTypeEnum;
import com.luoys.upgrade.toolservice.service.transform.TransformUser;
import com.luoys.upgrade.toolservice.web.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务，包含用户相关的所有业务逻辑
 *
 * @author luoys
 */
@Slf4j
@Service
public class UserService {


    private final static int DEFAULT_USER_STATUS = 1;

    @Autowired
    private UserMapper userMapper;

    /**
     * 更新用户信息
     * @param userVO 用户对象
     * @return 更新成功为true
     */
    public Boolean update(UserVO userVO) {
        if (userVO == null) {
            return null;
        }
        int result = userMapper.update(TransformUser.transformVO2PO(userVO));
        return  result == 1;
    }

    /**
     * 根据账号密码查询用户
     * @param loginName 账号
     * @param passWord 密码
     * @return 用户对象
     */
    public UserVO queryByLoginName(String loginName, String passWord) {
        if (loginName == null || passWord == null) {
            return null;
        }
        return TransformUser.transformPO2VO(userMapper.selectByLoginInfo(loginName, null, passWord));
    }

    /**
     * 根据手机号密码查询用户
     * @param phone 手机号
     * @param passWord 密码
     * @return 用户对象
     */
    public UserVO queryByPhone(String phone, String passWord) {
        if (phone == null || passWord == null) {
            return null;
        }
        return TransformUser.transformPO2VO(userMapper.selectByLoginInfo(null, phone, passWord));
    }

    /**
     * 根据业务id查询用户
     * @param userId 业务id
     * @return 用户对象
     */
    public UserVO queryByUserId(Integer userId) {
        if (null == userId) {
            return null;
        }
        return TransformUser.transformPO2VO(userMapper.selectById(userId));
    }

    /**
     * 判断账号是否存在
     * @param loginName 账号
     * @return 存在为
     */
    public Boolean checkUserExist(String loginName) {
        if (loginName == null) {
            log.error("--->校验入参为空");
            return null;
        }
        return null != userMapper.selectByLoginName(loginName);
    }

    /**
     * 创建用户
     * @param userVO 用户对象
     * @return 创建后的用户对象
     */
    public UserVO newUser(UserVO userVO) {
        if (userVO == null || userVO.getUsername() == null || userVO.getPassword() == null) {
            log.error("--->注册入参为空");
            return null;
        }
        if (userVO.getUsername() == null) {
            userVO.setUsername(KeywordEnum.DEFAULT_USER.getValue());
        }
        if (userVO.getType() == null) {
            userVO.setType(UserTypeEnum.REGULAR.getCode());
        }
//        if (userVO.getStatus() == null) {
//            userVO.setStatus(DEFAULT_USER_STATUS);
//        }
//        userVO.setUserId(NumberSender.createUserId());
        UserPO userPO = TransformUser.transformVO2PO(userVO);
        userMapper.insert(userPO);
        return TransformUser.transformPO2VO(userPO);
    }

}
