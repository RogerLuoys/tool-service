package com.luoys.upgrade.toolservice.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.luoys.upgrade.toolservice.dao.ConfigMapper;
import com.luoys.upgrade.toolservice.dao.po.AutoCasePO;
import com.luoys.upgrade.toolservice.dao.po.AutoCaseQueryPO;
import com.luoys.upgrade.toolservice.dao.po.CaseStepRelationPO;
import com.luoys.upgrade.toolservice.dao.po.ConfigPO;
import com.luoys.upgrade.toolservice.service.client.StepExecutor;
import com.luoys.upgrade.toolservice.service.common.StringUtil;
import com.luoys.upgrade.toolservice.service.common.ThreadPoolUtil;
import com.luoys.upgrade.toolservice.dao.AutoCaseMapper;
import com.luoys.upgrade.toolservice.dao.CaseStepRelationMapper;
import com.luoys.upgrade.toolservice.dao.UserMapper;
import com.luoys.upgrade.toolservice.service.dto.CaseDTO;
import com.luoys.upgrade.toolservice.service.dto.DataSourceDTO;
import com.luoys.upgrade.toolservice.service.dto.StepDTO;
import com.luoys.upgrade.toolservice.service.enums.AutoCaseStatusEnum;
import com.luoys.upgrade.toolservice.service.enums.AutoCaseTypeEnum;
import com.luoys.upgrade.toolservice.service.enums.KeywordEnum;
import com.luoys.upgrade.toolservice.service.enums.RelatedStepTypeEnum;
import com.luoys.upgrade.toolservice.service.transform.TransformAutoStep;
import com.luoys.upgrade.toolservice.service.transform.TransformCaseStepRelation;
import com.luoys.upgrade.toolservice.service.transform.TransformConfig;
import com.luoys.upgrade.toolservice.web.vo.*;
import com.luoys.upgrade.toolservice.service.transform.TransformAutoCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

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
    private ConfigMapper configMapper;

    @Autowired
    private StepService stepService;

//    @Autowired
//    private UIClient uiClient;

    /**
     * 新增单个用例
     *
     * @param autoCaseVO 用例对象
     * @return 新增成功为true，失败为false
     */
    public Integer create(AutoCaseVO autoCaseVO) {
        if (autoCaseVO.getType() == null) {
            log.error("--->用例类型不能为空：{}", autoCaseVO);
            return null;
        }
        if (autoCaseVO.getStatus() == null) {
            log.error("--->用例状态不能为空：{}", autoCaseVO);
            return null;
        }
        if (StringUtil.isBlank(autoCaseVO.getName())) {
            log.error("--->用例名称不能为空：{}", autoCaseVO);
            return null;
        }
//        autoCaseVO.setCaseId(NumberSender.createCaseId());
        if (autoCaseVO.getOwnerId().equals(KeywordEnum.DEFAULT_USER.getCode().toString())) {
            autoCaseVO.setOwnerName(KeywordEnum.DEFAULT_USER.getValue());
        } else {
            String userName = userMapper.selectById(autoCaseVO.getOwnerId()).getUsername();
            autoCaseVO.setOwnerName(userName);
        }
        AutoCasePO autoCasePO = TransformAutoCase.transformVO2PO(autoCaseVO);
        autoCaseMapper.insert(autoCasePO);
        return autoCasePO.getId();
    }

    /**
     * 快速新增单个用例
     *
     * @param autoCaseVO 用例对象，需要有名称和类型
     * @return 新增成功为true，失败为false
     */
    public Integer quickCreate(AutoCaseVO autoCaseVO) {
        if (autoCaseVO.getType() == null) {
            log.error("--->用例类型不能为空：{}", autoCaseVO);
            return null;
        }
        if (StringUtil.isBlank(autoCaseVO.getName())) {
            log.error("--->用例名称不能为空：{}", autoCaseVO);
            return null;
        }
        autoCaseVO.setStatus(AutoCaseStatusEnum.PLANNING.getCode());
        AutoCasePO autoCasePO = TransformAutoCase.transformVO2PO(autoCaseVO);
        autoCaseMapper.insert(autoCasePO);
        return autoCasePO.getId();
    }

    /**
     * 快速新增单个配置
     *
     * @param configVO 配置对象，需要有名称和类型
     * @return 新增成功为1
     */
    public Integer quickCreate(ConfigVO configVO) {
        if (configVO.getType() == null) {
            log.error("--->用例类型不能为空：{}", configVO);
            return null;
        }
        ConfigPO configPO = TransformConfig.transformVO2PO(configVO);
        configMapper.insert(configPO);
        return configPO.getId();
    }

    /**
     * 新增用例中关联的步骤
     *
     * @param caseStepVO 步骤对象
     * @return 成功为true，失败为false
     */
    public Integer createRelatedStep(CaseStepVO caseStepVO) {
        // 如果关联步骤为空，则先快速创建一个步骤
        if (null == caseStepVO.getStepId() || caseStepVO.getStepId() <= 0) {
            caseStepVO.setStepId(stepService.quickCreate());
        }
        CaseStepRelationPO caseStepRelationPO = TransformCaseStepRelation.transformVO2PO(caseStepVO);
        caseStepRelationMapper.insert(caseStepRelationPO);
        return caseStepRelationPO.getId();
    }

