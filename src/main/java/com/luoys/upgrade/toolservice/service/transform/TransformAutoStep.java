package com.luoys.upgrade.toolservice.service.transform;

import com.luoys.upgrade.toolservice.dao.po.AutoStepPO;
import com.luoys.upgrade.toolservice.service.dto.*;
import com.luoys.upgrade.toolservice.service.enums.AutoStepTypeEnum;
import com.luoys.upgrade.toolservice.web.vo.AutoStepSimpleVO;
import com.luoys.upgrade.toolservice.web.vo.AutoStepVO;

import java.util.ArrayList;
import java.util.List;

public class TransformAutoStep {

    public static AutoStepSimpleVO transformPO2SimpleVO(AutoStepPO po) {
        if (po == null)
            return null;
        AutoStepSimpleVO vo = new AutoStepSimpleVO();
        vo.setDescription(po.getDescription());
        vo.setStepId(po.getId());
        vo.setOwnerId(po.getOwnerId());
        vo.setOwnerName(po.getOwnerName());
        vo.setName(po.getName());
        vo.setType(po.getType());
        vo.setIsPublic(po.getIsPublic());
        vo.setActualResult(po.getActualResult());
        vo.setExpectResult(po.getExpectResult());
        vo.setAfterSleep(po.getAfterSleep());
        vo.setAssertType(po.getAssertType());
        return vo;
    }

    public static List<AutoStepSimpleVO> transformPO2VO(List<AutoStepPO> poList) {
        List<AutoStepSimpleVO> voList = new ArrayList<>();
        for (AutoStepPO po : poList) {
            voList.add(transformPO2SimpleVO(po));
        }
        return voList;
    }

    public static AutoStepVO transformPO2VO(AutoStepPO po) {
        if (po == null) {
            return null;
        }
        AutoStepVO vo = new AutoStepVO();
        // 设置基本信息
        vo.setIsPublic(po.getIsPublic());
        vo.setDescription(po.getDescription());
        vo.setStepId(po.getId());
        vo.setOwnerId(po.getOwnerId());
        vo.setOwnerName(po.getOwnerName());
        vo.setName(po.getName());
        vo.setType(po.getType());
        vo.setActualResult(po.getActualResult());
        vo.setExpectResult(po.getExpectResult());
        vo.setAfterSleep(po.getAfterSleep());
        vo.setAssertType(po.getAssertType());
        // 模板转换
        switch (AutoStepTypeEnum.fromCode(po.getType())) {
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
                vo.setJdbc(jdbcDTO);
                break;
            case STEP_HTTP:
                HttpRequestDTO httpRequestDTO = new HttpRequestDTO();
                httpRequestDTO.setHttpURL(po.getHttpUrl());
                httpRequestDTO.setHttpType(po.getHttpHeader());
                httpRequestDTO.setHttpBody(po.getHttpBody());
                vo.setHttpRequest(httpRequestDTO);
                break;
            case STEP_RPC:
                RpcDTO rpcDTO = new RpcDTO();
                rpcDTO.setUrl(po.getRpcUrl());
                rpcDTO.setInterfaceName(po.getRpcInterface());
                rpcDTO.setMethodName(po.getRpcMethod());
                rpcDTO.setParameterType(po.getRpcParameterType());
                rpcDTO.setParameterList(TransformCommon.toParameter(po.getRpcParameter()));
                vo.setRpc(rpcDTO);
                break;
            case STEP_UI:
                UiDTO uiDTO = new UiDTO();
                uiDTO.setUrl(po.getUiUrl());
                uiDTO.setType(po.getUiType());
                uiDTO.setElement(po.getUiElement());
                uiDTO.setElementId(po.getUiElementId());
                vo.setUi(uiDTO);
                break;
        }
        return vo;
    }

    public static AutoStepPO transformVO2PO(AutoStepVO vo) {
        if (vo == null) {
            return null;
        }
        AutoStepPO po = new AutoStepPO();
        // 设置基本信息
        po.setIsPublic(vo.getIsPublic());
        po.setDescription(vo.getDescription());
        po.setId(vo.getStepId());
        po.setType(vo.getType());
        po.setOwnerId(vo.getOwnerId());
        po.setOwnerName(vo.getOwnerName());
        po.setName(vo.getName());
        po.setActualResult(vo.getActualResult());
        po.setExpectResult(vo.getExpectResult());
        po.setAfterSleep(vo.getAfterSleep());
        po.setAssertType(vo.getAssertType());
        // 模板转换
        switch (AutoStepTypeEnum.fromCode(vo.getType())) {
            case STEP_SQL:
                po.setJdbcDriver(vo.getJdbc().getDataSource().getDriver());
                po.setJdbcUrl(vo.getJdbc().getDataSource().getUrl());
                po.setJdbcUsername(vo.getJdbc().getDataSource().getUsername());
                po.setJdbcPassword(vo.getJdbc().getDataSource().getPassword());
                po.setJdbcSql(TransformCommon.toSql(vo.getJdbc().getSqlList()));
                break;
            case STEP_HTTP:
                po.setHttpUrl(vo.getHttpRequest().getHttpURL());
                po.setHttpBody(vo.getHttpRequest().getHttpBody());
                po.setHttpHeader(TransformCommon.toParameter(vo.getHttpRequest().getHttpHeaderList()));
                po.setHttpType(vo.getHttpRequest().getHttpType());
                break;
            case STEP_RPC:
                po.setRpcInterface(vo.getRpc().getInterfaceName());
                po.setRpcMethod(vo.getRpc().getMethodName());
                po.setRpcUrl(vo.getRpc().getUrl());
                po.setRpcParameterType(vo.getRpc().getParameterType());
                po.setRpcParameter(TransformCommon.toParameter(vo.getRpc().getParameterList()));
                break;
            case STEP_UI:
                po.setUiElement(vo.getUi().getElement());
                po.setUiElementId(vo.getUi().getElementId());
                po.setUiType(vo.getUi().getType());
                po.setUiUrl(vo.getUi().getUrl());
                break;
        }
        return po;
    }

}
