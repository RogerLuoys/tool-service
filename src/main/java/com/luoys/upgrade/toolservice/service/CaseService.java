package com.luoys.upgrade.toolservice.service;

import com.luoys.upgrade.toolservice.common.NumberSender;
import com.luoys.upgrade.toolservice.common.ThreadPoolUtil;
import com.luoys.upgrade.toolservice.dao.AutoCaseMapper;
import com.luoys.upgrade.toolservice.service.client.UIClient;
import com.luoys.upgrade.toolservice.service.dto.StepDTO;
import com.luoys.upgrade.toolservice.service.enums.AutoCaseTypeEnum;
import com.luoys.upgrade.toolservice.service.enums.KeywordEnum;
import com.luoys.upgrade.toolservice.web.vo.AutoCaseSimpleVO;
import com.luoys.upgrade.toolservice.web.vo.AutoCaseVO;
import com.luoys.upgrade.toolservice.service.transform.TransformAutoCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CaseService {

    @Autowired
    private AutoCaseMapper autoCaseMapper;

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
     * 逻辑删除单个用例
     * @param caseId 用例业务id
     * @return 删除成功为true，失败为false
     */
    public Boolean remove(String caseId) {
        int result = autoCaseMapper.remove(caseId);
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
        return TransformAutoCase.transformPO2VO(autoCaseMapper.selectByUUID(caseId));
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
        // 如果是UI用例，则执行前也退出
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
            for (StepDTO stepDTO: autoCaseVO.getPreStepList()) {
                stepService.use(stepService.queryDetail(stepDTO.getStepId()));
            }
        }
        // 执行主要步骤，只要有一个步骤为false，则整个case结果为false
        if (autoCaseVO.getMainStepList() != null && autoCaseVO.getMainStepList().size() != 0) {
            for (StepDTO stepDTO: autoCaseVO.getMainStepList()) {
                if (!stepService.use(stepService.queryDetail(stepDTO.getStepId()))) {
                    result = false;
                }
            }
        } else {
            log.error("--->用例没有主步骤：caseId={}", autoCaseVO.getCaseId());
            return false;
        }
        // 执行收尾步骤，如果执行结果为false，则不执行该步骤
        if (result && autoCaseVO.getAfterStepList() != null && autoCaseVO.getAfterStepList().size() != 0) {
            for (StepDTO stepDTO: autoCaseVO.getAfterStepList()) {
                stepService.use(stepService.queryDetail(stepDTO.getStepId()));
            }
        }
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
