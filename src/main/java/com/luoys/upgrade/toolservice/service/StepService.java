package com.luoys.upgrade.toolservice.service;

import com.luoys.upgrade.toolservice.dao.AutoCaseMapper;
import com.luoys.upgrade.toolservice.dao.ResourceMapper;
import com.luoys.upgrade.toolservice.dao.po.AutoStepPO;
import com.luoys.upgrade.toolservice.service.common.StringUtil;
import com.luoys.upgrade.toolservice.dao.AutoStepMapper;
import com.luoys.upgrade.toolservice.service.enums.autoStep.methodType.*;
import com.luoys.upgrade.toolservice.service.enums.autoStep.ModuleTypeEnum;
import com.luoys.upgrade.toolservice.service.transform.TransformAutoStep;
import com.luoys.upgrade.toolservice.web.vo.AutoStepVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 步骤服务，包含自动化步骤相关的所有业务逻辑
 *
 * @author luoys
 */
@Slf4j
@Service
public class StepService {

    @Autowired
    private AutoStepMapper autoStepMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private AutoCaseMapper autoCaseMapper;

    /**
     * 创建单个步骤
     *
     * @param autoStepVO 步骤对象
     * @return 返回id
     */
    public Integer create(AutoStepVO autoStepVO) {
        AutoStepPO autoStepPO = TransformAutoStep.transformVO2PO(autoStepVO);
        autoStepMapper.insert(autoStepPO);
        return autoStepPO.getId();
    }

    /**
     * 快速创建单个步骤
     *
     * @return 成功则返回stepId
     */
    public Integer quickCreate() {
        AutoStepVO autoStepVO = new AutoStepVO();
        autoStepVO.setModuleType(ModuleTypeEnum.UNDEFINED_MODULE.getCode());
        AutoStepPO autoStepPO = TransformAutoStep.transformVO2PO(autoStepVO);
        autoStepMapper.insert(autoStepPO);
        return autoStepPO.getId();
    }

    /**
     * 逻辑删除单个步骤
     *
     * @param stepId 步骤业务id
     * @return 成功为1
     */
    public Integer remove(Integer stepId) {
        return autoStepMapper.remove(stepId);
    }

    /**
     * 更新单个步骤
     *
     * @param autoStepVO 步骤对象
     * @return 成功为1
     */
    public Integer update(AutoStepVO autoStepVO) {
        AutoStepPO autoStepPO = TransformAutoStep.transformVO2PO(autoStepVO);
        return autoStepMapper.update(autoStepPO);
    }

    /**
     * 更新步骤执行结果
     *
     * @return 成功为1
     */
    public Integer updateResult(Integer stepId, String result) {
        return autoStepMapper.updateResult(stepId, result);
    }

    /**
     * 查询步骤详情
     *
     * @param stepId 步骤业务id
     * @return 步骤对象
     */
    public AutoStepVO queryDetail(Integer stepId) {
        AutoStepPO autoStepPO = autoStepMapper.select(stepId);
        return TransformAutoStep.transformPO2VO(autoStepPO);
    }


    /**
     * 转换步骤模式，将结构化步骤转换成脚本
     *
     * @param autoStepVO -
     */
    public AutoStepVO change2ScriptMode(AutoStepVO autoStepVO) {
        if (autoStepVO == null) {
            return null;
        }
        String param1 = StringUtil.escape(autoStepVO.getParameter1());
        String param2 = StringUtil.escape(autoStepVO.getParameter2());
        String param3 = StringUtil.escape(autoStepVO.getParameter3());
        String methodName = autoStepVO.getMethodName();
        switch (ModuleTypeEnum.fromCode(autoStepVO.getModuleType())) {
            case UI:
                autoStepVO.setScript(this.buildScript(autoStepVO.getVarName(), ModuleTypeEnum.UI.getName(), methodName, param1, param2, param3));
                break;
            case SQL:
                autoStepVO.setScript(this.buildScript(autoStepVO.getVarName(), ModuleTypeEnum.SQL.getName(), methodName, param1, param2, param3));
                break;
            case HTTP:
                autoStepVO.setScript(this.buildScript(autoStepVO.getVarName(), ModuleTypeEnum.HTTP.getName(), methodName, param1, param2, param3));
                break;
            case ASSERTION:
                autoStepVO.setScript(this.buildScript(autoStepVO.getVarName(), ModuleTypeEnum.ASSERTION.getName(), methodName, param1, param2, param3));
                break;
            case UTIL:
                autoStepVO.setScript(this.buildScript(autoStepVO.getVarName(), ModuleTypeEnum.UTIL.getName(), methodName, param1, param2, param3));
                break;
            case RPC:
                autoStepVO.setScript(this.buildScript(autoStepVO.getVarName(), ModuleTypeEnum.RPC.getName(), methodName, param1, param2, param3));
                break;
            case PO:
                autoStepVO.setScript(this.buildScript(autoStepVO.getVarName(), ModuleTypeEnum.PO.getName(), methodName, param1, param2, param3));
                break;
            case UNDEFINED_MODULE:
                autoStepVO.setScript("auto.undefined.unKnown();\n");
                break;
        }
        return autoStepVO;
    }

