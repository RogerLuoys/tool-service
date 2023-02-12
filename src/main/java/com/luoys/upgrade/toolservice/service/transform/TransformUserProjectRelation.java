package com.luoys.upgrade.toolservice.service.transform;

import com.luoys.upgrade.toolservice.dao.po.AutoCasePO;
import com.luoys.upgrade.toolservice.dao.po.UserProjectRelationPO;
import com.luoys.upgrade.toolservice.web.vo.AutoCaseSimpleVO;
import com.luoys.upgrade.toolservice.web.vo.MemberVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目成员数据转换
 *
 * @author luoys
 */
public class TransformUserProjectRelation {

    public static UserProjectRelationPO transformVO2PO(MemberVO vo) {
        if (vo == null) {
            return null;
        }
        UserProjectRelationPO po = new UserProjectRelationPO();
        po.setProjectId(vo.getProjectId());
        po.setUserId(vo.getUserId());
        po.setType(vo.getType());
        return po;
    }


    public static MemberVO transformPO2VO(UserProjectRelationPO po) {
        if (po == null) {
            return null;
        }
        MemberVO vo = new MemberVO();
        vo.setProjectId(po.getProjectId());
        vo.setUserId(po.getUserId());
        vo.setType(po.getType());
        vo.setNickname(po.getNickname());
        vo.setUsername(po.getUsername());
        return vo;
    }

    public static List<MemberVO> transformPO2VO(List<UserProjectRelationPO> poList) {
        List<MemberVO> voList = new ArrayList<>();
        for (UserProjectRelationPO po : poList) {
            voList.add(transformPO2VO(po));
        }
        return voList;
    }

}