//    /**
//     * 复制用例的基本信息和关联信息
//     * @param autoCaseVO 用例详情
//     * @return 执行无报错为true
//     */
//    public Boolean copyCase(AutoCaseVO autoCaseVO) {
//        // copy基本信息
//        autoCaseMapper.insert(TransformAutoCase.transformVO2PO(autoCaseVO));
//        List<CaseStepVO> caseStepList = new ArrayList<>();
//        caseStepList.addAll(autoCaseVO.getPreStepList());
//        caseStepList.addAll(autoCaseVO.getMainStepList());
//        caseStepList.addAll(autoCaseVO.getAfterStepList());
//        // 更新关联对象中的caseId
//        for (int i = 0; i < caseStepList.size(); i++) {
//            caseStepList.get(i).setCaseId(caseId);
//        }
//        caseStepRelationMapper.batchInsert(TransformCaseStepRelation.transformVO2PO(caseStepList));
//        return true;
//    }

    /**
     * 逻辑删除单个用例
     *
     * @param caseId 用例业务id
     * @return 删除成功为true，失败为false
     */
    public Boolean remove(Integer caseId) {
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
     * 删除用例中关联的配置
     *
     * @param configId 步骤对象
     * @return 成功为true，失败为false
     */
    public Integer removeConfig(Integer configId) {
        return configMapper.remove(configId);
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
    public Boolean updateRelatedStep(Integer caseId, Integer stepId, int sequence) {
        CaseStepVO caseStepVO = new CaseStepVO();
        caseStepVO.setCaseId(caseId);
        caseStepVO.setStepId(stepId);
        caseStepVO.setSequence(sequence);
        return updateRelatedStep(caseStepVO);
    }

    public Integer updateConfig(ConfigVO configVO) {
        ConfigPO configPO = TransformConfig.transformVO2PO(configVO);
        return configMapper.update(configPO);
    }

    /**
     * 查询用例列表
     *
     * @param queryVO 查询条件
     * @return 用例列表
     */
    public List<AutoCaseSimpleVO> query(QueryVO queryVO) {
//        Integer startIndex = queryVO.getPageIndex() == null ? null : (queryVO.getPageIndex() - 1) * KeywordEnum.DEFAULT_PAGE_SIZE.getCode();
        AutoCaseQueryPO autoCaseQueryPO = TransformAutoCase.transformVO2PO(queryVO);
        List<AutoCasePO> autoCasePOList =  autoCaseMapper.list(autoCaseQueryPO);
        return TransformAutoCase.transformPO2SimpleVO(autoCasePOList);
    }
    public Integer count(QueryVO queryVO) {
        AutoCaseQueryPO autoCaseQueryPO = TransformAutoCase.transformVO2PO(queryVO);
        return autoCaseMapper.count(autoCaseQueryPO);
    }

    /**
     * 查询用例详情
     *
     * @param caseId 用例业务id
     * @return 用例对象
     */
    public AutoCaseVO queryDetail(Integer caseId) {
        // 查基本信息
        AutoCaseVO autoCaseVO = TransformAutoCase.transformPO2VO(autoCaseMapper.selectById(caseId));
        // 如果是超类则查参数
        if (AutoCaseTypeEnum.SUPPER_CLASS.getCode().equals(autoCaseVO.getType())
                || autoCaseVO.getType().equals(AutoCaseTypeEnum.DATA_FACTORY.getCode())) {
            List<ConfigPO> configPOList = configMapper.list(autoCaseVO.getCaseId());
            autoCaseVO.setParameterList(TransformConfig.transformPO2VO(configPOList));
            // todo ui启动参数
            autoCaseVO.setArgumentList(null);
        }
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
     * 转换用例模式，将结构化数据转换成脚本
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
     * auto.sql.dbName("sql");
     * auto.assert.isContains("actual","expect");
     * auto.http.post("url", "body");
     * auto.rpc.invoke("url","paramClassName","paramValueForJson");
     * auto.po.login("username","password");
     * auto.util.sleep("param1");
     * String param = auto.methodType.method(param);
     *
     * @param autoCaseVO -
     */
    public AutoCaseVO change2UiMode(AutoCaseVO autoCaseVO) {

        // 通过正则解析脚本，把整段脚本解析成行('\w':任意字符，'.':0到无限次)
//        List<String> mainSteps = StringUtil.getMatch("auto\\.(ui|http|sql|rpc|util|po|assertion)\\.\\w+\\(.*\\);", autoCaseVO.getMainSteps());
        List<String> mainSteps = StringUtil.getMatch("\\(String[ ]{1,4}\\w{1,20}[ ]{1,4}=[ ]{1,4})?auto\\.(ui|http|sql|rpc|util|po|assertion)\\.\\w+\\(.*\\);[ ]{0,4}(\\n|\\r)\\i", autoCaseVO.getMainSteps());

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
     * 执行用例（异步），并更新用例的执行结果
     *
     * @param autoCaseVO 用例对象
     * @return 主要步骤全部执行结果都为true才返回true
     */
    public Boolean useAsync(AutoCaseVO autoCaseVO) throws RejectedExecutionException {
        ThreadPoolUtil.executeAuto(() -> {
            log.info("--->执行单个用例：caseId={}", autoCaseVO.getCaseId());
            CaseDTO caseDTO = TransformAutoCase.transformVO2DTO(autoCaseVO);
            StepExecutor executor = new StepExecutor();
            executor.execute(caseDTO);
            log.info("---->执行完毕，更新用例结果：caseId={}, result={}", autoCaseVO.getCaseId(), caseDTO.getStatus());
            autoCaseMapper.updateStatus(autoCaseVO.getCaseId(),
                    caseDTO.getStatus());
        });
        return true;
    }

    /**
     * 执行用例 (仅执行，不更新结果)
     *
     * @param autoCaseVO 用例对象
     * @return 主要步骤全部执行结果都为true才返回true
     */
    public Boolean use(AutoCaseVO autoCaseVO, StepExecutor executor) {
        CaseDTO caseDTO = TransformAutoCase.transformVO2DTO(autoCaseVO);
        executor.execute(caseDTO);
        log.info("--->执行用例完成：caseId={}, caseName={}, result={}", autoCaseVO.getCaseId(), autoCaseVO.getName(), caseDTO.getStatus());
        return true;
    }

//    private Boolean execute(AutoCaseVO autoCaseVO, StepExecutor executor) {
//        boolean result = true;
//        if (autoCaseVO.getMainStepList() == null || autoCaseVO.getMainStepList().size() == 0) {
//            log.error("--->用例没有主步骤：caseId={}", autoCaseVO.getCaseId());
//            return false;
//        }
//        // 执行前置步骤@BeforeTest
//        if (autoCaseVO.getPreStepList() != null && autoCaseVO.getPreStepList().size() != 0) {
//            for (CaseStepVO vo : autoCaseVO.getPreStepList()) {
//                // 前置步骤任意方法执行异常或失败，都直接跳过下面步骤
//                if (executor.execute(vo.getAutoStep()).equalsIgnoreCase("false")) {
//                    return false;
//                }
//            }
//        }
//        // 执行主要步骤@Test，只要有一个步骤为false，则整个case结果为false
//        if (autoCaseVO.getMainStepList() != null && autoCaseVO.getMainStepList().size() != 0) {
//            for (CaseStepVO vo : autoCaseVO.getMainStepList()) {
//                String var = executor.execute(vo.getAutoStep());
//                if (var == null || var.equalsIgnoreCase("false")) {
//                    result = false;
//                    break;
//                }
//            }
//        }
//        // 执行收尾步骤@AfterTest
//        if (result && autoCaseVO.getAfterStepList() != null && autoCaseVO.getAfterStepList().size() != 0) {
//            for (CaseStepVO vo : autoCaseVO.getAfterStepList()) {
//                executor.execute(vo.getAutoStep());
//            }
//        }
//        return result;
//    }

}
