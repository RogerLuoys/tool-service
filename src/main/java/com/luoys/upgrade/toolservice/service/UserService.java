package com.luoys.upgrade.toolservice.service;

import com.luoys.upgrade.toolservice.dao.po.UserPO;
import com.luoys.upgrade.toolservice.dao.UserMapper;
import com.luoys.upgrade.toolservice.service.common.CacheUtil;
import com.luoys.upgrade.toolservice.service.enums.UserTypeEnum;
import com.luoys.upgrade.toolservice.service.transform.TransformUser;
import com.luoys.upgrade.toolservice.web.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

/**
 * 用户服务，包含用户相关的所有业务逻辑
 *
 * @author luoys
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 更新用户信息
     * @param userVO 用户对象
     * @return 更新成功为true
     */
    public Integer update(UserVO userVO) {
        if (userVO == null) {
            return null;
        }
        if (userVO.getPassword() != null) {
            userVO.setLoginInfo(encryptByMd5(userVO.getUsername(), userVO.getPassword()));
        }
        UserPO userPO = TransformUser.transformVO2PO(userVO);
        return  userMapper.update(userPO);
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
        UserPO userPO = userMapper.selectByAccountInfo(loginName, null, passWord);
        return TransformUser.transformPO2VO(userPO);
    }

    /**
     * 根据登录信息查询用户
     * @param loginInfo 登录信息(密文)
     * @return 用户对象
     */
    public UserVO queryByLoginInfo(String loginInfo) {
        if (loginInfo == null) {
            return null;
        }
        return CacheUtil.getUserByLoginInfo(loginInfo);
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
        UserPO userPO = userMapper.selectById(userId);
        return TransformUser.transformPO2VO(userPO);
    }

    /**
     * 根据登录名或昵称查询用户列表
     * @param name username或nickname
     * @return 用户列表
     */
    public List<UserVO> queryByName(String name) {
        if (null == name) {
            return null;
        }
        List<UserPO> userPOList = userMapper.listByName(name);
        return TransformUser.transformPO2VO(userPOList);
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
        if (userVO.getType() == null) {
            userVO.setType(UserTypeEnum.REGULAR.getCode());
        }
        userVO.setLoginInfo(encryptByMd5(userVO.getUsername(), userVO.getPassword()));
        UserPO userPO = TransformUser.transformVO2PO(userVO);
        userMapper.insert(userPO);
        return TransformUser.transformPO2VO(userPO);
    }

    /**
     * 账号信息加密
     * @return 密文
     */
    private String encryptByMd5(String username, String password) {
        String userInfo = username + password;
        return DigestUtils.md5DigestAsHex(userInfo.getBytes());
    }

}
