package com.luoys.upgrade.toolservice.service.transform;

import com.luoys.upgrade.toolservice.dao.po.UserProjectRelationPO;
import com.luoys.upgrade.toolservice.web.vo.MemberVO;

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
}
