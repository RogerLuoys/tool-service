package com.luoys.upgrade.toolservice.service;

import com.luoys.upgrade.toolservice.common.NumberSender;
import com.luoys.upgrade.toolservice.common.StringUtil;
import com.luoys.upgrade.toolservice.common.ThreadPoolUtil;
import com.luoys.upgrade.toolservice.dao.AutoCaseMapper;
import com.luoys.upgrade.toolservice.dao.CaseStepRelationMapper;
import com.luoys.upgrade.toolservice.dao.UserMapper;
import com.luoys.upgrade.toolservice.service.client.UIClient;
import com.luoys.upgrade.toolservice.service.enums.AutoCaseStatusEnum;
import com.luoys.upgrade.toolservice.service.enums.AutoCaseTypeEnum;
import com.luoys.upgrade.toolservice.service.enums.KeywordEnum;
import com.luoys.upgrade.toolservice.service.enums.RelatedStepTypeEnum;
import com.luoys.upgrade.toolservice.service.transform.TransformAutoStep;
import com.luoys.upgrade.toolservice.service.transform.TransformCaseStepRelation;
import com.luoys.upgrade.toolservice.web.vo.AutoCaseSimpleVO;
import com.luoys.upgrade.toolservice.web.vo.AutoCaseVO;
import com.luoys.upgrade.toolservice.service.transform.TransformAutoCase;
import com.luoys.upgrade.toolservice.web.vo.AutoStepVO;
import com.luoys.upgrade.toolservice.web.vo.CaseStepVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

/**
 * 用例服务，包含自动化用例相关的所有业务逻辑
 *
 * @author luoys
 */
@Slf4j
@Service
public class CaseService {

    @Autowired
    private AutoCaseMapper autoCaseMapper;

    @Autowired
    private CaseStepRelationMapper caseStepRelationMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StepService stepService;

    @Autowired
    private UIClient uiClient;

    /**
     * 新增单个用例
     *
     * @param autoCaseVO 用例对象
     * @return 新增成功为true，失败为false
     */
    public Boolean create(AutoCaseVO autoCaseVO) {
        if (autoCaseVO.getType() == null) {
            log.error("--->用例类型不能为空：{}", autoCaseVO);
            return false;
        }
        if (autoCaseVO.getStatus() == null) {
            log.error("--->用例状态不能为空：{}", autoCaseVO);
            return false;
        }
        if (StringUtil.isBlank(autoCaseVO.getName())) {
            log.error("--->用例名称不能为空：{}", autoCaseVO);
            return false;
        }
        autoCaseVO.setCaseId(NumberSender.createCaseId());
        if (autoCaseVO.getOwnerId().equals(KeywordEnum.DEFAULT_USER.getCode().toString())) {
            autoCaseVO.setOwnerName(KeywordEnum.DEFAULT_USER.getValue());
        } else {
            String userName = userMapper.selectByUUId(autoCaseVO.getOwnerId()).getUserName();
            autoCaseVO.setOwnerName(userName);
        }
        int result = autoCaseMapper.insert(TransformAutoCase.transformVO2PO(autoCaseVO));
        return result == 1;
    }

    /**
     * 快速新增单个用例
     *
     * @param autoCaseVO 用例对象，需要有名称和类型
     * @return 新增成功为true，失败为false
     */
    public String quickCreate(AutoCaseVO autoCaseVO) {
        if (autoCaseVO.getType() == null) {
            log.error("--->用例类型不能为空：{}", autoCaseVO);
            return null;
        }
        if (StringUtil.isBlank(autoCaseVO.getName())) {
            log.error("--->用例名称不能为空：{}", autoCaseVO);
            return null;
        }
        autoCaseVO.setStatus(AutoCaseStatusEnum.PLANNING.getCode());
        autoCaseVO.setCaseId(NumberSender.createCaseId());
        int result = autoCaseMapper.insert(TransformAutoCase.transformVO2PO(autoCaseVO));
        return result == 1 ? autoCaseVO.getCaseId() : null;
    }

    /**
     * 新增用例中关联的步骤
     *
     * @param caseStepVO 步骤对象
     * @return 成功为true，失败为false
     */
    public Boolean createRelatedStep(CaseStepVO caseStepVO) {
        // 如果关联步骤为空，则先快速创建一个步骤
        if (StringUtil.isBlank(caseStepVO.getStepId())) {
            caseStepVO.setStepId(stepService.quickCreate());
        }
        int result = caseStepRelationMapper.insert(TransformCaseStepRelation.transformVO2PO(caseStepVO));
        return result == 1;
    }

