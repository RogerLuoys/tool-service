package com.luoys.upgrade.toolservice.service.transform;

import com.luoys.upgrade.toolservice.dao.po.CaseStepRelationPO;
import com.luoys.upgrade.toolservice.web.vo.CaseStepVO;

import java.util.ArrayList;
import java.util.List;

public class TransformCaseStepRelation {

    public static CaseStepVO transformPO2SimpleVO(CaseStepRelationPO po) {
        if (po == null) {
            return null;
        }
        CaseStepVO vo = new CaseStepVO();
        vo.setStepId(po.getStepId());
        vo.setSequence(po.getSequence());
        vo.setType(po.getType());
//        vo.setName(po.getCaseName());
//        vo.setAssertType(po.getCaseAssertType());
//        vo.setActualResult(po.getCaseActualResult());
//        vo.setExpectResult(po.getCaseExpectResult());
        return vo;
    }

    public static List<CaseStepVO> transformPO2SimpleVO(List<CaseStepRelationPO> poList) {
        List<CaseStepVO> voList = new ArrayList<>();
        for (CaseStepRelationPO po : poList) {
            voList.add(transformPO2SimpleVO(po));
        }
        return voList;
    }

    public static CaseStepRelationPO transformVO2SimplePO(CaseStepVO vo) {
        if (vo == null) {
            return null;
        }
        CaseStepRelationPO po = new CaseStepRelationPO();
        po.setStepId(vo.getStepId());
        po.setSequence(vo.getSequence());
        po.setType(vo.getType());
//        po.setCaseName(vo.getName());
//        po.setCaseAssertType(vo.getAssertType());
//        po.setCaseActualResult(vo.getActualResult());
//        po.setCaseExpectResult(vo.getExpectResult());
        return po;
    }

}
