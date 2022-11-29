package com.luoys.upgrade.toolservice.service;

import com.luoys.upgrade.toolservice.service.common.StringUtil;
import com.luoys.upgrade.toolservice.service.common.ThreadPoolUtil;
import com.luoys.upgrade.toolservice.dao.AutoStepMapper;
import com.luoys.upgrade.toolservice.dao.UserMapper;
import com.luoys.upgrade.toolservice.service.client.DBClient;
import com.luoys.upgrade.toolservice.service.client.HTTPClient;
import com.luoys.upgrade.toolservice.service.client.RPCClient;
import com.luoys.upgrade.toolservice.service.client.UIClient;
import com.luoys.upgrade.toolservice.service.dto.*;
import com.luoys.upgrade.toolservice.service.enums.*;
import com.luoys.upgrade.toolservice.service.enums.autoStep.methodType.*;
import com.luoys.upgrade.toolservice.service.enums.autoStep.ModuleTypeEnum;
import com.luoys.upgrade.toolservice.service.transform.TransformAutoStep;
import com.luoys.upgrade.toolservice.web.vo.AutoStepVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

/**
 * 步骤服务，包含自动化步骤相关的所有业务逻辑
 *
 * @author luoys
 */
@Slf4j
@Service
public class StepService {

    private static final int MULTIPLE_LIMIT = 30;

    @Autowired
    private AutoStepMapper autoStepMapper;

    @Autowired
    private ResourceService resourceService;

//    @Autowired
//    private UserMapper userMapper;
//
//    @Autowired
//    private HTTPClient httpClient;
//
//    @Autowired
//    private RPCClient rpcClient;
//
//    @Autowired
//    private DBClient dbClient;
//
//    @Autowired
//    private UIClient uiClient;

//    @DubboReference
//    private UserService userService;

    /**
     * 创建单个步骤
     *
     * @param autoStepVO 步骤对象
     * @return 成功为true，失败为false
     */
    public Boolean create(AutoStepVO autoStepVO) {
//        if (!autoStepVO.getIsPublic()) {
//            autoStepVO.setIsPublic(false);
//        }
//        autoStepVO.setStepId(NumberSender.createStepId());
//        if (autoStepVO.getOwnerId().equals(KeywordEnum.DEFAULT_USER.getCode().toString())) {
//            autoStepVO.setOwnerName(KeywordEnum.DEFAULT_USER.getValue());
//        } else {
//            String userName = userMapper.selectByUUId(autoStepVO.getOwnerId()).getUserName();
//            autoStepVO.setOwnerName(userName);
//        }
        int result = autoStepMapper.insert(TransformAutoStep.transformVO2PO(autoStepVO));
        return result == 1;
    }

    /**
     * 快速创建单个步骤
     *
     * @return 成功则返回stepId
     */
    public Integer quickCreate() {
        AutoStepVO autoStepVO = new AutoStepVO();
//        autoStepVO.setStepId(NumberSender.createStepId());
        // 设置默认值
        autoStepVO.setName(KeywordEnum.DEFAULT_STEP_NAME.getValue());
//        autoStepVO.setIsPublic(false);
//        autoStepVO.setOwnerId(KeywordEnum.DEFAULT_USER.getCode().toString());
//        autoStepVO.setOwnerName(KeywordEnum.DEFAULT_USER.getValue());
//        autoStepVO.setType(AutoStepTypeEnum.STEP_SQL.getCode());
//        autoStepVO.setAssertType(AssertTypeEnum.NO_ASSERT.getCode());
        autoStepVO.setModuleType(ModuleTypeEnum.UTIL.getCode());
        autoStepMapper.insert(TransformAutoStep.transformVO2PO(autoStepVO));
        return autoStepVO.getStepId();
    }

    /**
     * 逻辑删除单个步骤
     *
     * @param stepId 步骤业务id
     * @return 成功为true，失败为false
     */
    public Boolean remove(Integer stepId) {
        int result = autoStepMapper.remove(stepId);
        return result == 1;
    }

