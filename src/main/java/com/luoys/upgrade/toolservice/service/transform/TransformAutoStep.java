package com.luoys.upgrade.toolservice.service.transform;

import com.luoys.upgrade.toolservice.service.common.CacheUtil;
import com.luoys.upgrade.toolservice.service.common.StringUtil;
import com.luoys.upgrade.toolservice.dao.po.AutoStepPO;
import com.luoys.upgrade.toolservice.service.dto.*;
import com.luoys.upgrade.toolservice.service.enums.*;
import com.luoys.upgrade.toolservice.service.enums.autoStep.ModuleTypeEnum;
import com.luoys.upgrade.toolservice.web.vo.AutoStepSimpleVO;
import com.luoys.upgrade.toolservice.web.vo.AutoStepVO;
import com.luoys.upgrade.toolservice.web.vo.CaseStepVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 自动化步骤数据转换
 *
 * @author luoys
 */
public class TransformAutoStep {

    /**
     * 基本信息转换
     *
     * @param po -
     * @return -
     */
    public static AutoStepSimpleVO transformPO2SimpleVO(AutoStepPO po) {
        if (po == null)
            return null;
        AutoStepSimpleVO vo = new AutoStepSimpleVO();
        vo.setResult(po.getResult());
        vo.setStepId(po.getId());
//        vo.setOwnerId(po.getOwnerId());
//        vo.setOwnerName(po.getOwnerName());
        vo.setName(po.getName());
        vo.setModuleType(po.getModuleType());
//        vo.setIsPublic(po.getIsPublic());
//        vo.setAssertActual(po.getAssertActual());
//        vo.setAssertExpect(po.getAssertExpect());
//        vo.setAssertResult(po.getAssertResult());
//        vo.setAfterSleep(po.getAfterSleep());
//        vo.setAssertType(po.getAssertType());
        return vo;
    }

    public static List<AutoStepSimpleVO> transformPO2VO(List<AutoStepPO> poList) {
        List<AutoStepSimpleVO> voList = new ArrayList<>();
        for (AutoStepPO po : poList) {
            voList.add(transformPO2SimpleVO(po));
        }
        return voList;
    }

    /**
     * 详细信息转换
     *
     * @param po -
     * @return -
     */
    public static AutoStepVO transformPO2VO(AutoStepPO po) {
        if (po == null) {
            return null;
        }
        AutoStepVO vo = new AutoStepVO();
        // 设置基本信息
        vo.setResult(po.getResult());
        vo.setStepId(po.getId());
        vo.setName(po.getName());
        vo.setModuleType(po.getModuleType());
        vo.setMethodType(po.getMethodType());
        vo.setMethodName(po.getMethodName());
        vo.setMethodId(po.getMethodId());
        vo.setVarName(po.getVarName());
        vo.setParameter1(po.getParameter1());
        vo.setParameter2(po.getParameter2());
        vo.setParameter3(po.getParameter3());
        return vo;
    }

    public static AutoStepPO transformVO2PO(AutoStepVO vo) {
        if (vo == null) {
            return null;
        }
        AutoStepPO po = new AutoStepPO();
        // 设置基本信息
        po.setResult(vo.getResult());
        po.setId(vo.getStepId());
        po.setVarName(vo.getVarName());
        po.setName(vo.getName());

        po.setModuleType(vo.getModuleType());
        po.setMethodType(vo.getMethodType());
        po.setMethodName(vo.getMethodName());
        po.setMethodId(vo.getMethodId());
        po.setParameter1(vo.getParameter1());
        po.setParameter2(vo.getParameter2());
        po.setParameter3(vo.getParameter3());
        return po;
    }

    public static StepDTO transformVO2DTO(AutoStepVO vo) {
        if (vo == null) {
            return null;
        }
        StepDTO dto = new StepDTO();
        // 设置基本信息
        dto.setStepId(vo.getStepId());
        dto.setMethodType(vo.getMethodType());
        dto.setModuleType(vo.getModuleType());
        dto.setParameter1(vo.getParameter1());
        dto.setParameter2(vo.getParameter2());
        dto.setParameter3(vo.getParameter3());
        dto.setVarName(vo.getVarName());
        if (vo.getModuleType().equals(ModuleTypeEnum.SQL.getCode())) {
            dto.setDataSource(CacheUtil.getJdbcById(vo.getMethodId()));
        }
        return dto;
    }

    public static List<StepDTO> transformVO2DTO(List<AutoStepVO> voList) {
        List<StepDTO> dtoList = new ArrayList<>();
        for (AutoStepVO vo : voList) {
            if (vo.getModuleType().equals(ModuleTypeEnum.PO.getCode())) {
                List<StepDTO> po = CacheUtil.getPoById(vo.getMethodId());
                for (StepDTO poStep: po) {
                    poStep.setParameter1(poStep.getParameter1().replaceAll("\\$\\{param1}", vo.getParameter1()));
                    poStep.setParameter2(poStep.getParameter2().replaceAll("\\$\\{param2}", vo.getParameter2()));
                    poStep.setParameter3(poStep.getParameter3().replaceAll("\\$\\{param3}", vo.getParameter3()));
                    dtoList.add(poStep);
                }
            } else {
                dtoList.add(transformVO2DTO(vo));
            }
        }
        return dtoList;
    }

//    public static List<StepDTO> transformVO2DTO(List<CaseStepVO> voList) {
//        List<StepDTO> dtoList = new ArrayList<>();
//        for (CaseStepVO vo : voList) {
//            dtoList.add(transformVO2DTO(vo.getAutoStep()));
//        }
//        return dtoList;
//    }

}
