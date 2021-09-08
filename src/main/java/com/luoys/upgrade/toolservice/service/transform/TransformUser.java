package com.luoys.upgrade.toolservice.service.transform;

import com.luoys.upgrade.toolservice.dao.po.UserPO;
import com.luoys.upgrade.toolservice.web.vo.UserVO;

public class TransformUser {

    public static UserPO transformBO2PO(UserVO vo) {
        if (vo == null) {
            return null;
        }
        UserPO po = new UserPO();
        po.setStatus(vo.getStatus());
        po.setUserId(vo.getUserId());
        po.setLoginName(vo.getLoginName());
        po.setType(vo.getType());
        po.setPhone(vo.getPhone());
        po.setUserName(vo.getUserName());
        po.setPassWord(vo.getPassWord());
        return po;
    }

    public static UserVO transformPO2BO(UserPO po) {
        if (po == null) {
            return null;
        }
        UserVO vo = new UserVO();
        vo.setPassWord(po.getPassWord());
        vo.setUserId(po.getUserId());
        vo.setUserName(po.getUserName());
        vo.setPhone(po.getPhone());
        vo.setStatus(po.getStatus());
        vo.setType(po.getType());
        vo.setLoginName(po.getLoginName());
        return vo;
    }
}