    /**
     * 更新单个步骤
     *
     * @param autoStepVO 步骤对象
     * @return 成功为true，失败为false
     */
    public Boolean update(AutoStepVO autoStepVO) {
        int result = autoStepMapper.update(TransformAutoStep.transformVO2PO(autoStepVO));
        return result == 1;
    }

//    /**
//     * 查询步骤列表
//     *
//     * @param userId      用户id
//     * @param isOnlyOwner 是否只查自己
//     * @param type        类型
//     * @param name        名字
//     * @param pageIndex   页码
//     * @return 步骤列表
//     */
//    @Deprecated
//    public List<AutoStepSimpleVO> query(String userId,
//                                        Boolean isOnlyOwner,
//                                        Integer type,
//                                        String name,
//                                        Boolean isPublic,
//                                        Integer pageIndex) {
//        if (isOnlyOwner != null && isOnlyOwner) {
//            userId = null;
//        }
//        //数据库startIndex从0开始
//        int startIndex = (pageIndex - 1) * KeywordEnum.DEFAULT_PAGE_SIZE.getCode();
//        return TransformAutoStep.transformPO2VO(autoStepMapper.list(type, name, userId, isPublic, startIndex));
//    }
//
//    /**
//     * 查询步骤总数
//     *
//     * @param userId      用户id
//     * @param isOnlyOwner 是否只查自己
//     * @param type        类型
//     * @param name        名字
//     * @return 步骤列表
//     */
//    public Integer count(String userId,
//                         Boolean isOnlyOwner,
//                         Integer type,
//                         String name,
//                         Boolean isPublic) {
//        if (isOnlyOwner != null && isOnlyOwner) {
//            userId = null;
//        }
//        return autoStepMapper.count(type, name, userId, isPublic);
//    }

    /**
     * 查询步骤详情
     *
     * @param stepId 步骤业务id
     * @return 步骤对象
     */
    public AutoStepVO queryDetail(Integer stepId) {
        return TransformAutoStep.transformPO2VO(autoStepMapper.select(stepId));
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
        String param1 = autoStepVO.getParameter1();
        String param2 = autoStepVO.getParameter2();
        String param3 = autoStepVO.getParameter3();
        String methodName = autoStepVO.getMethodName();
        switch (ModuleTypeEnum.fromCode(autoStepVO.getModuleType())) {
            case UI:
                switch (UiEnum.fromCode(autoStepVO.getMethodType())) {
                    case CLICK:
                        if (StringUtil.isBlank(param2)) {
                            autoStepVO.setScript(this.buildScript(autoStepVO.getVarName(), ModuleTypeEnum.UI.getName(), methodName, param1));
                        } else {
                            autoStepVO.setScript(this.buildScript(autoStepVO.getVarName(), ModuleTypeEnum.UI.getName(), methodName, param1, param2));
                        }
                        break;
                    case OPEN_URL:
                    case CLICK_BY_JS:
                        autoStepVO.setScript(this.buildScript(autoStepVO.getVarName(), ModuleTypeEnum.UI.getName(), methodName, param1));
                        break;
                    case SEND_KEY:
                        autoStepVO.setScript(this.buildScript(autoStepVO.getVarName(), ModuleTypeEnum.UI.getName(), methodName, param1, param2));
                        break;
                    case SWITCH_TAB:
                        autoStepVO.setScript(this.buildScript(autoStepVO.getVarName(), ModuleTypeEnum.UI.getName(), methodName));
                        break;
                }
                break;
            case SQL:
                autoStepVO.setScript(this.buildScript(autoStepVO.getVarName(), ModuleTypeEnum.SQL.getName(), methodName, param1));
                break;
            case HTTP:
                switch (HttpEnum.fromCode(autoStepVO.getMethodType())) {
                    case GET:
                        if (StringUtil.isBlank(param2)) {
                            autoStepVO.setScript(this.buildScript(autoStepVO.getVarName(), ModuleTypeEnum.HTTP.getName(), methodName, param1));
                        } else {
                            autoStepVO.setScript(this.buildScript(autoStepVO.getVarName(), ModuleTypeEnum.HTTP.getName(), methodName, param1, param2));
                        }
                        break;
                    case POST: // post,put,delete 都重载两次，转换逻辑一样
                    case PUT:
                    case DELETE:
                        if (StringUtil.isBlank(param2) && StringUtil.isBlank(param3)) {
                            autoStepVO.setScript(this.buildScript(autoStepVO.getVarName(), ModuleTypeEnum.HTTP.getName(), methodName, param1));
                        } else if (StringUtil.isBlank(param2)) {
                            autoStepVO.setScript(this.buildScript(autoStepVO.getVarName(), ModuleTypeEnum.HTTP.getName(), methodName, param1, param2));
                        } else {
                            autoStepVO.setScript(this.buildScript(autoStepVO.getVarName(), ModuleTypeEnum.HTTP.getName(), methodName, param1, param2, param3));
                        }
                        break;
                }
                break;
        }
        return autoStepVO;
    }

//    private void toScript(AutoStepVO autoStepVO, String method) {
//        // auto.module.method() 或 auto.module.method("param1") 或 auto.module.method("param1", "param2") 或 auto.module.method("param1", "param2", "param3")
//        if (StringUtil.isBlank(autoStepVO.getParameter1()) && StringUtil.isBlank(autoStepVO.getParameter2()) && StringUtil.isBlank(autoStepVO.getParameter3())) {
//            autoStepVO.setScript(this.buildScript(autoStepVO.getVarName(), ModuleTypeEnum.UI.getName(), method));
//        } else if (StringUtil.isBlank(autoStepVO.getParameter2()) && StringUtil.isBlank(autoStepVO.getParameter3())) {
//            autoStepVO.setScript(this.buildScript(autoStepVO.getVarName(), ModuleTypeEnum.UI.getName(), method, autoStepVO.getParameter1()));
//        } else if ()
//    }

