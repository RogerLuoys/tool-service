package com.luoys.upgrade.toolservice.service.transform;

import com.luoys.upgrade.toolservice.dao.po.UserPO;
import com.luoys.upgrade.toolservice.web.vo.UserVO;

import java.util.ArrayList;
import java.util.List;

public class TransformUser {

    public static UserPO transformVO2PO(UserVO vo) {
        if (vo == null) {
            return null;
        }
        UserPO po = new UserPO();
//        po.setStatus(vo.getStatus());
        po.setId(vo.getUserId());
        po.setUsername(vo.getUsername());
        po.setType(vo.getType());
        po.setPhone(vo.getPhone());
        po.setNickname(vo.getNickname());
        po.setPassword(vo.getPassword());
        po.setLoginInfo(vo.getLoginInfo());
        return po;
    }

    public static UserVO transformPO2VO(UserPO po) {
        if (po == null) {
            return null;
        }
        UserVO vo = new UserVO();
        vo.setPassword(po.getPassword());
        vo.setUserId(po.getId());
        vo.setUsername(po.getUsername());
        vo.setPhone(po.getPhone());
        vo.setType(po.getType());
        vo.setLoginInfo(po.getLoginInfo());
        vo.setNickname(po.getNickname());
        return vo;
    }


    public static List<UserVO> transformPO2VO(List<UserPO> poList) {
        List<UserVO> voList = new ArrayList<>();
        for (UserPO po : poList) {
            voList.add(transformPO2VO(po));
        }
        return voList;
    }

}
