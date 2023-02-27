package com.luoys.upgrade.toolservice.service.transform;

import com.luoys.upgrade.toolservice.dao.po.CaseStepRelationPO;
import com.luoys.upgrade.toolservice.service.common.CacheUtil;
import com.luoys.upgrade.toolservice.service.dto.StepDTO;
import com.luoys.upgrade.toolservice.service.enums.autoStep.ModuleTypeEnum;
import com.luoys.upgrade.toolservice.web.vo.AutoStepVO;
import com.luoys.upgrade.toolservice.web.vo.CaseStepVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 用例步骤关联关系数据转换
 *
 * @author luoys
 */
public class TransformCaseStepRelation {

    /**
     * 同时转换完整的关联信息和步骤信息
     * @param po 带有关联表和步骤表的完整信息
     * @return 带有关联表和步骤表的完整信息
     */
    public static CaseStepVO transformPO2VO(CaseStepRelationPO po) {
        if (po == null) {
            return null;
        }
        CaseStepVO vo = new CaseStepVO();
        // 设置用例步骤关联信息
        vo.setCaseId(po.getCaseId());
        vo.setStepId(po.getStepId());
        vo.setSequence(po.getSequence());
        vo.setType(po.getType());
        // 设置步骤信息
        AutoStepVO autoStepVO = new AutoStepVO();
        vo.setAutoStep(autoStepVO);
        // 设置步骤基本信息
        vo.getAutoStep().setStepId(po.getStepId());
        vo.getAutoStep().setName(po.getStepName());
        vo.getAutoStep().setModuleType(po.getModuleType());
        vo.getAutoStep().setMethodType(po.getMethodType());
        vo.getAutoStep().setMethodId(po.getMethodId());
        vo.getAutoStep().setMethodName(po.getMethodName());
        vo.getAutoStep().setParameter3(po.getParameter3());
        vo.getAutoStep().setParameter2(po.getParameter2());
        vo.getAutoStep().setParameter1(po.getParameter1());
        vo.getAutoStep().setVarName(po.getVarName());
        vo.getAutoStep().setResult(po.getResult());
        return vo;
    }

    /**
     * 同时转换完整的关联信息和步骤信息(列表模式)
     * @param poList 带有关联表和步骤表的完整信息
     * @return 带有关联表和步骤表的完整信息
     */
    public static List<CaseStepVO> transformPO2VO(List<CaseStepRelationPO> poList) {
        List<CaseStepVO> voList = new ArrayList<>();
        for (CaseStepRelationPO po : poList) {
            voList.add(transformPO2VO(po));
        }
        return voList;
    }

    /**
     * 只转换用例步骤的关联信息
     * @param vo -
     * @return 只含关联信息
     */
    public static CaseStepRelationPO transformVO2PO(CaseStepVO vo) {
        if (vo == null) {
            return null;
        }
        CaseStepRelationPO po = new CaseStepRelationPO();
        // 只需要关联表数据
        po.setStepId(vo.getStepId());
        po.setSequence(vo.getSequence());
        po.setType(vo.getType());
        po.setCaseId(vo.getCaseId());
        return po;
    }

    /**
     * 只转换用例步骤的关联信息(列表模式)
     * @param voList -
     * @return 只含关联信息
     */
    public static List<CaseStepRelationPO> transformVO2PO(List<CaseStepVO> voList) {
        List<CaseStepRelationPO> poList = new ArrayList<>();
        for (CaseStepVO vo : voList) {
            poList.add(transformVO2PO(vo));
        }
        return poList;
    }

    public static List<StepDTO> transformVO2DTO(List<CaseStepVO> voList) {
        List<StepDTO> dtoList = new ArrayList<>();
        for (CaseStepVO vo : voList) {
            if (vo.getAutoStep().getModuleType().equals(ModuleTypeEnum.PO.getCode())) {
                List<StepDTO> po = CacheUtil.getPoById(vo.getAutoStep().getMethodId());
                for (StepDTO poStep: po) {
                    poStep.setParameter1(poStep.getParameter1().replaceAll("\\$\\{param1}", vo.getAutoStep().getParameter1()));
                    poStep.setParameter2(poStep.getParameter2().replaceAll("\\$\\{param2}", vo.getAutoStep().getParameter2()));
                    poStep.setParameter3(poStep.getParameter3().replaceAll("\\$\\{param3}", vo.getAutoStep().getParameter3()));
                    dtoList.add(poStep);
                }
            } else {
                dtoList.add(TransformAutoStep.transformVO2DTO(vo.getAutoStep()));
            }
        }
        return dtoList;
    }

}