    /**
     * 拼接脚本
     * @param varName 变量名
     * @param module 模块名
     * @param method 方法名
     * @param parameters 入参
     * @return 步骤脚本
     */
    private String buildScript(String varName, String module, String method, String... parameters) {
        StringBuilder stringBuilder = new StringBuilder();
        if (StringUtil.isBlank(varName)) {
            stringBuilder.append("String ").append(varName).append(" = ");
        }
        stringBuilder.append("auto").append(module).append(method).append("(\"");
//        for (String param : parameters) {
//            stringBuilder.append("\"").append(param).append("\"");
//            stringBuilder.append(", ");
//        }
        for (int i = 0; i < parameters.length; i++) {
            stringBuilder.append("\"").append(parameters[i]).append("\"");
            if (i != parameters.length -1) {
                stringBuilder.append(", ");
            }
        }
//        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append("\")");
        return stringBuilder.toString();
    }

    /**
     * 转换步骤模式，将结构化步骤转换成脚本
     *
     * @param autoStepVO - 带脚本的完整步骤对象
     */
    public AutoStepVO change2UiMode(AutoStepVO autoStepVO) {
        if (autoStepVO == null) {
            return null;
        }
        // 脚本范例：auto.methodType.methodName(methodParam);
        String script = autoStepVO.getScript();
        // 截取模块名，取第1个'.'到第2个'.'之间的内容，如：auto.ui.click(xpath)会取ui
        String moduleName = script.substring(5, script.indexOf(".", 5));
        // 截取方法名，取第2个'.'到第1个'('之间的内容，如：auto.ui.click(xpath)会取click
        String methodName = script.substring(script.indexOf(".", 5) + 1, script.indexOf("("));
//        // 则要取数据库名(执行sql的方法需要)
//        String dbName = script.substring(9, script.indexOf("("));
        // 截取步骤入参，如：xpath (根据实际情况使用)
        String methodParam = script.substring(script.indexOf("(\"") + 2, script.lastIndexOf("\")"));
//        String methodParamNoString = script.substring(script.indexOf("(") + 1, script.lastIndexOf(");"));
        // 截取多个参数，如：("xpath","key") (根据实际情况使用)
        String[] params = methodParam.split("\",\\s{0,4}\"");

        // 步骤有赋值
        if (script.startsWith("String|string")) {
            // 取String和=之间的内容，再去空格
            String varName = script.substring(6, script.indexOf("=")).trim();
            autoStepVO.setVarName(varName);
        }
        // 直接设置方法名称
        autoStepVO.setMethodName(methodName);
        // 先根据步骤类型，再根据类型中的方法，进行步骤解析
        switch (ModuleTypeEnum.fromName(moduleName)) {
            case UI:    // ui类型的步骤
//                if (autoStepVO.getUi() == null) {
//                    autoStepVO.setUi(new UiDTO());
//                }
                autoStepVO.setModuleType(ModuleTypeEnum.UI.getCode());
                switch (UiEnum.fromName(methodName)) {
                    case OPEN_URL:  // 脚本范例：auto.ui.openUrl("url")
                        autoStepVO.setMethodType(UiEnum.OPEN_URL.getCode());
                        autoStepVO.setParameter1(methodParam);
//                        autoStepVO.setName(UiTypeEnum.OPEN_URL.getDescription());
//                        autoStepVO.getUi().setUrl(methodParamString);
//                        autoStepVO.getUi().setType(UiTypeEnum.OPEN_URL.getCode());
                        break;
                    case CLICK: // 脚本范例：auto.ui.click("xpath")
                        autoStepVO.setMethodType(UiEnum.CLICK.getCode());
                        autoStepVO.setParameter1(params[0]);
                        if (params.length != 1) {
                            autoStepVO.setParameter2(params[1]);
                        }
                        break;
                    case SEND_KEY:  // 脚本范例：auto.ui.sendKey("xpath","key")
                        autoStepVO.setMethodType(UiEnum.SEND_KEY.getCode());
                        autoStepVO.setParameter1(params[0]);
                        if (params.length == 3) {
                            autoStepVO.setParameter2(params[1]);
                            autoStepVO.setParameter2(params[2]);
                        } else if (params.length == 2) {
                            autoStepVO.setParameter2(params[1]);
                        }
//                        autoStepVO.setName(UiTypeEnum.SEND_KEY.getDescription());
//                        autoStepVO.getUi().setElement(params[0]);
//                        autoStepVO.getUi().setKey(params[1]);
//                        autoStepVO.getUi().setType(UiTypeEnum.SEND_KEY.getCode());
                        break;
                    case SWITCH_TAB:  // 脚本范例：auto.ui.switchTab()
                        autoStepVO.setMethodType(UiEnum.SWITCH_TAB.getCode());
//                        autoStepVO.setName(UiTypeEnum.SWITCH_FRAME.getDescription());
//                        autoStepVO.getUi().setElement(methodParamString);
//                        autoStepVO.getUi().setType(UiTypeEnum.SWITCH_FRAME.getCode());
                        break;
                    case MOVE: // 脚本范例：auto.ui.move("xpath")
                        autoStepVO.setMethodType(UiEnum.MOVE.getCode());
                        autoStepVO.setParameter1(methodParam);
//                        autoStepVO.setName(UiTypeEnum.HOVER.getDescription());
//                        autoStepVO.getUi().setElement(methodParamString);
//                        autoStepVO.getUi().setType(UiTypeEnum.HOVER.getCode());
                        break;
                }
                break;
            case SQL:   // 执行sql类型的步骤，脚本范例：auto.onePiece.execute("sql");
//                if (autoStepVO.getJdbc() == null) {
//                    autoStepVO.setJdbc(new JdbcDTO());
//                }
//                autoStepVO.setType(AutoStepTypeEnum.STEP_SQL.getCode());
                autoStepVO.setModuleType(ModuleTypeEnum.SQL.getCode());
                autoStepVO.setParameter1(methodParam);
                autoStepVO.setMethodId(resourceService.queryDetailByName(methodName).getResourceId());

//                // 数据源的连接信息固化，执行时不关联查询，在关联数据源或修改数据源时，要批量更新步骤里的数据源信息
//                autoStepVO.getJdbc().setDbName(dbName);
//                // 通过名称，在资源表中查询到数据库连接信息
//                DataSourceDTO dataSource = resourceService.queryDetailByName(dbName).getDataSource();
//                autoStepVO.getJdbc().setDriver(dataSource.getDriver());
//                autoStepVO.getJdbc().setUrl(dataSource.getUrl());
//                autoStepVO.getJdbc().setUsername(dataSource.getUsername());
//                autoStepVO.getJdbc().setPassword(dataSource.getPassword());
//                autoStepVO.getJdbc().setSql(methodParamString);
                break;
            case HTTP:  // http类型的步骤
//                if (autoStepVO.getHttpRequest() == null) {
//                    autoStepVO.setHttpRequest(new HttpRequestDTO());
//                }
//                autoStepVO.setType(AutoStepTypeEnum.STEP_HTTP.getCode());
                autoStepVO.setModuleType(ModuleTypeEnum.HTTP.getCode());
                switch (HttpEnum.fromName(methodName)) {
                    case GET:   // 脚本范例：auto.http.get("url")
                        autoStepVO.setMethodType(HttpEnum.GET.getCode());

//                        autoStepVO.setName(HttpTypeEnum.GET.getDescription());
//                        autoStepVO.getHttpRequest().setHttpURL(methodParamString);
//                        autoStepVO.getHttpRequest().setHttpType(HttpTypeEnum.GET.getValue());
                        break;
                    case POST:  // 脚本范例：auto.http.post("url","body")
                        autoStepVO.setMethodType(HttpEnum.POST.getCode());

//                        autoStepVO.setName(HttpTypeEnum.POST.getDescription());
//                        autoStepVO.getHttpRequest().setHttpURL(params[0]);
//                        if (params.length == 2) {
//                            autoStepVO.getHttpRequest().setHttpBody(params[1]);
//                        }
//                        autoStepVO.getHttpRequest().setHttpType(HttpTypeEnum.POST.getValue());
                        break;
                    case PUT:   // 脚本范例：auto.http.put("url","body")
                        autoStepVO.setMethodType(HttpEnum.PUT.getCode());

//                        autoStepVO.setName(HttpTypeEnum.PUT.getDescription());
//                        autoStepVO.getHttpRequest().setHttpURL(params[0]);
//                        if (params.length == 2) {
//                            autoStepVO.getHttpRequest().setHttpBody(params[1]);
//                        }
//                        autoStepVO.getHttpRequest().setHttpType(HttpTypeEnum.PUT.getValue());
                        break;
                    case DELETE:    // 脚本范例：auto.http.delete("url","body")
                        autoStepVO.setMethodType(HttpEnum.DELETE.getCode());

//                        autoStepVO.setName(HttpTypeEnum.DELETE.getDescription());
//                        autoStepVO.getHttpRequest().setHttpURL(params[0]);
//                        if (params.length == 2) {
//                            autoStepVO.getHttpRequest().setHttpBody(params[1]);
//                        }
//                        autoStepVO.getHttpRequest().setHttpType(HttpTypeEnum.DELETE.getValue());
                        break;
                }
                break;
            case RPC:   // rpc类型的步骤，脚本范例：auto.rpc.invoke("location","json")
                // todo rpc??
                autoStepVO.setModuleType(ModuleTypeEnum.RPC.getCode());
                break;
            case UTIL:  // 工具类型的步骤
//                if (autoStepVO.getUtil() == null) {
//                    autoStepVO.setUtil(new UtilDTO());
//                }
                autoStepVO.setModuleType(ModuleTypeEnum.UTIL.getCode());
                switch (UtilEnum.fromName(methodName)) {
                    case SLEEP:
                        autoStepVO.setMethodType(UtilEnum.SLEEP.getCode());

//                        autoStepVO.getUtil().setType(UtilTypeEnum.SLEEP.getCode());
//                        autoStepVO.getUtil().setParam1(methodParamNoString);
                        break;
                    case GET_JSON:
                        autoStepVO.setMethodType(UtilEnum.GET_JSON.getCode());

//                        autoStepVO.getUtil().setType(UtilTypeEnum.GET_JSON_VALUE.getCode());
//                        autoStepVO.getUtil().setParam1(params[0]);
//                        autoStepVO.getUtil().setParam2(params[1]);
                    case GET_JSON_ANY:
                        autoStepVO.setMethodType(UtilEnum.GET_JSON_ANY.getCode());

                    case GET_TIME:
                        autoStepVO.setMethodType(UtilEnum.GET_TIME.getCode());

//                        autoStepVO.getUtil().setType(UtilTypeEnum.GET_TIME.getCode());
//                        autoStepVO.getUtil().setParam1(methodParamNoString);
                    case GET_RANDOM:
                        autoStepVO.setMethodType(UtilEnum.GET_RANDOM.getCode());

//                        autoStepVO.getUtil().setType(UtilTypeEnum.GET_RANDOM_NUMBER.getCode());
                }
                break;
            case ASSERTION:
                // todo assertion
                autoStepVO.setModuleType(ModuleTypeEnum.ASSERTION.getCode());
                break;
            case PO:  // 被封装的方法
                // todo task??
                autoStepVO.setModuleType(ModuleTypeEnum.PO.getCode());
                break;
            default:
                autoStepVO.setModuleType(ModuleTypeEnum.UNDEFINED_MODULE.getCode());
                autoStepVO.setParameter1(script.length() <= 5000 ? script : "未知脚本过长，请自行检查");
                break;
        }
        return autoStepVO;
    }

