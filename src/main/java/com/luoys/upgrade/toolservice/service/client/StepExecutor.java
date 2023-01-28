package com.luoys.upgrade.toolservice.service.client;

import com.luoys.upgrade.toolservice.service.common.StringUtil;
import com.luoys.upgrade.toolservice.service.dto.CaseDTO;
import com.luoys.upgrade.toolservice.service.dto.StepDTO;
import com.luoys.upgrade.toolservice.service.enums.AutoCaseStatusEnum;
import com.luoys.upgrade.toolservice.service.enums.autoStep.ModuleTypeEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 步骤执行器，参考外观模式。
 * 每个执行自动化的线程对应一个执行器
 */
public class StepExecutor {

    // 每个测试模块对应一个RootClient，主要是WebDriver会不同
    private final Map<Integer, RootClient> clientMap = new HashMap<>();
    private final Map<Integer, List<StepDTO>> afterSuiteMap = new HashMap<>();

    public void execute(StepDTO stepDTO, RootClient auto) {
        String varName;
        switch (ModuleTypeEnum.fromCode(stepDTO.getModuleType())) {
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

    public void close() {
        for (Integer key: clientMap.keySet()) {
            // 先执行超类中的@AfterSuite
            if (afterSuiteMap.get(key) != null) {
                for (StepDTO oneStep: afterSuiteMap.get(key)) {
                    execute(oneStep, clientMap.get(key));
                }
            }
            // 再关闭webdriver相关资源
            clientMap.get(key).ui.quit();
        }
    }

    private void mergeParam(StepDTO stepDTO, Map<String, String> params) {
        if (params == null || params.size() == 0) {
            return;
        }
        List<String> varNames1 = stepDTO.getParameter1() == null ? new ArrayList<>() : StringUtil.getMatch("\\$\\{\\w{1,20}}", stepDTO.getParameter1());
        List<String> varNames2 = stepDTO.getParameter2() == null ? new ArrayList<>() : StringUtil.getMatch("\\$\\{\\w{1,20}}", stepDTO.getParameter2());
        List<String> varNames3 = stepDTO.getParameter3() == null ? new ArrayList<>() : StringUtil.getMatch("\\$\\{\\w{1,20}}", stepDTO.getParameter3());
        if (varNames1.size() != 0) {
            for (String varName: varNames1) {
                stepDTO.setParameter1(stepDTO.getParameter1().replace(varName, params.get(varName)));
            }
        }
        if (varNames2.size() != 0) {
            for (String varName: varNames2) {
                stepDTO.setParameter2(stepDTO.getParameter2().replace(varName, params.get(varName)));
            }
        }
        if (varNames3.size() != 0) {
            for (String varName: varNames3) {
                stepDTO.setParameter3(stepDTO.getParameter3().replace(varName, params.get(varName)));
            }
        }
    }

    private boolean execute(List<StepDTO> steps, RootClient auto) {
        if (steps == null || steps.size() == 0) {
            return true;
        }
        Map<String, String> params = new HashMap<>();
        for (StepDTO oneStep: steps) {
            // 变量替换
            mergeParam(oneStep, params);
            execute(oneStep, auto);
            if (oneStep.getResult().equalsIgnoreCase("false")) {
                return false;
            }
            // 赋值
            if (!StringUtil.isBlank(oneStep.getVarName())) {
                params.put("${" + oneStep.getVarName() + "}", oneStep.getResult());
            }
        }
        return true;
    }

    public Boolean execute(CaseDTO caseDTO) {
        RootClient auto;
        // 通过超类ID，找到对应的执行客户端
        if (clientMap.containsKey(caseDTO.getSupperCaseId())) {
            auto = clientMap.get(caseDTO.getSupperCaseId());
        } else {
            auto = new RootClient(caseDTO);
            clientMap.put(caseDTO.getSupperCaseId(), auto);
            afterSuiteMap.put(caseDTO.getSupperCaseId(), caseDTO.getAfterSuite());
        }
        // 套件执行异常，直接跳过所有步骤
        if (auto.suiteError) {
            caseDTO.setStatus(AutoCaseStatusEnum.FAIL.getCode());
            return false;
        }
        // 步骤为空，直接失败
        if (caseDTO.getTest() == null || caseDTO.getTest().size() == 0) {
            caseDTO.setStatus(AutoCaseStatusEnum.FAIL.getCode());
            return false;
        }

        // 执行@BeforeSuite，只执行一次
        if (!auto.isBeforeSuiteDone) {
            if (!execute(caseDTO.getBeforeSuite(), auto)) {
                caseDTO.setStatus(AutoCaseStatusEnum.FAIL.getCode());
                return false;
            }
            auto.isBeforeSuiteDone = true;
        }
        // 执行超类中的@BeforeTest
        if (!execute(caseDTO.getSupperBeforeTest(), auto)) {
            caseDTO.setStatus(AutoCaseStatusEnum.FAIL.getCode());
            return false;
        }
        // 执行测试类中的@BeforeTest
        if (!execute(caseDTO.getBeforeTest(), auto)) {
            caseDTO.setStatus(AutoCaseStatusEnum.FAIL.getCode());
            return false;
        }
        // 执行测试类中的@Test
        if (!execute(caseDTO.getTest(), auto)) {
            caseDTO.setStatus(AutoCaseStatusEnum.FAIL.getCode());
            return false;
        }
        // @Test中的步骤全部执行完成且都为true，则用例执行成功
        caseDTO.setStatus(AutoCaseStatusEnum.SUCCESS.getCode());
        // 执行测试类中的@AfterTest
        execute(caseDTO.getAfterTest(), auto);
        // 执行超类中的@AfterTest
        execute(caseDTO.getAfterTest(), auto);
        return true;
    }

}
