package com.luoys.upgrade.toolservice.service.transform;

import com.luoys.upgrade.toolservice.dao.po.AutoCasePO;
import com.luoys.upgrade.toolservice.web.vo.AutoCaseSimpleVO;
import com.luoys.upgrade.toolservice.web.vo.AutoCaseVO;

import java.util.ArrayList;
import java.util.List;

public class TransformAutoCase {


    public static AutoCaseSimpleVO transformPO2SimpleVO(AutoCasePO po) {
        if (po == null) {
            return null;
        }
        AutoCaseSimpleVO vo = new AutoCaseSimpleVO();
        vo.setDescription(po.getDescription());
        vo.setOwnerId(po.getOwnerId());
        vo.setName(po.getName());
        vo.setCaseId(po.getId());
        vo.setStatus(po.getStatus());
        vo.setMaxTime(po.getMaxTime());
        vo.setOwnerName(po.getOwnerName());
        return vo;
    }

    public static List<AutoCaseSimpleVO> transformPO2SimpleVO(List<AutoCasePO> poList) {
        List<AutoCaseSimpleVO> voList = new ArrayList<>();
        for (AutoCasePO po : poList) {
            voList.add(transformPO2SimpleVO(po));
        }
        return voList;
    }

    public static AutoCaseVO transformPO2VO(AutoCasePO po) {
        if (po == null) {
            return null;
        }
        AutoCaseVO vo = new AutoCaseVO();
        // 设置基本信息
        vo.setName(po.getName());
        vo.setDescription(po.getDescription());
        vo.setOwnerId(po.getOwnerId());
        vo.setOwnerName(po.getOwnerName());
        vo.setCaseId(po.getId());
        vo.setStatus(po.getStatus());
        vo.setMaxTime(po.getMaxTime());
        // 设置步骤信息
        vo.setPreStepList(TransformCommon.toStep(po.getPreStep()));
        vo.setMainStepList(TransformCommon.toStep(po.getMainStep()));
        vo.setAfterStepList(TransformCommon.toStep(po.getAfterStep()));
        return vo;
    }

    public static AutoCasePO transformVO2PO(AutoCaseVO vo) {
        if (vo == null) {
            return null;
        }
        AutoCasePO po = new AutoCasePO();
        // 设置基本信息
        po.setName(vo.getName());
        po.setDescription(vo.getDescription());
        po.setOwnerId(vo.getOwnerId());
        po.setOwnerName(vo.getOwnerName());
        po.setId(vo.getCaseId());
        po.setStatus(vo.getStatus());
        po.setMaxTime(vo.getMaxTime());
        // 设置步骤信息
        po.setPreStep(TransformCommon.toStep(vo.getPreStepList()));
        po.setMainStep(TransformCommon.toStep(vo.getMainStepList()));
        po.setAfterStep(TransformCommon.toStep(vo.getAfterStepList()));
        return po;
    }

}