    /**
     * 复制用例的基本信息和关联信息
     * @param autoCaseVO 用例详情
     * @return 执行无报错为true
     */
    public Boolean copyCase(AutoCaseVO autoCaseVO) {
        // uuid不能copy，需要重新生成
        String caseId = NumberSender.createCaseId();
        autoCaseVO.setCaseId(caseId);
        // copy基本信息
        autoCaseMapper.insert(TransformAutoCase.transformVO2PO(autoCaseVO));
        List<CaseStepVO> caseStepList = new ArrayList<>();
        caseStepList.addAll(autoCaseVO.getPreStepList());
        caseStepList.addAll(autoCaseVO.getMainStepList());
        caseStepList.addAll(autoCaseVO.getAfterStepList());
        // 更新关联对象中的caseId
        for (int i = 0; i < caseStepList.size(); i++) {
            caseStepList.get(i).setCaseId(caseId);
        }
        caseStepRelationMapper.batchInsert(TransformCaseStepRelation.transformVO2PO(caseStepList));
        return true;
    }

    /**
     * 逻辑删除单个用例
     *
     * @param caseId 用例业务id
     * @return 删除成功为true，失败为false
     */
    public Boolean remove(String caseId) {
        caseStepRelationMapper.removeByCaseId(caseId);
        int result = autoCaseMapper.remove(caseId);
        return result == 1;
    }

    /**
     * 删除用例中关联的步骤
     *
     * @param caseStepVO 步骤对象
     * @return 成功为true，失败为false
     */
    public Boolean removeRelatedStep(CaseStepVO caseStepVO) {
        int result = caseStepRelationMapper.remove(TransformCaseStepRelation.transformVO2PO(caseStepVO));
        return result == 1;
    }

    /**
     * 更新单个用例
     *
     * @param autoCaseVO 用例对象
     * @return 更新成功为true，失败为false
     */
    public Boolean update(AutoCaseVO autoCaseVO) {
        int result = autoCaseMapper.update(TransformAutoCase.transformVO2PO(autoCaseVO));
        return result == 1;
    }

    /**
     * 更新用例关联步骤的顺序
     *
     * @param caseStepVO 步骤对象
     * @return 成功为true，失败为false
     */
    public Boolean updateRelatedStep(CaseStepVO caseStepVO) {
        int result = caseStepRelationMapper.update(TransformCaseStepRelation.transformVO2PO(caseStepVO));
        return result == 1;
    }

    /**
     * 更新用例关联步骤的顺序
     *
     * @param caseId 用例ID
     * @param stepId 步骤ID
     * @param sequence 顺序，从1开始
     * @return 成功为true，失败为false
     */
    public Boolean updateRelatedStep(String caseId, String stepId, int sequence) {
        CaseStepVO caseStepVO = new CaseStepVO();
        caseStepVO.setCaseId(caseId);
        caseStepVO.setStepId(stepId);
        caseStepVO.setSequence(sequence);
        return updateRelatedStep(caseStepVO);
    }

    /**
     * 查询用例列表
     *
     * @param userId      用户id
     * @param isOnlyOwner 是否只查自己
     * @param status      用例状态
     * @param name        用例名称
     * @param pageIndex   页码
     * @return 用例列表
     */
    public List<AutoCaseSimpleVO> query(String userId, Boolean isOnlyOwner, Integer status, String name, Integer pageIndex) {
        if (!isOnlyOwner) {
            userId = null;
        }
        int startIndex = (pageIndex - 1) * KeywordEnum.DEFAULT_PAGE_SIZE.getCode();
        return TransformAutoCase.transformPO2SimpleVO(autoCaseMapper.list(status, name, userId, startIndex));
    }

    public Integer count(String userId, Boolean isOnlyOwner, Integer status, String name) {
        if (!isOnlyOwner) {
            userId = null;
        }
        return autoCaseMapper.count(status, name, userId);
    }

    /**
     * 查询用例详情
     *
     * @param caseId 用例业务id
     * @return 用例对象
     */
    public AutoCaseVO queryDetail(String caseId) {
        // 查基本信息
        AutoCaseVO autoCaseVO = TransformAutoCase.transformPO2VO(autoCaseMapper.selectByUUID(caseId));
        // 查关联的步骤
        List<CaseStepVO> caseStepList = TransformCaseStepRelation.transformPO2VO(caseStepRelationMapper.listStepByCaseId(caseId));
        List<CaseStepVO> preList = new ArrayList<>();
        List<CaseStepVO> mainList = new ArrayList<>();
        List<CaseStepVO> afterList = new ArrayList<>();
        // 区分步骤类型
        for (CaseStepVO vo : caseStepList) {
            switch (RelatedStepTypeEnum.fromCode(vo.getType())) {
                case PRE_STEP:
                    preList.add(vo);
                    break;
                case MAIN_STEP:
                    mainList.add(vo);
                    break;
                case AFTER_STEP:
                    afterList.add(vo);
                    break;
            }
        }
        autoCaseVO.setPreStepList(preList);
        autoCaseVO.setMainStepList(mainList);
        autoCaseVO.setAfterStepList(afterList);
        return autoCaseVO;
    }


