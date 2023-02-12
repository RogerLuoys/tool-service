package com.luoys.upgrade.toolservice.service.transform;

import com.luoys.upgrade.toolservice.dao.po.AutoSuitePO;
import com.luoys.upgrade.toolservice.dao.po.AutoSuiteQueryPO;
import com.luoys.upgrade.toolservice.service.common.CacheUtil;
import com.luoys.upgrade.toolservice.service.dto.SuiteDTO;
import com.luoys.upgrade.toolservice.service.enums.DefaultEnum;
import com.luoys.upgrade.toolservice.web.vo.AutoSuiteSimpleVO;
import com.luoys.upgrade.toolservice.web.vo.AutoSuiteVO;
import com.luoys.upgrade.toolservice.web.vo.QueryVO;

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
        vo.setOwnerName(po.getOwnerId() == null ? null : CacheUtil.getUserById(po.getOwnerId()));
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
        vo.setSlaveType(po.getSlaveType());
        vo.setCaseTimeOut(po.getCaseTimeOut());
        vo.setTimeOut(po.getTimeOut());
        vo.setSuiteId(po.getId());
        vo.setOwnerName(po.getOwnerId() == null ? null : CacheUtil.getUserById(po.getOwnerId()));
        vo.setProjectId(po.getProjectId());
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
        po.setProjectId(vo.getProjectId());
        po.setFailed(vo.getFailed());
        po.setPassed(vo.getPassed());
//        po.setOwnerName(vo.getOwnerName());
        po.setSlaveType(vo.getSlaveType());
        po.setCaseTimeOut(vo.getCaseTimeOut());
        po.setTimeOut(vo.getTimeOut());
        po.setStatus(vo.getStatus());
        return po;
    }

    public static AutoSuiteQueryPO transformVO2PO(QueryVO vo) {
        if (vo == null) {
            return null;
        }
        AutoSuiteQueryPO po = new AutoSuiteQueryPO();
        po.setOwnerId(vo.getUserId());
        po.setName(vo.getName());
        po.setProjectId(vo.getProjectId());
        if (vo.getPageIndex() != null) {
            po.setStartIndex((vo.getPageIndex() - 1) * DefaultEnum.DEFAULT_PAGE_SIZE.getCode());
        }
        po.setStatus(vo.getStatus());
        return po;
    }

    public static SuiteDTO transformVO2DTO(AutoSuiteVO vo) {
        if (vo == null) {
            return null;
        }
        SuiteDTO dto = new SuiteDTO();
        // 设置基本信息
        dto.setSuiteId(vo.getSuiteId());
        dto.setProjectId(vo.getProjectId());
        dto.setRelatedCase(vo.getRelatedCase().getList());
        dto.setRetry(vo.getRetry());
        dto.setSlaveType(vo.getSlaveType());
        return dto;
    }

}
