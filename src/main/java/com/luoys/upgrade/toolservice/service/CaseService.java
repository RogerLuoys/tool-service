package com.luoys.upgrade.toolservice.service;

import com.luoys.upgrade.toolservice.dao.AutoCaseMapper;
import com.luoys.upgrade.toolservice.service.dto.StepDTO;
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

    /**
     * 新增单个用例
     * @param autoCaseVO 用例对象
     * @return 新增成功为true，失败为false
     */
    public Boolean create(AutoCaseVO autoCaseVO) {
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
     * @param caseId 用例主键id
     * @return 删除成功为true，失败为false
     */
    public Boolean remove(Integer caseId) {
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
     * @param caseId 用例主键id
     * @return 用例对象
     */
    public AutoCaseVO queryDetail(Integer caseId) {
        return TransformAutoCase.transformPO2VO(autoCaseMapper.selectById(caseId));
    }

    /**
     * 使用用例
     * @param autoCaseVO 用例对象
     * @return 主要步骤全部执行结果都为true才返回true
     */
    public Boolean use(AutoCaseVO autoCaseVO) {
        boolean result;
        // 执行前置步骤
        if (autoCaseVO.getPreStepList() != null && autoCaseVO.getAfterStepList().size() != 0) {
            execute(autoCaseVO.getPreStepList());
        }
        // 执行主要步骤
        if (autoCaseVO.getMainStepList() != null && autoCaseVO.getMainStepList().size() != 0) {
            result = execute(autoCaseVO.getMainStepList());
        } else {
            log.error("--->用例没有主步骤：caseId={}", autoCaseVO.getCaseId());
            return false;
        }
        // 执行收尾步骤
        if (autoCaseVO.getAfterStepList() != null && autoCaseVO.getAfterStepList().size() != 0) {
            execute(autoCaseVO.getAfterStepList());
        }
        return result;
    }

    private Boolean execute(List<StepDTO> stepList) {
        boolean result = true;
        for (StepDTO stepDTO: stepList) {
            if (!stepService.use(stepService.queryDetail(stepDTO.getStepId()))) {
                result = false;
            }
        }
        return result;
    }

}