    /**
     * 拼接脚本
     * @param varName 变量名
     * @param module 模块名
     * @param method 方法名
     * @param param1 入参1
     * @param param2 入参2
     * @param param3 入参3
     * @return 步骤脚本
     */
    private String buildScript(String varName, String module, String method, String param1, String param2, String param3) {
        StringBuilder stringBuilder = new StringBuilder();
        if (!StringUtil.isBlank(varName)) {
            stringBuilder.append("String ").append(varName).append(" = ");
        }
        stringBuilder.append("auto.").append(module).append(".").append(method);
        // 多形参方法，不接受前面参数为空，如(null, "1", "1")则认为未设置入参
        if (StringUtil.isBlank(param1)) {
            // 第一个参数为空，为未设置入参
            stringBuilder.append("();\n");
            return stringBuilder.toString();
        } else if (StringUtil.isBlank(param2)) {
            // 第一个参数不为空，第二个参数为空，为设置了一个入参
            stringBuilder.append("(\"").append(param1).append("\");\n");
            return stringBuilder.toString();
        } else if (StringUtil.isBlank(param3)) {
            // 第一、二个参数不为空，第三个参数为空，为设置了两个入参
            stringBuilder.append("(\"")
                    .append(param1).append("\", \"")
                    .append(param2).append("\");\n");
            return stringBuilder.toString();
        } else {
            // 第一、二、三个参数都不为空，为设置了三个入参
            stringBuilder.append("(\"")
                    .append(param1).append("\", \"")
                    .append(param2).append("\", \"")
                    .append(param3).append("\");\n");
            return stringBuilder.toString();
        }
    }

