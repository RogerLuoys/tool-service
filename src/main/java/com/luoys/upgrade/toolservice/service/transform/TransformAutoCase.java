package com.luoys.upgrade.toolservice.service.transform;

import com.luoys.upgrade.toolservice.dao.po.AutoCasePO;
import com.luoys.upgrade.toolservice.web.vo.AutoCaseSimpleVO;
import com.luoys.upgrade.toolservice.web.vo.AutoCaseVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动化用例数据转换
 *
 * @author luoys
 */
public class TransformAutoCase {


    public static AutoCaseSimpleVO transformPO2SimpleVO(AutoCasePO po) {
        if (po == null) {
            return null;
        }
        AutoCaseSimpleVO vo = new AutoCaseSimpleVO();
        vo.setDescription(po.getDescription());
        vo.setOwnerId(po.getOwnerId());
        vo.setName(po.getName());
        vo.setCaseId(po.getCaseId());
        vo.setType(po.getType());
        vo.setStatus(po.getStatus());
        vo.setMaxTime(po.getMaxTime());
        vo.setOwnerName(po.getOwnerName());
        vo.setEnvironment(po.getEnvironment());
        vo.setFinishTime(po.getFinishTime());
        return vo;
    }

    public static List<AutoCaseSimpleVO> transformPO2SimpleVO(List<AutoCasePO> poList) {
        List<AutoCaseSimpleVO> voList = new ArrayList<>();
        for (AutoCasePO po : poList) {
            voList.add(transformPO2SimpleVO(po));
        }
        return voList;
    }

    /**
     * 转换用例的基本信息
     * @param po -
     * @return -
     */
    public static AutoCaseVO transformPO2VO(AutoCasePO po) {
        if (po == null) {
            return null;
        }
        AutoCaseVO vo = new AutoCaseVO();
        // 设置基本信息
        vo.setName(po.getName());
        vo.setType(po.getType());
        vo.setDescription(po.getDescription());
        vo.setOwnerId(po.getOwnerId());
        vo.setOwnerName(po.getOwnerName());
        vo.setCaseId(po.getCaseId());
        vo.setStatus(po.getStatus());
        vo.setEnvironment(po.getEnvironment());
        vo.setFinishTime(po.getFinishTime());
        vo.setMaxTime(po.getMaxTime());
        return vo;
    }

    /**
     * 转换用例的基本信息
     * @param vo -
     * @return -
     */
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
        po.setCaseId(vo.getCaseId());
        po.setType(vo.getType());
        po.setStatus(vo.getStatus());
        po.setMaxTime(vo.getMaxTime());
        po.setEnvironment(vo.getEnvironment());
        po.setFinishTime(vo.getFinishTime());
        return po;
    }

}
