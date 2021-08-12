package com.luoys.upgrade.toolservice.service.transform;

import com.luoys.upgrade.toolservice.dao.po.AutoSuitePO;
import com.luoys.upgrade.toolservice.web.vo.AutoSuiteSimpleVO;
import com.luoys.upgrade.toolservice.web.vo.AutoSuiteVO;

import java.util.ArrayList;
import java.util.List;

public class TransformAutoSuite {

    public static AutoSuiteSimpleVO transformPO2SimpleVO(AutoSuitePO po) {
        if (po == null) {
            return null;
        }
        AutoSuiteSimpleVO vo = new AutoSuiteSimpleVO();
        vo.setDescription(po.getDescription());
        vo.setName(po.getName());
        vo.setFailed(po.getFailed());
        vo.setPassed(po.getPassed());
        vo.setSuiteId(po.getSuiteId());
        vo.setOwnerId(po.getOwnerId());
        vo.setOwnerName(po.getOwnerName());
        vo.setEnvironment(po.getEnvironment());
        vo.setCaseMaxTime(po.getCaseMaxTime());
        vo.setStepSleep(po.getStepSleep());
        vo.setTotal(po.getTotal());
        return vo;
    }

    public static List<AutoSuiteSimpleVO> transformPO2SimpleVO(List<AutoSuitePO> poList) {
        List<AutoSuiteSimpleVO> voList = new ArrayList<>();
        for (AutoSuitePO po : poList) {
            voList.add(transformPO2SimpleVO(po));
        }
        return voList;
    }

    public static AutoSuiteVO transformPO2VO(AutoSuitePO po) {
        if (po == null) {
            return null;
        }
        AutoSuiteVO vo = new AutoSuiteVO();
        // 设置基本信息
        vo.setOwnerId(po.getOwnerId());
        vo.setDescription(po.getDescription());
        vo.setTotal(po.getTotal());
        vo.setName(po.getName());
        vo.setFailed(po.getFailed());
        vo.setPassed(po.getPassed());
        vo.setSuiteId(po.getSuiteId());
        vo.setOwnerName(po.getOwnerName());
        vo.setEnvironment(po.getEnvironment());
        vo.setCaseMaxTime(po.getCaseMaxTime());
        vo.setStepSleep(po.getStepSleep());
        // 设置用例信息
//        vo.setCaseList(TransformCommon.toCase(po.getTestCase()));
        return vo;
    }

    public static AutoSuitePO transformVO2PO(AutoSuiteVO vo) {
        if (vo == null) {
            return null;
        }
        AutoSuitePO po = new AutoSuitePO();
        // 设置基本信息
        po.setOwnerId(vo.getOwnerId());
        po.setDescription(vo.getDescription());
        po.setTotal(vo.getTotal());
        po.setName(vo.getName());
        po.setSuiteId(vo.getSuiteId());
        po.setFailed(vo.getFailed());
        po.setPassed(vo.getPassed());
        po.setOwnerName(vo.getOwnerName());
        po.setEnvironment(vo.getEnvironment());
        po.setCaseMaxTime(vo.getCaseMaxTime());
        po.setStepSleep(vo.getStepSleep());
        // 设置用例信息
//        po.setTestCase(TransformCommon.toCase(vo.getCaseList()));
        return po;
    }

}
