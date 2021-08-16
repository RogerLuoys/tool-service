package com.luoys.upgrade.toolservice.service;

import com.luoys.upgrade.toolservice.common.NumberSender;
import com.luoys.upgrade.toolservice.common.StringUtil;
import com.luoys.upgrade.toolservice.common.ThreadPoolUtil;
import com.luoys.upgrade.toolservice.dao.AutoCaseMapper;
import com.luoys.upgrade.toolservice.dao.CaseStepRelationMapper;
import com.luoys.upgrade.toolservice.service.client.UIClient;
import com.luoys.upgrade.toolservice.service.enums.AutoCaseStatusEnum;
import com.luoys.upgrade.toolservice.service.enums.AutoCaseTypeEnum;
import com.luoys.upgrade.toolservice.service.enums.KeywordEnum;
import com.luoys.upgrade.toolservice.service.enums.RelatedStepTypeEnum;
import com.luoys.upgrade.toolservice.service.transform.TransformCaseStepRelation;
import com.luoys.upgrade.toolservice.web.vo.AutoCaseSimpleVO;
import com.luoys.upgrade.toolservice.web.vo.AutoCaseVO;
import com.luoys.upgrade.toolservice.service.transform.TransformAutoCase;
import com.luoys.upgrade.toolservice.web.vo.CaseStepVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CaseService {

    @Autowired
    private AutoCaseMapper autoCaseMapper;

    @Autowired
    private CaseStepRelationMapper caseStepRelationMapper;

    @Autowired
    private StepService stepService;

    @Autowired
    private UIClient uiClient;

    /**
     * 新增单个用例
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
        if (autoCaseVO.getOwnerId().equals(KeywordEnum.DEFAULT_USER.getCode())) {
            autoCaseVO.setOwnerName(KeywordEnum.DEFAULT_USER.getDescription());
        } else {
            //todo 查用户名
        }
        int result = autoCaseMapper.insert(TransformAutoCase.transformVO2PO(autoCaseVO));
        return result == 1;
    }

    /**
     * 快速新增单个用例
     * @param autoCaseVO 用例对象，需要有名称和类型
     * @return 新增成功为true，失败为false
     */
    public Boolean quickCreate(AutoCaseVO autoCaseVO) {
        if (autoCaseVO.getType() == null) {
            log.error("--->用例类型不能为空：{}", autoCaseVO);
            return false;
        }
        if (StringUtil.isBlank(autoCaseVO.getName())) {
            log.error("--->用例名称不能为空：{}", autoCaseVO);
            return false;
        }
        autoCaseVO.setStatus(AutoCaseStatusEnum.PLANNING.getCode());
        autoCaseVO.setCaseId(NumberSender.createCaseId());
        int result = autoCaseMapper.insert(TransformAutoCase.transformVO2PO(autoCaseVO));
        return result == 1;
    }

    /**
     * 新增用例中关联的步骤
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
     * 逻辑删除单个用例
     * @param caseId 用例业务id
     * @return 删除成功为true，失败为false
     */
    public Boolean remove(String caseId) {
        caseStepRelationMapper.removeByCaseId(caseId);
        int result = autoCaseMapper.remove(caseId);
        return result == 1;
    }

    /**
     * 删除测试集关联的用例
     * @param caseStepVO 步骤对象
     * @return 成功为true，失败为false
     */
    public Boolean removeRelatedStep(CaseStepVO caseStepVO) {
        int result = caseStepRelationMapper.remove(TransformCaseStepRelation.transformVO2PO(caseStepVO));
        return result == 1;
    }

    /**
     * 更新单个用例
     * @param autoCaseVO 用例对象
     * @return 更新成功为true，失败为false
     */
    public Boolean update(AutoCaseVO autoCaseVO) {
        int result = autoCaseMapper.update(TransformAutoCase.transformVO2PO(autoCaseVO));
        return result == 1;
    }

    /**
     * 更新用例关联的步骤
     * @param caseStepVO 步骤对象
     * @return 成功为true，失败为false
     */
    public Boolean updateRelatedStep(CaseStepVO caseStepVO) {
        int result = caseStepRelationMapper.update(TransformCaseStepRelation.transformVO2PO(caseStepVO));
        return result == 1;
    }

    /**
     * 查询用例列表
     * @param userId 用户id
     * @param isOnlyOwner 是否只查自己
     * @param status 用例状态
     * @param name 用例名称
     * @param pageIndex 页码
     * @return 用例列表
     */
    public List<AutoCaseSimpleVO> query(String userId, Boolean isOnlyOwner, Integer status, String name, Integer pageIndex) {
        if (!isOnlyOwner) {
            userId = null;
        }
        return TransformAutoCase.transformPO2SimpleVO(autoCaseMapper.list(status, name, userId, pageIndex-1));
    }

    /**
     * 查询用例详情
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
     * 使用用例（异步）
     * @param autoCaseVO 用例对象
     * @return 主要步骤全部执行结果都为true才返回true
     */
    public Boolean useAsync(AutoCaseVO autoCaseVO) {
        try {
            // UI用例和API用例使用不同线程池，UI自动化只能单个子线程
            if (autoCaseVO.getType().equals(AutoCaseTypeEnum.UI_CASE.getCode())) {
                ThreadPoolUtil.executeUI(new Runnable() {
                    @Override
                    public void run() {
                        useUI(autoCaseVO);
                    }
                });
            } else {
                ThreadPoolUtil.execute(new Runnable() {
                    @Override
                    public void run() {
                        useAPI(autoCaseVO);
                    }
                });
            }
        } catch (Exception e) {
            log.error("--->执行用例异常", e);
            return false;
        }
        return true;
    }

    /**
     * 使用用例
     * @param autoCaseVO 用例对象
     * @return 主要步骤全部执行结果都为true才返回true
     */
    public Boolean use(AutoCaseVO autoCaseVO) {
        boolean result;
        // UI和接口用例分开执行
        if (autoCaseVO.getType().equals(AutoCaseTypeEnum.UI_CASE.getCode())) {
            result = useUI(autoCaseVO);
        } else {
            result = useAPI(autoCaseVO);
        }
        return result;
    }

    /**
     * 执行接口自动化用例
     * @param autoCaseVO 用例对象
     * @return 主要步骤全部执行结果都为true才返回true
     */
    public Boolean useAPI(AutoCaseVO autoCaseVO) {
        // 执行用例
        return execute(autoCaseVO);
    }

    /**
     * 执行UI自动化用例
     * @param autoCaseVO 用例对象
     * @return 主要步骤全部执行结果都为true才返回true
     */
    public Boolean useUI(AutoCaseVO autoCaseVO) {
        boolean result;
        // 确保webDrive退出
        uiClient.quit();
        // 执行用例
        result = execute(autoCaseVO);
        // 确保webDrive退出
        uiClient.quit();
        return result;
    }

    private Boolean execute(AutoCaseVO autoCaseVO) {
        boolean result = true;
        // 执行前置步骤
        if (autoCaseVO.getPreStepList() != null && autoCaseVO.getAfterStepList().size() != 0) {
            for (CaseStepVO vo: autoCaseVO.getPreStepList()) {
                stepService.use(vo.getAutoStep());
            }
        }
        // 执行主要步骤，只要有一个步骤为false，则整个case结果为false
        if (autoCaseVO.getMainStepList() != null && autoCaseVO.getMainStepList().size() != 0) {
            for (CaseStepVO vo: autoCaseVO.getMainStepList()) {
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
            for (CaseStepVO vo: autoCaseVO.getAfterStepList()) {
                stepService.use(vo.getAutoStep());
            }
        }
        // 更新用例执行状态
        autoCaseMapper.updateStatus(autoCaseVO.getCaseId(), result ? AutoCaseStatusEnum.SUCCESS.getCode() : AutoCaseStatusEnum.FAIL.getCode());
        return result;
    }

//    private Boolean execute(List<StepDTO> stepList) {
//        boolean result = true;
//        for (StepDTO stepDTO: stepList) {
//            if (!stepService.use(stepService.queryDetail(stepDTO.getStepId()))) {
//                result = false;
//            }
//        }
//        return result;
//    }

}
