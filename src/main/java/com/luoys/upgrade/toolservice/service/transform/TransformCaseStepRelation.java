package com.luoys.upgrade.toolservice.service.transform;

import com.luoys.upgrade.toolservice.dao.po.CaseStepRelationPO;
import com.luoys.upgrade.toolservice.service.dto.*;
import com.luoys.upgrade.toolservice.service.enums.AreaEnum;
import com.luoys.upgrade.toolservice.service.enums.AutoStepTypeEnum;
import com.luoys.upgrade.toolservice.web.vo.AutoStepVO;
import com.luoys.upgrade.toolservice.web.vo.CaseStepVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        vo.getAutoStep().setIsPublic(po.getIsPublic());
        vo.getAutoStep().setStepId(po.getStepId());
        vo.getAutoStep().setOwnerId(po.getOwnerId());
        vo.getAutoStep().setOwnerName(po.getOwnerName());
        vo.getAutoStep().setName(po.getStepName());
        vo.getAutoStep().setType(po.getStepType());
        vo.getAutoStep().setAssertActual(po.getAssertActual());
        vo.getAutoStep().setAssertExpect(po.getAssertExpect());
        vo.getAutoStep().setAssertResult(po.getAssertResult());
        vo.getAutoStep().setAfterSleep(po.getAfterSleep());
        vo.getAutoStep().setAssertType(po.getAssertType());
        // 模板转换
        switch (AutoStepTypeEnum.fromCode(po.getStepType())) {
            case STEP_SQL:
                JdbcDTO jdbcDTO = new JdbcDTO();
                //设置sql对象
                jdbcDTO.setSqlList(TransformCommon.toSql(po.getJdbcSql()));
                //设置数据源对象
                DataSourceDTO dataSourceDTO = new DataSourceDTO();
                dataSourceDTO.setDriver(po.getJdbcDriver());
                dataSourceDTO.setUrl(po.getJdbcUrl());
                dataSourceDTO.setUsername(po.getJdbcUsername());
                dataSourceDTO.setPassword(po.getJdbcPassword());
                jdbcDTO.setDataSource(dataSourceDTO);
                //设置数据库对象
                vo.getAutoStep().setJdbc(jdbcDTO);
                break;
            case STEP_HTTP:
                HttpRequestDTO httpRequestDTO = new HttpRequestDTO();
                httpRequestDTO.setHttpURL(po.getHttpUrl());
                httpRequestDTO.setHttpType(po.getHttpType());
                httpRequestDTO.setHttpHeaderList(TransformCommon.toParameter(po.getHttpHeader()));
                httpRequestDTO.setHttpBody(po.getHttpBody());
                vo.getAutoStep().setHttpRequest(httpRequestDTO);
                break;
            case STEP_RPC:
                RpcDTO rpcDTO = new RpcDTO();
                rpcDTO.setUrl(po.getRpcUrl());
                rpcDTO.setInterfaceName(po.getRpcInterface());
                rpcDTO.setMethodName(po.getRpcMethod());
                rpcDTO.setParameterType(po.getRpcParameterType());
                rpcDTO.setParameterList(TransformCommon.toParameter(po.getRpcParameter()));
                vo.getAutoStep().setRpc(rpcDTO);
                break;
            case STEP_UI:
                UiDTO uiDTO = new UiDTO();
                uiDTO.setUrl(po.getUiUrl());
                uiDTO.setType(po.getUiType());
                uiDTO.setElement(po.getUiElement());
                uiDTO.setElementId(po.getUiElementId());
                uiDTO.setKey(po.getUiKey());
                vo.getAutoStep().setUi(uiDTO);
                break;
            case STEP_MULTIPLE:
                Map<String, List<StepDTO>> stepMap = TransformCommon.toMultipleStep(po.getSteps());
                vo.getAutoStep().setIfStepList(stepMap.get(AreaEnum.IF.getValue()));
                vo.getAutoStep().setElseStepList(stepMap.get(AreaEnum.ELSE.getValue()));
                vo.getAutoStep().setThenStepList(stepMap.get(AreaEnum.THEN.getValue()));
                break;
        }
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

}