    private void setParameters(AutoStepVO autoStepVO, String param) {
        String[] params = param.split("(\",\\s{0,4}\")");
    }

//    /**
//     * 使用单个步骤（异步模式）
//     *
//     * @param autoStepVO 步骤对象
//     * @return 使用结果，执行成功且验证通过为true，失败或异常为false
//     */
//    public Boolean useAsync(AutoStepVO autoStepVO) throws RejectedExecutionException {
//        if (autoStepVO.getType().equals(AutoStepTypeEnum.STEP_UI.getCode())) {
//            log.error("--->UI步骤不支持单步调试：stepId={}", autoStepVO.getStepId());
//            return false;
//        }
//        ThreadPoolUtil.executeAPI(() -> execute(autoStepVO));
//        return true;
//    }
//
//    public Boolean use(AutoStepVO autoStepVO) {
//
//        // 如果是聚合步骤类型，则把子步骤全取出，并分别放入对于的列表中,否则直接方法elseSteps中
//        if (autoStepVO.getType().equals(AutoStepTypeEnum.STEP_MULTIPLE.getCode())) {
//            if (autoStepVO.getIfStepList().size() + autoStepVO.getThenStepList().size() + autoStepVO.getElseStepList().size() > MULTIPLE_LIMIT) {
//                log.warn("--->聚合步骤个数超过上限");
//                return null;
//            }
//            List<AutoStepVO> ifSteps = new ArrayList<>();
//            List<AutoStepVO> thenSteps = new ArrayList<>();
//            List<AutoStepVO> elseSteps = new ArrayList<>();
//            for (int i = 0; i < autoStepVO.getIfStepList().size(); i++) {
//                ifSteps.add(queryDetail(autoStepVO.getIfStepList().get(i).getStepId()));
//            }
//            for (int i = 0; i < autoStepVO.getThenStepList().size(); i++) {
//                thenSteps.add(queryDetail(autoStepVO.getThenStepList().get(i).getStepId()));
//            }
//            for (int i = 0; i < autoStepVO.getElseStepList().size(); i++) {
//                elseSteps.add(queryDetail(autoStepVO.getElseStepList().get(i).getStepId()));
//            }
//            // 当ifSteps列表有数据，且执行结果为true时，执行thenSteps列表，否则执行elseSteps列表
//            if (ifSteps.size() > 0 && executeList(ifSteps)) {
//                return executeList(thenSteps);
//            } else {
//                return executeList(elseSteps);
//            }
//        }
//
//        // 非聚合类型步骤，直接执行
//        return executeOne(autoStepVO);
//    }
//
//    /**
//     * 使用步骤列表
//     *
//     * @param stepList 步骤对象
//     * @return 全部执行成功且验证通过为true，只要有一个失败或异常为false
//     */
//    private Boolean executeList(List<AutoStepVO> stepList) {
//        boolean result = true;
//        for (AutoStepVO autoStepVO : stepList) {
//            // 只要有其中一个步骤验证结果为false，则整个步骤列表执行结果为false
//            if (!executeOne(autoStepVO)) {
//                result = false;
//            }
//        }
//        return result;
//    }
//
//    /**
//     * 执行一个步骤，并验证结果
//     * @param autoStepVO 步骤对象
//     * @return 验证成功为true，验证失败为false
//     */
//    private Boolean executeOne(AutoStepVO autoStepVO) {
//        try {
//            // 执行步骤并设置实际结果
//            autoStepVO.setAssertActual(execute(autoStepVO));
//            if (autoStepVO.getType().equals(AutoStepTypeEnum.STEP_UI.getCode()) && autoStepVO.getAfterSleep() > 0) {
//                Thread.sleep(autoStepVO.getAfterSleep() * 1000);
//            }
//        } catch (Exception e) {
//            log.error("--->步骤执行异常：stepId={}", autoStepVO.getStepId(), e);
//            return false;
//        }
//        return verify(autoStepVO);
//    }
//
//    private String execute(AutoStepVO autoStepVO) {
//        switch (AutoStepTypeEnum.fromCode(autoStepVO.getType())) {
//            case STEP_SQL:
//                //通过JdbcTemplate实现
//                return dbClient.execute(autoStepVO.getJdbc());
//            case STEP_HTTP:
//                TransformAutoStep.mergeParam(autoStepVO);
//                //通过restTemplate实现
//                return httpClient.execute(autoStepVO.getHttpRequest());
//            case STEP_RPC:
//                TransformAutoStep.mergeParam(autoStepVO);
//                //通过dubbo的泛化调用实现
//                return rpcClient.execute(autoStepVO.getRpc());
//            case STEP_UI:
//                TransformAutoStep.mergeParam(autoStepVO);
//                //通过selenium实现
//                return uiClient.execute(autoStepVO.getUi());
//            case STEP_MULTIPLE:
//                log.error("--->执行步骤异常，聚合类型步骤不能直接执行：stepId={}", autoStepVO.getStepId());
//                return null;
//            default:
//                return null;
//        }
//    }
//
//    /**
//     * 校验步骤执行结果，
//     * 如果步骤需要校验，则会写入实际结果和校验结果
//     *
//     * @param autoStepVO 步骤对象
//     * @return 如果无需校验或校验通过，则返回true；否则返回false
//     */
//    private Boolean verify(AutoStepVO autoStepVO) {
//        boolean result;
//        switch (AssertionEnum.fromCode(autoStepVO.getAssertType())) {
//            case NO_ASSERT:
//                return true;
//            case EQUALS:
//                result = autoStepVO.getAssertActual().equals(autoStepVO.getAssertExpect());
//                autoStepMapper.updateResult(autoStepVO.getStepId(), autoStepVO.getAssertActual(), result);
//                return result;
//            case CONTAINS:
//                result = autoStepVO.getAssertActual().contains(autoStepVO.getAssertExpect());
//                autoStepMapper.updateResult(autoStepVO.getStepId(), autoStepVO.getAssertActual(), result);
//                return result;
//            default:
//                log.error("--->未知步骤校验类型");
//                return false;
//        }
//    }

}
