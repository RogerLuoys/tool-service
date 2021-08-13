package com.luoys.upgrade.toolservice.service.transform;

import com.luoys.upgrade.toolservice.dao.po.SuiteCaseRelationPO;
import com.luoys.upgrade.toolservice.web.vo.SuiteCaseVO;

import java.util.ArrayList;
import java.util.List;

public class TransformSuiteCaseRelation {

    public static SuiteCaseVO transformPO2SimpleVO(SuiteCaseRelationPO po) {
        if (po == null) {
            return null;
        }
        SuiteCaseVO vo = new SuiteCaseVO();
        vo.setCaseId(po.getCaseId());
        vo.setSequence(po.getSequence());
        vo.setType(po.getCaseType());
        vo.setName(po.getCaseName());
        vo.setStatus(po.getCaseStatus());
        return vo;
    }

    public static List<SuiteCaseVO> transformPO2SimpleVO(List<SuiteCaseRelationPO> poList) {
        List<SuiteCaseVO> voList = new ArrayList<>();
        for (SuiteCaseRelationPO po : poList) {
            voList.add(transformPO2SimpleVO(po));
        }
        return voList;
    }

    public static SuiteCaseRelationPO transformVO2SimplePO(SuiteCaseVO vo) {
        if (vo == null) {
            return null;
        }
        SuiteCaseRelationPO po = new SuiteCaseRelationPO();
        po.setCaseId(vo.getCaseId());
        po.setSequence(vo.getSequence());
        po.setCaseType(vo.getType());
        po.setCaseName(vo.getName());
        po.setCaseStatus(vo.getStatus());
        return po;
    }

}