    /**
     * 转换用例模式，将结构化用例转换成脚本
     *
     * @param
     */
    public AutoCaseVO change2ScriptMode(AutoCaseVO autoCaseVO) {
        if (autoCaseVO == null) {
            return null;
        }
        StringBuilder steps = new StringBuilder();

        // 转换主要步骤
        for (CaseStepVO stepVO : autoCaseVO.getMainStepList()) {
            steps.append(stepService.change2ScriptMode(stepVO.getAutoStep()).getScript());
        }
        autoCaseVO.setMainSteps(steps.toString());
        steps.delete(0, steps.length());

        return autoCaseVO;
    }

    /**
     * 转换用例模式，将结构化用例转换成脚本，例
     * auto.ui.sendKey("xpath","key");
     * auto.dbName.execute("sql");
     * auto.assert.isContains("expect");
     * auto.http.post("url", "body");
     * auto.rpc.invoke("json");
     * auto.task.taskName("param1","param2");
     * auto.util.sleep("param1")
     *
     * @param autoCaseVO -
     */
    public AutoCaseVO change2UiMode(AutoCaseVO autoCaseVO) {

        // 通过正则解析脚本，把整段脚本解析成行
        List<String> mainSteps = StringUtil.getMatch("auto\\.(ui|http|db|rpc|util|steps)\\.\\w+\\(.*\\);|auto\\.\\w+\\.execute\\(.*\\);", autoCaseVO.getMainSteps());

        // 完全新增脚本，或脚本内步骤有新增，需要创建对应数量的关联步骤
        while (mainSteps.size() - autoCaseVO.getMainStepList().size() > 0) {
            CaseStepVO caseStepVO = new CaseStepVO();
            caseStepVO.setCaseId(autoCaseVO.getCaseId());
            // 先创建步骤，再将stepId关联上
            caseStepVO.setStepId(stepService.quickCreate());
            caseStepVO.setType(RelatedStepTypeEnum.MAIN_STEP.getCode());
            // 步骤顺序需要设置好
            caseStepVO.setSequence(autoCaseVO.getMainStepList().size() + 1);
            // 将关系存入数据库
            createRelatedStep(caseStepVO);
            // 创建步骤实例
            caseStepVO.setAutoStep(new AutoStepVO());
            // 填充步骤的stepId
            caseStepVO.getAutoStep().setStepId(caseStepVO.getStepId());
            // 将关联步骤的实例添加至用例对象中
            autoCaseVO.getMainStepList().add(caseStepVO);
        }

        // 脚本中删除了一些步骤，需要删除对应的关联关系
        while (mainSteps.size() - autoCaseVO.getMainStepList().size() < 0) {
            // 数据库中删最后一个步骤的关联关系
            removeRelatedStep(autoCaseVO.getMainStepList().get(autoCaseVO.getMainStepList().size() - 1));
            // 然后将最后一个步骤从对象中删除
            autoCaseVO.getMainStepList().remove(autoCaseVO.getMainStepList().size() - 1);
        }

        // 基于脚本，对脚本内的步骤做全量更新
        for (int i = 0; i< mainSteps.size(); i++) {
            // 将脚本塞入对应的步骤中
            autoCaseVO.getMainStepList().get(i).getAutoStep().setScript(mainSteps.get(i));
            // 基于脚本，将步骤转换为ui模式(会覆盖原数据)
            AutoStepVO autoStepVO = stepService.change2UiMode(autoCaseVO.getMainStepList().get(i).getAutoStep());
            // 重新设置步骤执行顺序
            updateRelatedStep(autoCaseVO.getCaseId(), autoStepVO.getStepId(), i + 1);
            stepService.update(autoStepVO);
        }

        return autoCaseVO;
    }


