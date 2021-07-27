package com.luoys.upgrade.toolservice.service;

import com.luoys.upgrade.toolservice.common.HttpUtil;
import com.luoys.upgrade.toolservice.common.JdbcUtil;
import com.luoys.upgrade.toolservice.common.NumberSender;
import com.luoys.upgrade.toolservice.common.RpcUtil;
import com.luoys.upgrade.toolservice.service.enums.KeywordEnum;
import com.luoys.upgrade.toolservice.service.enums.ToolTypeEnum;
import com.luoys.upgrade.toolservice.service.transform.TransformTool;
import com.luoys.upgrade.toolservice.web.vo.ToolSimpleVO;
import com.luoys.upgrade.toolservice.web.vo.ToolVO;
import com.luoys.upgrade.toolservice.dao.ToolMapper;
import com.luoys.upgrade.uc.share.service.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FactoryService {

    @Autowired
    private ToolMapper toolMapper;

//    @DubboReference
//    private UserService userService;

    /**
     * 创建单个工具
     * @param toolVO 工具对象
     * @return 成功为true，失败为false
     */
    public Boolean create(ToolVO toolVO) {
        toolVO.setToolId(NumberSender.createToolId());
        if (toolVO.getOwnerId().equals(KeywordEnum.DEFAULT_USER.getCode())) {
            toolVO.setOwnerName(KeywordEnum.DEFAULT_USER.getDescription());
        } else {
//            toolVO.setOwnerName(userService.queryByUserId(toolVO.getOwnerId()).getData().getUserName());
        }
        int result = toolMapper.insert(TransformTool.transformVO2PO(toolVO));
        return result == 1;
    }

    /**
     * 逻辑删除单个工具
     * @param toolId 工具uuid
     * @return 成功为true，失败为false
     */
    public Boolean remove(String toolId) {
        int result = toolMapper.deleteByUID(toolId);
        return result == 1;
    }

    /**
     * 更新单个工具
     * @param toolVO 工具对象
     * @return 成功为true，失败为false
     */
    public Boolean update(ToolVO toolVO) {
        int result = toolMapper.updateByUID(TransformTool.transformVO2PO(toolVO));
        return result == 1;
    }

    /**
     * 查询工具列表
     * @param userId 用户id
     * @param isOnlyOwner 是否只查自己
     * @param type 类型
     * @param name 名字
     * @param isTestStep 是否只查测试步骤
     * @param pageIndex 页码
     * @return 工具列表
     */
    public List<ToolSimpleVO> query(String userId,
                                    Boolean isOnlyOwner,
                                    Integer type,
                                    String name,
                                    Boolean isTestStep,
                                    Integer pageIndex) {
        if (isOnlyOwner != null && isOnlyOwner) {
            userId = null;
        }
        //数据库startIndex从0开始
        return TransformTool.transformPO2VO(toolMapper.list(type, name, isTestStep, userId, pageIndex-1));
    }

    /**
     * 查询工具详情
     * @param toolId 工具uuid
     * @return 工具对象
     */
    public ToolVO queryDetail(String toolId) {
        return TransformTool.transformPO2VO(toolMapper.selectByUID(toolId));
    }

    /**
     * 使用单个工具
     * @param toolVO 工具对象
     * @return 使用结果，sql为查出的数据，http为调用后的response
     */
    public String use(ToolVO toolVO) {
        switch (ToolTypeEnum.fromCode(toolVO.getType())) {
            case SQL:
                //先将参数与sql模板合并
                TransformTool.mergeSql(toolVO);
                return JdbcUtil.execute(toolVO.getJdbc()) ? "执行成功" : "执行失败";
            case HTTP:
                TransformTool.mergeHttp(toolVO);
                return HttpUtil.execute(toolVO.getHttpRequest());
            case RPC:
                TransformTool.mergeRpc(toolVO);
                return RpcUtil.execute(toolVO.getRpc());
            default:
                break;
        }
        return null;
    }

}