    /**
     * 转换步骤模式，将结构化步骤转换成脚本
     *
     * @param autoStepVO - 带脚本的完整步骤对象
     */
    public AutoStepVO change2UiMode(AutoStepVO autoStepVO, Integer supperCaseId, Integer projectId) {
        if (autoStepVO == null) {
            return null;
        }
        // 脚本范例：auto.methodType.methodName(methodParam);
        String script = autoStepVO.getScript();
        // 步骤有赋值，先取变量名，再去除多余字符
        if (script.startsWith("String")) {
            // 取String和=之间的内容，再去空格
            String varName = script.substring(6, script.indexOf("=")).trim();
            autoStepVO.setVarName(varName);
            script = script.substring(script.indexOf("auto."));
        }
        // 截取模块名，取第1个'.'到第2个'.'之间的内容，如：auto.ui.click(xpath)会取ui
        String moduleName = script.substring(5, script.indexOf(".", 5));
        // 截取方法名，取第2个'.'到第1个'('之间的内容，如：auto.ui.click(xpath)会取click
        String methodName = script.substring(script.indexOf(".", 5) + 1, script.indexOf("("));
        // 截取步骤入参，入参中的双引号，需要已被转义
        String methodParam;
        if (script.contains("(\"") && script.contains("\")")) {
            methodParam = script.substring(script.indexOf("(\"") + 2, script.lastIndexOf("\")"));
        } else {
            methodParam = null;
        }
        // 截取多个参数，如：("xpath","key") (根据实际情况使用)
        String[] params = StringUtil.isBlank(methodParam) ? null : methodParam.split("\",\\s{0,4}\"");
        String param1, param2, param3;
        if (params == null || params.length == 0) {
            // 方法无入参
            param1 = null;
            param2 = null;
            param3 = null;
        } else if (params.length == 1) {
            // 方法有一个入参
            param1 = params[0];
            param2 = null;
            param3 = null;
        } else if (params.length == 2) {
            // 方法有二个入参
            param1 = params[0];
            param2 = params[1];
            param3 = null;
        } else {
            // 方法有三个入参
            param1 = params[0];
            param2 = params[1];
            param3 = params[2];
        }

        // 通过模块名和方法名，解析脚本步骤类型和方法类型的枚举值
        switch (ModuleTypeEnum.fromName(moduleName)) {
            case SQL:   // 执行sql类型的步骤
                autoStepVO.setModuleType(ModuleTypeEnum.SQL.getCode());
                if (SqlEnum.SQL_EXECUTE_BY_JSON.getName().equals(methodName)) {
                    // 脚本范例：auto.sql.executeByJson("json")
                    autoStepVO.setMethodType(SqlEnum.SQL_EXECUTE_BY_JSON.getCode());
                } else {
                    // 脚本范例：auto.sql.dbName("sql")
                    autoStepVO.setMethodType(SqlEnum.DB_NAME.getCode());
                    autoStepVO.setMethodId(resourceMapper.selectDataSource(methodName, projectId).getId());
                }
                break;
            case PO:  // 被封装的方法
                autoStepVO.setModuleType(ModuleTypeEnum.PO.getCode());
                if (PoEnum.PO_EXECUTE_BY_JSON.getName().equals(methodName)) {
                    // 脚本范例：auto.po.executeByJson("json")
                    autoStepVO.setMethodType(PoEnum.PO_EXECUTE_BY_JSON.getCode());
                } else {
                    // 脚本范例：auto.po.poName("param1","param2","param3")
                    autoStepVO.setMethodType(PoEnum.PO_NAME.getCode());
                    autoStepVO.setMethodId(autoCaseMapper.selectPo(methodName, supperCaseId).getId());
                }
                break;
            case UI:    // ui类型的步骤
                autoStepVO.setModuleType(ModuleTypeEnum.UI.getCode());
                switch (UiEnum.fromName(methodName)) {
                    case OPEN_URL:  // 脚本范例：auto.ui.openUrl("url")
                        autoStepVO.setMethodType(UiEnum.OPEN_URL.getCode());
                        break;
                    case CLICK: // 脚本范例：auto.ui.click("xpath") 或 auto.ui.click("xpath", "1");
                        autoStepVO.setMethodType(UiEnum.CLICK.getCode());
                        break;
                    case CLICK_BY_JS:
                        autoStepVO.setMethodType(UiEnum.CLICK_BY_JS.getCode());
                        break;
                    case CLICK_BY_MOVE:
                        autoStepVO.setMethodType(UiEnum.CLICK_BY_MOVE.getCode());
                        break;
                    case SEND_KEY:  // 脚本范例：auto.ui.sendKey("key") 或 auto.ui.sendKey("xpath","key") 或 auto.ui.sendKey("xpath","key", "index")
                        autoStepVO.setMethodType(UiEnum.SEND_KEY.getCode());
                        break;
                    case SEND_KEY_BY_ENTER:
                        autoStepVO.setMethodType(UiEnum.SEND_KEY_BY_ENTER.getCode());
                        break;
                    case SWITCH_TAB:  // 脚本范例：auto.ui.switchTab()
                        autoStepVO.setMethodType(UiEnum.SWITCH_TAB.getCode());
                        break;
                    case MOVE: // 脚本范例：auto.ui.move("xpath")
                        autoStepVO.setMethodType(UiEnum.MOVE.getCode());
                        break;
                    case CLEAR_COOKIES:
                        autoStepVO.setMethodType(UiEnum.CLEAR_COOKIES.getCode());
                        break;
                    case DRAG:
                        autoStepVO.setMethodType(UiEnum.DRAG.getCode());
                        break;
                    case EXECUTE_JS:
                        autoStepVO.setMethodType(UiEnum.EXECUTE_JS.getCode());
                        break;
                }
                break;
            case HTTP:  // http类型的步骤
                autoStepVO.setModuleType(ModuleTypeEnum.HTTP.getCode());
                switch (HttpEnum.fromName(methodName)) {
                    case GET:   // 脚本范例：auto.http.get("url")
                        autoStepVO.setMethodType(HttpEnum.GET.getCode());
                        break;
                    case POST:  // 脚本范例：auto.http.post("url","body")
                        autoStepVO.setMethodType(HttpEnum.POST.getCode());
                        break;
                    case PUT:   // 脚本范例：auto.http.put("url","body")
                        autoStepVO.setMethodType(HttpEnum.PUT.getCode());
                        break;
                    case DELETE:    // 脚本范例：auto.http.delete("url","body")
                        autoStepVO.setMethodType(HttpEnum.DELETE.getCode());
                        break;
                }
                break;
            case RPC:   // rpc类型的步骤
                autoStepVO.setModuleType(ModuleTypeEnum.RPC.getCode());
                if (RpcEnum.RPC_EXECUTE_BY_JSON.getName().equals(methodName)) {
                    // 脚本范例：auto.rpc.executeByJson("json")
                    autoStepVO.setMethodType(RpcEnum.RPC_EXECUTE_BY_JSON.getCode());
                } else {
                    // 脚本范例：auto.rpc.invoke("uri","paramType","paramValue")
                    autoStepVO.setMethodType(RpcEnum.INVOKE.getCode());
                }
                break;
            case UTIL:  // 工具类型的步骤
                autoStepVO.setModuleType(ModuleTypeEnum.UTIL.getCode());
                switch (UtilEnum.fromName(methodName)) {
                    case SLEEP: // 脚本范例：auto.util.sleep("1")
                        autoStepVO.setMethodType(UtilEnum.SLEEP.getCode());
                        break;
                    case GET_JSON: // 脚本范例：auto.util.getJson("key","json")
                        autoStepVO.setMethodType(UtilEnum.GET_JSON.getCode());
                        break;
                    case GET_JSON_ANY: // 脚本范例：auto.util.getJsonAny("key","json")
                        autoStepVO.setMethodType(UtilEnum.GET_JSON_ANY.getCode());
                        break;
                    case GET_TIME: // 脚本范例：auto.util.getTime()
                        autoStepVO.setMethodType(UtilEnum.GET_TIME.getCode());
                        break;
                    case GET_RANDOM: // 脚本范例：auto.util.getJsonAny("1","100")
                        autoStepVO.setMethodType(UtilEnum.GET_RANDOM.getCode());
                        break;
                }
                break;
            case ASSERTION:  // 断言类型的步骤
                autoStepVO.setModuleType(ModuleTypeEnum.ASSERTION.getCode());
                switch (AssertionEnum.fromName(methodName)) {
                    case IS_EQUALS: // 脚本范例：auto.assertion.isEquals("actual","expect")
                        autoStepVO.setMethodType(AssertionEnum.IS_EQUALS.getCode());
                        break;
                    case IS_DELETED: // 脚本范例：auto.assertion.isDelete("actual")
                        autoStepVO.setMethodType(AssertionEnum.IS_DELETED.getCode());
                        break;
                    case IS_NOT_DELETED: // 脚本范例：auto.assertion.isNotDelete("actual")
                        autoStepVO.setMethodType(AssertionEnum.IS_NOT_DELETED.getCode());
                        break;
                    case IS_GREATER: // 脚本范例：auto.assertion.isGreater("actual","expect")
                        autoStepVO.setMethodType(AssertionEnum.IS_GREATER.getCode());
                        break;
                    case IS_SMALLER: // 脚本范例：auto.assertion.isSmaller("actual","expect")
                        autoStepVO.setMethodType(AssertionEnum.IS_SMALLER.getCode());
                        break;
                    case IS_CONTAINS: // 脚本范例：auto.assertion.isContains("actual","expect")
                        autoStepVO.setMethodType(AssertionEnum.IS_CONTAINS.getCode());
                        break;
                    case IS_BE_CONTAINS: // 脚本范例：auto.assertion.isBeContains("actual","expect")
                        autoStepVO.setMethodType(AssertionEnum.IS_BE_CONTAINS.getCode());
                        break;
                    case IS_XPATH_EXIST: // 脚本范例：auto.assertion.isXpathExist("actual")
                        autoStepVO.setMethodType(AssertionEnum.IS_XPATH_EXIST.getCode());
                        break;
                    case IS_XPATH_NOT_EXIST: // 脚本范例：auto.assertion.isXpathNotExist("actual")
                        autoStepVO.setMethodType(AssertionEnum.IS_XPATH_NOT_EXIST.getCode());
                        break;
                }
                break;
            default:
                autoStepVO.setModuleType(ModuleTypeEnum.UNDEFINED_MODULE.getCode());
                break;
        }
        // 设置方法名称
        autoStepVO.setMethodName(methodName);
        // 设置方法入参
        autoStepVO.setParameter1(param1);
        autoStepVO.setParameter2(param2);
        autoStepVO.setParameter3(param3);
        return autoStepVO;
    }

}
