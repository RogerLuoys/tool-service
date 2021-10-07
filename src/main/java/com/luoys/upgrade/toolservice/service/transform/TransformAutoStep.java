package com.luoys.upgrade.toolservice.service.transform;

import com.luoys.upgrade.toolservice.common.StringUtil;
import com.luoys.upgrade.toolservice.dao.po.AutoStepPO;
import com.luoys.upgrade.toolservice.service.dto.*;
import com.luoys.upgrade.toolservice.service.enums.AutoStepTypeEnum;
import com.luoys.upgrade.toolservice.service.enums.KeywordEnum;
import com.luoys.upgrade.toolservice.web.vo.AutoStepSimpleVO;
import com.luoys.upgrade.toolservice.web.vo.AutoStepVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动化步骤数据转换
 *
 * @author luoys
 */
public class TransformAutoStep {

    public static AutoStepSimpleVO transformPO2SimpleVO(AutoStepPO po) {
        if (po == null)
            return null;
        AutoStepSimpleVO vo = new AutoStepSimpleVO();
        vo.setDescription(po.getDescription());
        vo.setStepId(po.getStepId());
        vo.setOwnerId(po.getOwnerId());
        vo.setOwnerName(po.getOwnerName());
        vo.setName(po.getName());
        vo.setType(po.getType());
        vo.setIsPublic(po.getIsPublic());
        vo.setAssertActual(po.getAssertActual());
        vo.setAssertExpect(po.getAssertExpect());
        vo.setAssertResult(po.getAssertResult());
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
        vo.setStepId(po.getStepId());
        vo.setOwnerId(po.getOwnerId());
        vo.setOwnerName(po.getOwnerName());
        vo.setName(po.getName());
        vo.setType(po.getType());
        vo.setAssertActual(po.getAssertActual());
        vo.setAssertExpect(po.getAssertExpect());
        vo.setAssertResult(po.getAssertResult());
        vo.setAfterSleep(po.getAfterSleep());
        vo.setAssertType(po.getAssertType());
        // 详细信息转换
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
                httpRequestDTO.setHttpType(po.getHttpType());
                httpRequestDTO.setHttpHeaderList(TransformCommon.toParameter(po.getHttpHeader()));
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
                uiDTO.setKey(po.getUiKey());
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
        po.setStepId(vo.getStepId());
        po.setType(vo.getType());
        po.setOwnerId(vo.getOwnerId());
        po.setOwnerName(vo.getOwnerName());
        po.setName(vo.getName());
        po.setAssertActual(vo.getAssertActual());
        po.setAssertExpect(vo.getAssertExpect());
        po.setAssertResult(vo.getAssertResult());
        po.setAfterSleep(vo.getAfterSleep());
        po.setAssertType(vo.getAssertType());
        // 详细信息转换
        switch (AutoStepTypeEnum.fromCode(vo.getType())) {
            case STEP_SQL:
                if (vo.getJdbc() != null && vo.getJdbc().getDataSource() != null) {
                    po.setJdbcDriver(vo.getJdbc().getDataSource().getDriver());
                    po.setJdbcUrl(vo.getJdbc().getDataSource().getUrl());
                    po.setJdbcUsername(vo.getJdbc().getDataSource().getUsername());
                    po.setJdbcPassword(vo.getJdbc().getDataSource().getPassword());
                }
                if (vo.getJdbc() != null && vo.getJdbc().getSqlList() != null) {
                    po.setJdbcSql(TransformCommon.toSql(vo.getJdbc().getSqlList()));
                }
                break;
            case STEP_HTTP:
                if (vo.getHttpRequest() == null) {
                    break;
                }
                po.setHttpUrl(vo.getHttpRequest().getHttpURL());
                po.setHttpBody(vo.getHttpRequest().getHttpBody());
                po.setHttpHeader(TransformCommon.toParameter(vo.getHttpRequest().getHttpHeaderList()));
                po.setHttpType(vo.getHttpRequest().getHttpType());
                break;
            case STEP_RPC:
                if (vo.getRpc() == null) {
                    break;
                }
                po.setRpcInterface(vo.getRpc().getInterfaceName());
                po.setRpcMethod(vo.getRpc().getMethodName());
                po.setRpcUrl(vo.getRpc().getUrl());
                po.setRpcParameterType(vo.getRpc().getParameterType());
                po.setRpcParameter(TransformCommon.toParameter(vo.getRpc().getParameterList()));
                break;
            case STEP_UI:
                if (vo.getUi() == null) {
                    break;
                }
                po.setUiElement(vo.getUi().getElement());
                po.setUiElementId(vo.getUi().getElementId());
                po.setUiType(vo.getUi().getType());
                po.setUiUrl(vo.getUi().getUrl());
                po.setUiKey(vo.getUi().getKey());
                break;
        }

        return po;
    }

    /**
     * 变量合并
     * @param autoStepVO -
     */
    public static void mergeParam(AutoStepVO autoStepVO) {
        if (StringUtil.isBlank(autoStepVO.getEnvironment())) {
            return;
        }
        String actualURL;
        switch (AutoStepTypeEnum.fromCode(autoStepVO.getType())) {
            case STEP_HTTP:
                actualURL = autoStepVO.getHttpRequest().getHttpURL().replace(
                        KeywordEnum.PRE_PARAM_ENV.getValue(), autoStepVO.getEnvironment());
                autoStepVO.getHttpRequest().setHttpURL(actualURL);
                break;
            case STEP_RPC:
                actualURL = autoStepVO.getRpc().getUrl().replace(
                        KeywordEnum.PRE_PARAM_ENV.getValue(), autoStepVO.getEnvironment());
                autoStepVO.getRpc().setUrl(actualURL);
                break;
            case STEP_UI:
                actualURL = autoStepVO.getUi().getUrl().replace(
                        KeywordEnum.PRE_PARAM_ENV.getValue(), autoStepVO.getEnvironment());
                autoStepVO.getUi().setUrl(actualURL);
                break;
        }
    }
}
