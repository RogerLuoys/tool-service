package com.luoys.upgrade.toolservice.service.automation;

import com.luoys.upgrade.toolservice.service.automation.client.RootClient;
import com.luoys.upgrade.toolservice.service.common.StringUtil;
import com.luoys.upgrade.toolservice.service.dto.CaseDTO;
import com.luoys.upgrade.toolservice.service.dto.StepDTO;
import com.luoys.upgrade.toolservice.service.enums.AutoCaseStatusEnum;
import com.luoys.upgrade.toolservice.service.enums.DefaultEnum;
import com.luoys.upgrade.toolservice.service.enums.autoStep.ModuleTypeEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自动化执行器，参考外观模式。
 * 每个执行自动化的线程只实例化一个执行器
 */
public class AutoExecutor {

    // 一个套件都可能包含多个测试模块，每个测试模块(超类)需对应一个RootClient，主要是WebDriver会不同
    private final Map<Integer, RootClient> clientMap = new HashMap<>();
    private final Map<Integer, List<StepDTO>> afterSuiteMap = new HashMap<>();

    /**
     *  使用指定客户端执行单个步骤
     */
    private void executeStep(StepDTO stepDTO, RootClient auto) {
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
                varName = DefaultEnum.DEFAULT_CLIENT_ERROR.getValue();
        }
        stepDTO.setResult(varName);
    }

    /**
     * 处理入参中的变量(相当于传参)
     * @param params 包含变量名和变量值的映射关系
     */
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

    /**
     * 执行步骤列表(相当于执行一个方法)
     */
    private boolean executeSteps(List<StepDTO> steps, RootClient auto) {
        if (steps == null || steps.size() == 0) {
            return true;
        }
        Map<String, String> params = new HashMap<>();
        for (StepDTO oneStep: steps) {
            // 局部变量赋值
            mergeParam(oneStep, params);
            // 执行步骤
            executeStep(oneStep, auto);
            if (oneStep.getResult().equals(DefaultEnum.DEFAULT_CLIENT_ERROR.getValue())) {
                return false;
            }
            // 赋值
            if (!StringUtil.isBlank(oneStep.getVarName())) {
                params.put("${" + oneStep.getVarName() + "}", oneStep.getResult());
            }
        }
        return true;
    }

    /**
     * 执行一个测试用例
     */
    public Boolean executeCase(CaseDTO caseDTO) {
        RootClient auto;
        if (clientMap.containsKey(caseDTO.getSupperCaseId())) {
            // 通过超类ID，找到对应的执行客户端
            auto = clientMap.get(caseDTO.getSupperCaseId());
        } else {
            // 如果未找到，说明改超类下的用例从未执行过，需要实例化一个客户端
            auto = new RootClient(caseDTO);
            clientMap.put(caseDTO.getSupperCaseId(), auto);
            afterSuiteMap.put(caseDTO.getSupperCaseId(), caseDTO.getAfterSuite());
        }
        // 套件执行异常，直接失败
        if (auto.isBeforeSuiteError) {
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
            if (!executeSteps(caseDTO.getBeforeSuite(), auto)) {
                caseDTO.setStatus(AutoCaseStatusEnum.FAIL.getCode());
                auto.isBeforeSuiteError = true;
                return false;
            }
            auto.isBeforeSuiteDone = true;
        }
        // 执行超类中的@BeforeClass
        if (!executeSteps(caseDTO.getSupperBeforeClass(), auto)) {
            caseDTO.setStatus(AutoCaseStatusEnum.FAIL.getCode());
            return false;
        }
        // 执行测试类中的@BeforeClass
        if (!executeSteps(caseDTO.getBeforeClass(), auto)) {
            caseDTO.setStatus(AutoCaseStatusEnum.FAIL.getCode());
            return false;
        }
        // 执行测试类中的@Test
        if (!executeSteps(caseDTO.getTest(), auto)) {
            caseDTO.setStatus(AutoCaseStatusEnum.FAIL.getCode());
            return false;
        }
        // @Test中的步骤全部执行完成且都为true，则用例执行成功
        caseDTO.setStatus(AutoCaseStatusEnum.SUCCESS.getCode());
        // 执行测试类中的@AfterClass
        executeSteps(caseDTO.getAfterClass(), auto);
        // 执行超类中的@AfterClass
        executeSteps(caseDTO.getSupperAfterClass(), auto);
        return true;
    }

    /**
     * 执行@AfterSuite，然后关闭执行器申请的所有资源
     */
    public void close() {
        // 一个套件可能会有多个测试模块(超类)，每个模块申请的客户端都要一一关闭
        for (Integer key: clientMap.keySet()) {
            // 执行超类中的@AfterSuite
            if (afterSuiteMap.get(key) != null) {
                for (StepDTO oneStep: afterSuiteMap.get(key)) {
                    executeStep(oneStep, clientMap.get(key));
                }
            }
            // 关闭申请过的资源
            clientMap.get(key).http.close();
            clientMap.get(key).ui.close();
        }
    }

}
