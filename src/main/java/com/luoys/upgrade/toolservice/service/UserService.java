package com.luoys.upgrade.toolservice.service;

import com.luoys.upgrade.toolservice.common.NumberSender;
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
    private final static String SUCCESS = "成功";

    @Autowired
    private UserMapper userMapper;

    public Boolean update(UserVO userVO) {
        if (userVO == null) {
            return null;
        }
        int result = userMapper.update(TransformUser.transformBO2PO(userVO));
        return  result == 1;
    }

    public UserVO queryByLoginName(String loginName, String passWord) {
        if (loginName == null || passWord == null) {
            return null;
        }
        return TransformUser.transformPO2BO(userMapper.selectByLoginInfo(loginName, null, passWord));
    }

    public UserVO queryByPhone(String phone, String passWord) {
        if (phone == null || passWord == null) {
            return null;
        }
        return TransformUser.transformPO2BO(userMapper.selectByLoginInfo(null, phone, passWord));
    }

    public UserVO queryByUserId(String userId) {
        if (null == userId) {
            return null;
        }
        return TransformUser.transformPO2BO(userMapper.selectByUUId(userId));
    }

    public Boolean checkUserExist(String loginName) {
        if (loginName == null) {
            log.error("----》入参为空");
            return null;
        }
        return null != userMapper.selectByLoginName(loginName);
    }

    public UserVO newUser(UserVO userVO) {
        if (userVO == null || userVO.getLoginName() == null || userVO.getPassWord() == null) {
            log.error("----》入参为空");
            return null;
        }
        if (userVO.getUserName() == null) {
            userVO.setUserName(KeywordEnum.DEFAULT_USER.getValue());
        }
        if (userVO.getType() == null) {
            userVO.setType(UserTypeEnum.REGULAR.getCode());
        }
        if (userVO.getStatus() == null) {
            userVO.setStatus(DEFAULT_USER_STATUS);
        }
        userVO.setUserId(NumberSender.createUserId());
        log.info("====》新增用户：{}", userVO);
        int insertUserResult = userMapper.insert(TransformUser.transformBO2PO(userVO));
        return insertUserResult == 1 ? userVO : null;
    }

}
