package com.luoys.upgrade.toolservice.service;

import com.luoys.upgrade.toolservice.dao.AutoStepMapper;
import com.luoys.upgrade.toolservice.service.client.DBClient;
import com.luoys.upgrade.toolservice.service.client.HTTPClient;
import com.luoys.upgrade.toolservice.service.client.RPCClient;
import com.luoys.upgrade.toolservice.service.client.UIClient;
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

    @Autowired
    private HTTPClient httpClient;

    @Autowired
    private RPCClient rpcClient;

    @Autowired
    private DBClient dbClient;

    @Autowired
    private UIClient uiClient;

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
     * @return 使用结果，执行成功且验证通过为true，失败或异常为false
     */
    public Boolean use(AutoStepVO autoStepVO) {
        String actualResult;
        try {
            actualResult = execute(autoStepVO);
        } catch (Exception e) {
            log.error("--->步骤执行异常：stepId={}", autoStepVO.getStepId(), e);
            return false;
        }
        return verify(autoStepVO, actualResult);
    }

    private String execute(AutoStepVO autoStepVO) {
        switch (AutoStepTypeEnum.fromCode(autoStepVO.getType())) {
            case STEP_SQL:
                //通过JdbcTemplate实现
                return dbClient.execute(autoStepVO.getJdbc());
            case STEP_HTTP:
                //通过restTemplate实现
                return  httpClient.execute(autoStepVO.getHttpRequest());
            case STEP_RPC:
                //通过json格式的泛化调用实现
                return  rpcClient.execute(autoStepVO.getRpc());
            case STEP_UI:
                //todo
                return uiClient.execute(autoStepVO.getUi());
            default:
                return null;
        }
    }

    /**
     * 校验步骤执行结果
     * 如果校验结果为false，会将实际值写入
     * @param autoStepVO 步骤对象
     * @param actualResult 实际结果
     * @return 如果无需校验或校验通过，则返回true；否则返回false
     */
    public Boolean verify(AutoStepVO autoStepVO, String actualResult) {
        boolean result;
        switch (AssertTypeEnum.fromCode(autoStepVO.getAssertType())) {
            case NO_ASSERT:
                result = true;
                break;
            case EQUALS:
                result = autoStepVO.getExpectResult().equals(actualResult);
                break;
            case CONTAINS:
                result = autoStepVO.getExpectResult().contains(actualResult);
                break;
            default:
                log.error("--->未知步骤校验类型");
                return false;
        }
        // 如果校验失败，才将实际结果写回
        if (!result) {
            updateActualResult(autoStepVO.getStepId(), actualResult);
        }
        return result;
    }

    public Boolean updateActualResult(Integer stepId, String actualResult) {
        int result = autoStepMapper.updateResult(stepId, actualResult);
        return result == 1;
    }

}
