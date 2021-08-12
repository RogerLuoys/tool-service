package com.luoys.upgrade.toolservice.service.transform;

import com.luoys.upgrade.toolservice.dao.po.ResourcePO;
import com.luoys.upgrade.toolservice.dao.po.SuiteCaseRelationPO;
import com.luoys.upgrade.toolservice.service.dto.CaseDTO;
import com.luoys.upgrade.toolservice.web.vo.ResourceSimpleVO;
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

}
