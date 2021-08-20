package com.luoys.upgrade.toolservice.service.transform;

import com.luoys.upgrade.toolservice.dao.po.SuiteCaseRelationPO;
import com.luoys.upgrade.toolservice.web.vo.AutoCaseSimpleVO;
import com.luoys.upgrade.toolservice.web.vo.SuiteCaseVO;

import java.util.ArrayList;
import java.util.List;

public class TransformSuiteCaseRelation {

    public static SuiteCaseVO transformPO2SimpleVO(SuiteCaseRelationPO po) {
        if (po == null) {
            return null;
        }
        SuiteCaseVO vo = new SuiteCaseVO();
        vo.setSequence(po.getSequence());
        vo.setCaseId(po.getCaseId());
        vo.setStatus(po.getStatus());
        // 设置用例信息
        AutoCaseSimpleVO childVO = new AutoCaseSimpleVO();
        childVO.setName(po.getCaseName());
        childVO.setCaseId(po.getCaseId());
        childVO.setStatus(po.getCaseStatus());
        childVO.setType(po.getCaseType());
        vo.setAutoCase(childVO);
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
        po.setSuiteId(vo.getSuiteId());
        po.setSequence(vo.getSequence());
        po.setStatus(vo.getStatus());
        return po;
    }

}
