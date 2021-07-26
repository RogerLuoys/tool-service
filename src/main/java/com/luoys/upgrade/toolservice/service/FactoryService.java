package com.luoys.upgrade.toolservice.service;

import com.luoys.upgrade.toolservice.common.HttpUtil;
import com.luoys.upgrade.toolservice.common.JdbcUtil;
import com.luoys.upgrade.toolservice.common.NumberSender;
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

    public Boolean remove(String toolId) {
        int result = toolMapper.deleteByUID(toolId);
        return result == 1;
    }

    public Boolean update(ToolVO toolVO) {
        int result = toolMapper.updateByUID(TransformTool.transformVO2PO(toolVO));
        return result == 1;
    }

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

    public ToolVO queryDetail(String toolId) {
        return TransformTool.transformPO2VO(toolMapper.selectByUID(toolId));
    }

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
                //todo rpc
                break;
            default:
                break;
        }
        return null;
    }
}
