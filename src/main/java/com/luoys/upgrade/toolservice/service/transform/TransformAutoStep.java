package com.luoys.upgrade.toolservice.service.transform;

import com.luoys.upgrade.toolservice.service.common.StringUtil;
import com.luoys.upgrade.toolservice.dao.po.AutoStepPO;
import com.luoys.upgrade.toolservice.service.dto.*;
import com.luoys.upgrade.toolservice.service.enums.*;
import com.luoys.upgrade.toolservice.web.vo.AutoStepSimpleVO;
import com.luoys.upgrade.toolservice.web.vo.AutoStepVO;

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
        vo.setDescription(po.getDescription());
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
        vo.setDescription(po.getDescription());
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
        po.setDescription(vo.getDescription());
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

//    /**
//     * 变量合并
//     *
//     * @param autoStepVO -
//     */
//    public static void mergeParam(AutoStepVO autoStepVO) {
//        if (StringUtil.isBlank(autoStepVO.getEnvironment())) {
//            return;
//        }
//        String actualURL;
//        switch (AutoStepTypeEnum.fromCode(autoStepVO.getType())) {
//            case STEP_HTTP:
//                actualURL = autoStepVO.getHttpRequest().getHttpURL().replace(
//                        KeywordEnum.PRE_PARAM_ENV.getValue(), autoStepVO.getEnvironment());
//                autoStepVO.getHttpRequest().setHttpURL(actualURL);
//                break;
//            case STEP_RPC:
//                actualURL = autoStepVO.getRpc().getUrl().replace(
//                        KeywordEnum.PRE_PARAM_ENV.getValue(), autoStepVO.getEnvironment());
//                autoStepVO.getRpc().setUrl(actualURL);
//                break;
//            case STEP_UI:
//                actualURL = autoStepVO.getUi().getUrl().replace(
//                        KeywordEnum.PRE_PARAM_ENV.getValue(), autoStepVO.getEnvironment());
//                autoStepVO.getUi().setUrl(actualURL);
//                break;
//        }
//    }
}
