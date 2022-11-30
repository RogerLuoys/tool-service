package com.luoys.upgrade.toolservice.service.transform;

import com.luoys.upgrade.toolservice.dao.po.AutoSuitePO;
import com.luoys.upgrade.toolservice.web.vo.AutoSuiteSimpleVO;
import com.luoys.upgrade.toolservice.web.vo.AutoSuiteVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动化测试集数据转换
 *
 * @author luoys
 */
public class TransformAutoSuite {

    /**
     * 把AutoSuitePO转换成AutoSuiteSimpleVO
     * @param po -
     * @return -
     */
    public static AutoSuiteSimpleVO transformPO2SimpleVO(AutoSuitePO po) {
        if (po == null) {
            return null;
        }
        AutoSuiteSimpleVO vo = new AutoSuiteSimpleVO();
        vo.setDescription(po.getDescription());
        vo.setName(po.getName());
        vo.setFailed(po.getFailed());
        vo.setPassed(po.getPassed());
        vo.setSuiteId(po.getId());
        vo.setOwnerId(po.getOwnerId());
        vo.setOwnerName(po.getOwnerName());
//        vo.setEnvironment(po.getEnvironment());
//        vo.setCaseMaxTime(po.getCaseMaxTime());
//        vo.setStepSleep(po.getStepSleep());
        vo.setTotal(po.getTotal());
        vo.setStatus(po.getStatus());
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
        vo.setSuiteId(po.getId());
        vo.setOwnerName(po.getOwnerName());
//        vo.setEnvironment(po.getEnvironment());
//        vo.setCaseMaxTime(po.getCaseMaxTime());
        vo.setStatus(po.getStatus());
//        vo.setStepSleep(po.getStepSleep());
//        vo.setIsUiCompleted(po.getIsUiCompleted());
//        vo.setIsApiCompleted(po.getIsApiCompleted());
//        // 参数列表转换
//        vo.setParameterList(TransformCommon.toParameter(po.getParameter()));
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
        po.setId(vo.getSuiteId());
        po.setFailed(vo.getFailed());
        po.setPassed(vo.getPassed());
        po.setOwnerName(vo.getOwnerName());
//        po.setEnvironment(vo.getEnvironment());
//        po.setCaseMaxTime(vo.getCaseMaxTime());
        po.setStatus(vo.getStatus());
//        po.setStepSleep(vo.getStepSleep());
//        po.setIsApiCompleted(vo.getIsApiCompleted());
//        po.setIsUiCompleted(vo.getIsUiCompleted());
//        // 参数列表转换
//        po.setParameter(TransformCommon.toParameter(vo.getParameterList()));
        return po;
    }

}