    /**
     * 使用用例（异步），并更新用例的执行结果
     *
     * @param autoCaseVO 用例对象
     * @return 主要步骤全部执行结果都为true才返回true
     */
    public Boolean useAsync(AutoCaseVO autoCaseVO) throws RejectedExecutionException {
        // UI用例和API用例使用不同线程池，UI自动化只能单个子线程
        if (autoCaseVO.getType().equals(AutoCaseTypeEnum.UI_CASE.getCode())) {
            ThreadPoolUtil.executeUI(() -> {
                log.info("--->执行ui用例的步骤：caseId={}", autoCaseVO.getCaseId());
                boolean result = executeUI(autoCaseVO);
                log.info("---->步骤执行完毕，更新用例结果：caseId={}, result={}", autoCaseVO.getCaseId(), result);
                autoCaseMapper.updateStatus(autoCaseVO.getCaseId(),
                        result ? AutoCaseStatusEnum.SUCCESS.getCode() : AutoCaseStatusEnum.FAIL.getCode());
            });
        } else {
            ThreadPoolUtil.executeAPI(() -> {
                log.info("--->执行api用例的步骤：caseId={}", autoCaseVO.getCaseId());
                boolean result = executeAPI(autoCaseVO);
                log.info("---->步骤执行完毕，更新用例结果：caseId={}, result={}", autoCaseVO.getCaseId(), result);
                autoCaseMapper.updateStatus(autoCaseVO.getCaseId(),
                        result ? AutoCaseStatusEnum.SUCCESS.getCode() : AutoCaseStatusEnum.FAIL.getCode());
            });
        }
        return true;
    }

    /**
     * 使用用例 (仅执行，不更新结果)
     *
     * @param autoCaseVO 用例对象
     * @return 主要步骤全部执行结果都为true才返回true
     */
    public Boolean use(AutoCaseVO autoCaseVO) {
        boolean result;
        // UI和接口用例分开执行
        if (autoCaseVO.getType().equals(AutoCaseTypeEnum.UI_CASE.getCode())) {
            result = executeUI(autoCaseVO);
        } else {
            result = executeAPI(autoCaseVO);
        }
        log.info("--->执行用例完成：caseId={}, caseName={}, result={}", autoCaseVO.getCaseId(), autoCaseVO.getName(), result);
        return result;
    }

    /**
     * 执行接口自动化用例
     *
     * @param autoCaseVO 用例对象
     * @return 主要步骤全部执行结果都为true才返回true
     */
    private Boolean executeAPI(AutoCaseVO autoCaseVO) {
        try {
            return execute(autoCaseVO);
        } catch (Exception e) {
            log.error("--->执行api用例异常：caseId={}", autoCaseVO.getCaseId(), e);
            return false;
        }
    }

    /**
     * 执行UI自动化用例
     *
     * @param autoCaseVO 用例对象
     * @return 主要步骤全部执行结果都为true才返回true
     */
    private Boolean executeUI(AutoCaseVO autoCaseVO) {
        boolean result;
        try {
            // 打开webDrive，默认chrome
            uiClient.init();
            // 执行包含ui步骤的用例
            result = execute(autoCaseVO);
        } catch (Exception e) {
            log.error("--->执行ui用例异常：caseId={}", autoCaseVO.getCaseId(), e);
            return false;
        } finally {
            // 退出webDrive
            uiClient.quit();
        }
        return result;
    }

    private Boolean execute(AutoCaseVO autoCaseVO) {
        boolean result = true;
        // 执行前置步骤
        if (autoCaseVO.getPreStepList() != null && autoCaseVO.getPreStepList().size() != 0) {
            for (CaseStepVO vo : autoCaseVO.getPreStepList()) {
                vo.getAutoStep().setEnvironment(StringUtil.isBlank(autoCaseVO.getEnvironment()) ? null : autoCaseVO.getEnvironment());
                stepService.use(vo.getAutoStep());
            }
        }
        // 执行主要步骤，只要有一个步骤为false，则整个case结果为false
        if (autoCaseVO.getMainStepList() != null && autoCaseVO.getMainStepList().size() != 0) {
            for (CaseStepVO vo : autoCaseVO.getMainStepList()) {
                vo.getAutoStep().setEnvironment(StringUtil.isBlank(autoCaseVO.getEnvironment()) ? null : autoCaseVO.getEnvironment());
                if (!stepService.use(vo.getAutoStep())) {
                    result = false;
                }
            }
        } else {
            log.error("--->用例没有主步骤：caseId={}", autoCaseVO.getCaseId());
            return false;
        }
        // 执行收尾步骤，如果执行结果为false，则不执行该步骤保留现场
        if (result && autoCaseVO.getAfterStepList() != null && autoCaseVO.getAfterStepList().size() != 0) {
            for (CaseStepVO vo : autoCaseVO.getAfterStepList()) {
                vo.getAutoStep().setEnvironment(StringUtil.isBlank(autoCaseVO.getEnvironment()) ? null : autoCaseVO.getEnvironment());
                stepService.use(vo.getAutoStep());
            }
        }
        return result;
    }

}
