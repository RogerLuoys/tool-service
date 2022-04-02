package com.luoys.upgrade.toolservice.service.transform;

import com.luoys.upgrade.toolservice.common.StringUtil;
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
            case STEP_MULTIPLE:
                Map<String, List<StepDTO>> stepMap = TransformCommon.toMultipleStep(po.getSteps());
                vo.setIfStepList(stepMap.get(AreaEnum.IF.getValue()));
                vo.setElseStepList(stepMap.get(AreaEnum.ELSE.getValue()));
                vo.setThenStepList(stepMap.get(AreaEnum.THEN.getValue()));
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
            case STEP_MULTIPLE:
                List<StepDTO> stepList = new ArrayList<>();
                stepList.addAll(vo.getIfStepList());
                stepList.addAll(vo.getElseStepList());
                stepList.addAll(vo.getThenStepList());
                po.setSteps(TransformCommon.toMultipleStep(stepList));
                break;
        }

        return po;
    }

    /**
     * 转换步骤模式，将结构化步骤转换成脚本
     *
     * @param autoStepVO -
     */
    public static AutoStepVO transform2ScriptMode(AutoStepVO autoStepVO) {
        if (autoStepVO == null) {
            return null;
        }
        switch (AutoStepTypeEnum.fromCode(autoStepVO.getType())) {
            case STEP_UI:
                switch (UiTypeEnum.fromCode(autoStepVO.getUi().getType())) {
                    case CLICK:
                        autoStepVO.setScript(UiTypeEnum.CLICK.getScriptTemplate() + "(\"" + autoStepVO.getUi().getElement() + "\");");
                    case OPEN_URL:
                        autoStepVO.setScript(UiTypeEnum.OPEN_URL.getScriptTemplate() + "(\"" + autoStepVO.getUi().getUrl() + "\");");
                    case HOVER:
                        autoStepVO.setScript(UiTypeEnum.HOVER.getScriptTemplate() + "(\"" + autoStepVO.getUi().getElement() + "\");");
                    case SEND_KEY:
                        autoStepVO.setScript(UiTypeEnum.SEND_KEY.getScriptTemplate() + "(\"" + autoStepVO.getUi().getElement() + "\", \"" + autoStepVO.getUi().getKey() + "\");");
                    case SWITCH_FRAME:
                        autoStepVO.setScript(UiTypeEnum.SWITCH_FRAME.getScriptTemplate() + "(\"" + autoStepVO.getUi().getElement() + "\");");
                }
            case STEP_SQL:
                autoStepVO.setScript("auto.db.execute(\"" + autoStepVO.getJdbc().getSqlList().get(0).getSql() + "\");");
            case STEP_HTTP:
                switch (HttpTypeEnum.fromValue(autoStepVO.getHttpRequest().getHttpType())) {
                    case GET:
                        autoStepVO.setScript(HttpTypeEnum.GET.getScriptTemplate() + "(\"" + autoStepVO.getHttpRequest().getHttpURL() + "\");");
                    case POST:
                        autoStepVO.setScript(HttpTypeEnum.POST.getScriptTemplate() + "(\"" + autoStepVO.getHttpRequest().getHttpURL() + "\", \"" + autoStepVO.getHttpRequest().getHttpBody() + "\");");
                    case PUT:
                        autoStepVO.setScript(HttpTypeEnum.PUT.getScriptTemplate() + "(\"" + autoStepVO.getHttpRequest().getHttpURL() + "\", \"" + autoStepVO.getHttpRequest().getHttpBody() + "\");");
                    case DELETE:
                        autoStepVO.setScript(HttpTypeEnum.DELETE.getScriptTemplate() + "(\"" + autoStepVO.getHttpRequest().getHttpURL() + "\", \"" + autoStepVO.getHttpRequest().getHttpBody() + "\");");
                }
        }
        return autoStepVO;
    }

    /**
     * 转换步骤模式，将结构化步骤转换成脚本
     *
     * @param autoStepVO -
     */
    public static void transform2UiMode(AutoStepVO autoStepVO) {
        if (autoStepVO == null) {
            return;
        }
        // 脚本范例：auto.methodType.methodName(methodParam);
        String script = autoStepVO.getScript();
        String methodType = script.substring(0, script.indexOf(".", 1));
        String methodName = script.substring(0, script.indexOf(".", 2));
        String methodParam = script.substring(script.indexOf("(\""), script.lastIndexOf("\")"));
        String[] params = methodParam.split("\"\\*\\s,\\*\\s\"");
        switch (MethodTypeEnum.fromScriptTemplate(methodType)) {
            case UI:    // 脚本范例：auto.ui.click("xpath")
                switch (UiTypeEnum.fromScriptTemplate(methodName)) {
                    case OPEN_URL:
                        autoStepVO.getUi().setUrl(methodParam);
                        autoStepVO.getUi().setType(UiTypeEnum.OPEN_URL.getCode());
                    case CLICK:
                        autoStepVO.getUi().setElement(methodParam);
                        autoStepVO.getUi().setType(UiTypeEnum.CLICK.getCode());
                    case SEND_KEY:
                        autoStepVO.getUi().setElement(params[0]);
                        autoStepVO.getUi().setKey(params[1]);
                        autoStepVO.getUi().setType(UiTypeEnum.SEND_KEY.getCode());
                    case SWITCH_FRAME:
                        autoStepVO.getUi().setElement(methodParam);
                        autoStepVO.getUi().setType(UiTypeEnum.SWITCH_FRAME.getCode());
                    case HOVER:
                        autoStepVO.getUi().setElement(methodParam);
                        autoStepVO.getUi().setType(UiTypeEnum.HOVER.getCode());
                }
            case SQL:   // 脚本范例：auto.db.onePiece("sql");
                // todo ?
                autoStepVO.getJdbc().getDataSource().setDbName(params[0]);
                autoStepVO.getJdbc().setSql(params[1]);
            case HTTP:  // 脚本范例：auto.http.doPost("url","body");
                switch (HttpTypeEnum.fromScriptTemplate(methodName)) {
                    case GET:
                        autoStepVO.getHttpRequest().setHttpURL(methodParam);
                        autoStepVO.getHttpRequest().setHttpType(HttpTypeEnum.GET.getValue());
                        break;
                    case POST:
                        autoStepVO.getHttpRequest().setHttpURL(params[0]);
                        autoStepVO.getHttpRequest().setHttpBody(params[1]);
                        autoStepVO.getHttpRequest().setHttpType(HttpTypeEnum.POST.getValue());
                    case PUT:
                        autoStepVO.getHttpRequest().setHttpURL(params[0]);
                        autoStepVO.getHttpRequest().setHttpBody(params[1]);
                        autoStepVO.getHttpRequest().setHttpType(HttpTypeEnum.PUT.getValue());
                    case DELETE:
                        autoStepVO.getHttpRequest().setHttpURL(params[0]);
                        autoStepVO.getHttpRequest().setHttpBody(params[1]);
                        autoStepVO.getHttpRequest().setHttpType(HttpTypeEnum.DELETE.getValue());
                }
        }
    }

    /**
     * 变量合并
     *
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
