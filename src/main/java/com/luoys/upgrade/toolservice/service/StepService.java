package com.luoys.upgrade.toolservice.service;

import com.luoys.upgrade.toolservice.common.HttpUtil;
import com.luoys.upgrade.toolservice.common.JdbcUtil;
import com.luoys.upgrade.toolservice.common.RpcUtil;
import com.luoys.upgrade.toolservice.dao.AutoStepMapper;
import com.luoys.upgrade.toolservice.service.client.HTTPClient;
import com.luoys.upgrade.toolservice.service.enums.AssertTypeEnum;
import com.luoys.upgrade.toolservice.service.enums.AutoStepTypeEnum;
import com.luoys.upgrade.toolservice.service.enums.KeywordEnum;
import com.luoys.upgrade.toolservice.service.enums.ToolTypeEnum;
import com.luoys.upgrade.toolservice.service.transform.TransformAutoStep;
import com.luoys.upgrade.toolservice.web.vo.AutoStepSimpleVO;
import com.luoys.upgrade.toolservice.web.vo.AutoStepVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class StepService {

    @Autowired
    private AutoStepMapper autoStepMapper;

//    @DubboReference
//    private UserService userService;

    /**
     * 创建单个步骤
     * @param autoStepVO 步骤对象
     * @return 成功为true，失败为false
     */
    public Boolean create(AutoStepVO autoStepVO) {
        if (autoStepVO.getOwnerId().equals(KeywordEnum.DEFAULT_USER.getCode())) {
            autoStepVO.setOwnerName(KeywordEnum.DEFAULT_USER.getDescription());
        } else {
//            autoStepVO.setOwnerName(userService.queryByUserId(autoStepVO.getOwnerId()).getData().getUserName());
        }
        int result = autoStepMapper.insert(TransformAutoStep.transformVO2PO(autoStepVO));
        return result == 1;
    }

    /**
     * 逻辑删除单个步骤
     * @param stepId 步骤uuid
     * @return 成功为true，失败为false
     */
    public Boolean remove(Integer stepId) {
        int result = autoStepMapper.remove(stepId);
        return result == 1;
    }

    /**
     * 更新单个步骤
     * @param autoStepVO 步骤对象
     * @return 成功为true，失败为false
     */
    public Boolean update(AutoStepVO autoStepVO) {
        int result = autoStepMapper.update(TransformAutoStep.transformVO2PO(autoStepVO));
        return result == 1;
    }

    /**
     * 查询步骤列表
     * @param userId 用户id
     * @param isOnlyOwner 是否只查自己
     * @param type 类型
     * @param name 名字
     * @param pageIndex 页码
     * @return 步骤列表
     */
    public List<AutoStepSimpleVO> query(String userId,
                                    Boolean isOnlyOwner,
                                    Integer type,
                                    String name,
                                    Boolean isPublic,
                                    Integer pageIndex) {
        if (isOnlyOwner != null && isOnlyOwner) {
            userId = null;
        }
        //数据库startIndex从0开始
        return TransformAutoStep.transformPO2VO(autoStepMapper.list(type, name, userId, isPublic, pageIndex-1));
    }

    /**
     * 查询步骤详情
     * @param stepId 步骤uuid
     * @return 步骤对象
     */
    public AutoStepVO queryDetail(Integer stepId) {
        return TransformAutoStep.transformPO2VO(autoStepMapper.selectById(stepId));
    }

    /**
     * 使用单个步骤
     * @param autoStepVO 步骤对象
     * @return 使用结果，sql为查出的数据，http为调用后的response
     */
    public Boolean use(AutoStepVO autoStepVO) {
        String actualResult = execute(autoStepVO);
        // 步骤无需校验，执行完直接返回true
        if (autoStepVO.getAssertType().equals(AssertTypeEnum.NO_ASSERT.getCode())) {
            return true;
        }
        updateActualResult(actualResult, autoStepVO.getStepId());
        return verify(autoStepVO, actualResult);
    }

    public String execute(AutoStepVO autoStepVO) {
        switch (AutoStepTypeEnum.fromCode(autoStepVO.getType())) {
            case STEP_SQL:
                //通过JdbcTemplate实现
                return JdbcUtil.execute(autoStepVO.getJdbc()) ? "执行成功" : "执行失败";
            case STEP_HTTP:
                //通过restTemplate实现
                return  HttpUtil.execute(autoStepVO.getHttpRequest());
            case STEP_RPC:
                //通过json格式的泛化调用实现
                return  RpcUtil.execute(autoStepVO.getRpc());
            case STEP_UI:
                //todo
                return null;
            default:
                return null;
        }
    }

    public Boolean verify(AutoStepVO autoStepVO, String actualResult) {
        switch (AssertTypeEnum.fromCode(autoStepVO.getAssertType())) {
            case EQUALS:
                return autoStepVO.getExpectResult().equals(actualResult);
            case CONTAINS:
                return autoStepVO.getExpectResult().contains(actualResult);
            case NO_ASSERT:
                return true;
            default:
                return false;
        }
    }

    public Boolean updateActualResult(String actualResult, Integer stepId) {
        //todo
        return false;
    }

}
