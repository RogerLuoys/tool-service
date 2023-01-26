package com.luoys.upgrade.toolservice.service.client;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.luoys.upgrade.toolservice.dao.po.AutoStepPO;
import com.luoys.upgrade.toolservice.service.dto.CaseDTO;
import com.luoys.upgrade.toolservice.service.dto.DataSourceDTO;
import com.luoys.upgrade.toolservice.service.dto.StepDTO;
import com.luoys.upgrade.toolservice.service.enums.AutoCaseStatusEnum;
import com.luoys.upgrade.toolservice.service.enums.ConfigTypeEnum;
import com.luoys.upgrade.toolservice.service.enums.autoStep.ModuleTypeEnum;
import com.luoys.upgrade.toolservice.service.transform.TransformAutoStep;
import com.luoys.upgrade.toolservice.web.vo.AutoCaseVO;
import com.luoys.upgrade.toolservice.web.vo.AutoStepVO;
import com.luoys.upgrade.toolservice.web.vo.ConfigVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 步骤执行器，参考外观模式。
 * 每个执行自动化的线程对应一个执行器
 */
public class StepExecutor {

//    private DBClient sql = new DBClient();
//    private UIClient ui = new UIClient();
//    private HTTPClient http = new HTTPClient();
//    private RPCClient rpc = new RPCClient();
//    private UtilClient util = new UtilClient();
//    private AssertionClient assertion = new AssertionClient(ui);


    // 每个测试模块对应一个RootClient，主要是WebDriver会不同
    private Map<Integer, RootClient> clientMap = new HashMap<>();

    public StepExecutor() {}

//    public StepExecutor(AutoCaseVO autoCaseVO) {
//        List<ConfigVO> argument = autoCaseVO.getArgumentList();
//        // 非UI自动化，不用额外初始化
//        if (argument == null || argument.size() == 0) {
//            return;
//        }
//        // 是UI自动化，需要初始化对应的webdriver
//        switch (ConfigTypeEnum.fromCode(argument.get(0).getType())) {
//            case CHROME:
//                ui.initChrome();
//                break;
//            case FIREFOX:
//                break;
//            case ANDROID:
//                ui.initAndroid();
//                break;
//        }
//
//    }

    public void close() {
        for (Integer key: clientMap.keySet()) {
            clientMap.get(key).ui.quit();
        }
    }

//    public String execute(AutoStepPO autoStepPO) {
//        String varName = null;
//        switch (ModuleTypeEnum.fromCode(autoStepPO.getModuleType())) {
//            case PO:
//                break;
//            case SQL:
//                varName = sql.execute(autoStepPO);
//                break;
//            case RPC:
//                varName = rpc.execute(autoStepPO);
//                break;
//            case HTTP:
//                varName = http.execute(autoStepPO);
//                break;
//            case UI:
//                varName = ui.execute(autoStepPO);
//                break;
//            case UTIL:
//                varName = util.execute(autoStepPO);
//                break;
//            case ASSERTION:
//                varName = assertion.execute(autoStepPO).toString();
//                break;
//            default:
//                varName = "false";
//        }
//        return varName;
//    }
//    public String execute(AutoStepVO autoStepVO) {
//        return execute(TransformAutoStep.transformVO2PO(autoStepVO));
//    }

    public void execute(StepDTO stepDTO, RootClient auto) {
        String varName = null;
        switch (ModuleTypeEnum.fromCode(stepDTO.getModuleType())) {
            case PO:
                break;
            case SQL:
                varName = auto.sql.execute(stepDTO);
                break;
            case RPC:
                varName = auto.rpc.execute(stepDTO);
                break;
            case HTTP:
                varName = auto.http.execute(stepDTO);
                break;
            case UI:
                varName = auto.ui.execute(stepDTO);
                break;
            case UTIL:
                varName = auto.util.execute(stepDTO);
                break;
            case ASSERTION:
                varName = auto.assertion.execute(stepDTO);
                break;
            default:
                varName = "false";
        }
        stepDTO.setResult(varName);
    }

    public Boolean execute(CaseDTO caseDTO) {
        RootClient auto = null;
        if (clientMap.containsKey(caseDTO.getSupperCaseId())) {
            auto = clientMap.get(caseDTO.getSupperCaseId());
        } else {
            auto = new RootClient(caseDTO);
            clientMap.put(caseDTO.getSupperCaseId(), auto);
        }
        for (StepDTO oneStep: caseDTO.getBeforeSuite()) {
            execute(oneStep, auto);
            if (oneStep.getResult().equalsIgnoreCase("false")) {
                caseDTO.setStatus(AutoCaseStatusEnum.FAIL.getCode());
                return false;
            }
        }
        for (StepDTO oneStep: caseDTO.getSupperBeforeClass()) {
            execute(oneStep, auto);
            if (oneStep.getResult().equalsIgnoreCase("false")) {
                caseDTO.setStatus(AutoCaseStatusEnum.FAIL.getCode());
                return false;
            }
        }
        for (StepDTO oneStep: caseDTO.getBeforeTest()) {
            execute(oneStep, auto);
            if (oneStep.getResult().equalsIgnoreCase("false")) {
                caseDTO.setStatus(AutoCaseStatusEnum.FAIL.getCode());
                return false;
            }
        }
        for (StepDTO oneStep: caseDTO.getTest()) {
            execute(oneStep, auto);
            if (oneStep.getResult().equalsIgnoreCase("false")) {
                caseDTO.setStatus(AutoCaseStatusEnum.FAIL.getCode());
                return false;
            }
        }
        for (StepDTO oneStep: caseDTO.getAfterTest()) {
            execute(oneStep, auto);
        }
        for (StepDTO oneStep: caseDTO.getSupperAfterClass()) {
            execute(oneStep, auto);
        }
        for (StepDTO oneStep: caseDTO.getAfterSuite()) {
            execute(oneStep, auto);
        }
        return true;
    }
}
