package com.luoys.upgrade.toolservice.service.transform;

import com.luoys.upgrade.toolservice.dao.po.AutoCasePO;
import com.luoys.upgrade.toolservice.dao.po.AutoCaseQueryPO;
import com.luoys.upgrade.toolservice.service.common.CacheUtil;
import com.luoys.upgrade.toolservice.service.dto.CaseDTO;
import com.luoys.upgrade.toolservice.service.enums.KeywordEnum;
import com.luoys.upgrade.toolservice.web.vo.AutoCaseSimpleVO;
import com.luoys.upgrade.toolservice.web.vo.AutoCaseVO;
import com.luoys.upgrade.toolservice.web.vo.CaseStepVO;
import com.luoys.upgrade.toolservice.web.vo.QueryVO;

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
        vo.setCaseId(po.getId());
        vo.setType(po.getType());
        vo.setStatus(po.getStatus());
//        vo.setMaxTime(po.getMaxTime());
        vo.setOwnerName(po.getOwnerName());
//        vo.setEnvironment(po.getEnvironment());
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
        vo.setProjectId(po.getProjectId());
        vo.setCaseId(po.getId());
        vo.setStatus(po.getStatus());
//        vo.setMainSteps(po.getMainSteps());
//        vo.setEnvironment(po.getEnvironment());
        vo.setFinishTime(po.getFinishTime());
        vo.setSupperCaseId(po.getSupperCaseId());
//        vo.setMaxTime(po.getMaxTime());
//        // 参数列表转换
//        vo.setParameterList(TransformCommon.toParameter(po.getParameter()));
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
        po.setId(vo.getCaseId());
        po.setType(vo.getType());
        po.setStatus(vo.getStatus());
        po.setProjectId(vo.getProjectId());
        po.setSupperCaseId(vo.getSupperCaseId());
//        po.setMaxTime(vo.getMaxTime());
//        po.setEnvironment(vo.getEnvironment());
        po.setFinishTime(vo.getFinishTime());
//        // 参数列表转换
//        po.setParameter(TransformCommon.toParameter(vo.getParameterList()));
//        // 转换步骤脚本
//        po.setMainSteps(vo.getMainSteps());
        return po;
    }

    /**
     * 转换用例的基本信息
     * @param vo -
     * @return -
     */
    public static AutoCaseQueryPO transformVO2PO(QueryVO vo) {
        if (vo == null) {
            return null;
        }
        AutoCaseQueryPO po = new AutoCaseQueryPO();
        po.setName(vo.getName());
        po.setType(vo.getType());
        po.setStatus(vo.getStatus());
        po.setProjectId(vo.getProjectId());
        po.setSupperCaseId(vo.getSupperCaseId());
        if (vo.getPageIndex() != null) {
            po.setStartIndex((vo.getPageIndex() - 1) * KeywordEnum.DEFAULT_PAGE_SIZE.getCode());
        }
        return po;
    }

    /**
     * 转换用例
     * @param vo -
     * @return -
     */
    public static CaseDTO transformVO2DTO(AutoCaseVO vo) {
        if (vo == null) {
            return null;
        }
        CaseDTO dto = new CaseDTO();
        // 从缓存中取超类数据
        AutoCaseVO supperCaseVO = CacheUtil.getSupperClassById(vo.getSupperCaseId());
        // 处理基本参数
        dto.setSupperCaseId(vo.getSupperCaseId());
        dto.setParams(TransformConfig.transformVO2Map(supperCaseVO.getParameterList()));
        dto.setUiArgument(TransformConfig.transformVO2Array(supperCaseVO.getArgumentList()));
        if (supperCaseVO.getArgumentList() != null && supperCaseVO.getArgumentList().size() > 0) {
            dto.setUiType(supperCaseVO.getArgumentList().get(0).getType());
        }
        // 编排步骤
        dto.setBeforeSuite(null); // 暂不支持
        dto.setSupperBeforeTest(TransformCaseStepRelation.transformVO2DTO(supperCaseVO.getPreStepList()));
        dto.setBeforeTest(TransformCaseStepRelation.transformVO2DTO(vo.getPreStepList()));
        dto.setTest(TransformCaseStepRelation.transformVO2DTO(vo.getMainStepList()));
        dto.setAfterTest(TransformCaseStepRelation.transformVO2DTO(vo.getAfterStepList()));
        dto.setSupperAfterTest(TransformCaseStepRelation.transformVO2DTO(supperCaseVO.getAfterStepList()));
        dto.setAfterSuite(null); // 暂不支持

        return dto;
    }

}
