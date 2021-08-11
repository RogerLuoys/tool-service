package com.luoys.upgrade.toolservice.service;

import com.luoys.upgrade.toolservice.common.NumberSender;
import com.luoys.upgrade.toolservice.dao.ToolMapper;
import com.luoys.upgrade.toolservice.service.client.DBClient;
import com.luoys.upgrade.toolservice.service.client.HTTPClient;
import com.luoys.upgrade.toolservice.service.client.RPCClient;
import com.luoys.upgrade.toolservice.service.enums.KeywordEnum;
import com.luoys.upgrade.toolservice.service.enums.ToolTypeEnum;
import com.luoys.upgrade.toolservice.service.transform.TransformTool;
import com.luoys.upgrade.toolservice.web.vo.ToolSimpleVO;
import com.luoys.upgrade.toolservice.web.vo.ToolVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FactoryService {

    private final static Integer MULTIPLE_LIMIT = 10;

    @Autowired
    private ToolMapper toolMapper;

    @Autowired
    private HTTPClient httpClient;

    @Autowired
    private RPCClient rpcClient;

    @Autowired
    private DBClient dbClient;

//    @DubboReference
//    private UserService userService;

    /**
     * 创建单个工具
     *
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
     *
     * @param toolId 工具uuid
     * @return 成功为true，失败为false
     */
    public Boolean remove(String toolId) {
        int result = toolMapper.remove(toolId);
        return result == 1;
    }

    /**
     * 更新单个工具
     *
     * @param toolVO 工具对象
     * @return 成功为true，失败为false
     */
    public Boolean update(ToolVO toolVO) {
        int result = toolMapper.update(TransformTool.transformVO2PO(toolVO));
        return result == 1;
    }

    /**
     * 查询工具列表
     *
     * @param userId      用户id
     * @param isOnlyOwner 是否只查自己
     * @param type        类型
     * @param name        名字
     * @param pageIndex   页码
     * @return 工具列表
     */
    public List<ToolSimpleVO> query(String userId,
                                    Boolean isOnlyOwner,
                                    Integer type,
                                    String name,
                                    Integer pageIndex) {
        if (isOnlyOwner != null && isOnlyOwner) {
            userId = null;
        }
        //数据库startIndex从0开始
        return TransformTool.transformPO2VO(toolMapper.list(type, name, userId, pageIndex - 1));
    }

    /**
     * 查询工具详情
     *
     * @param toolId 工具uuid
     * @return 工具对象
     */
    public ToolVO queryDetail(String toolId) {
        return TransformTool.transformPO2VO(toolMapper.selectByUUID(toolId));
    }

    /**
     * 使用工具
     *
     * @param toolVO 工具对象
     * @return 使用结果，sql为查出的数据，http为调用后的response
     */
    public String use(ToolVO toolVO) {
        List<ToolVO> toolList = new ArrayList<>();
        StringBuilder result = new StringBuilder();
        // 如果是聚合工具类型，则把子工具全取出
        if (toolVO.getType() == ToolTypeEnum.MULTIPLE.getCode()) {
            if (toolVO.getToolList().size() > MULTIPLE_LIMIT) {
                log.warn("--->聚合工具个数超过上限");
                return null;
            }
            for (int i = 0; i < toolVO.getToolList().size(); i++) {
                toolList.add(queryDetail(toolVO.getToolList().get(i)));
            }
        } else {
            toolList.add(toolVO);
        }
        // 执行工具
        try {
            for (ToolVO targetVO: toolList) {
                if (targetVO.getType().equals(ToolTypeEnum.MULTIPLE.getCode())) {
                    log.error("---->不支持套娃：toolId={}", targetVO.getToolId());
                    return null;
                }
                result.append(execute(targetVO)).append("\n");
            }
        } catch (Exception e) {
            log.error("---->工具执行异常：toolId={}", toolVO.getToolId(), e);
            return null;
        }
        result.delete(result.length() - 2, result.length());
        return result.toString();
    }

    private String execute(ToolVO toolVO) {
        switch (ToolTypeEnum.fromCode(toolVO.getType())) {
            case MULTIPLE:
                log.warn("--->不执行聚合工具类型");
                return null;
            case SQL:
                TransformTool.mergeSql(toolVO);
                //通过JdbcTemplate实现
                return dbClient.execute(toolVO.getJdbc());
            case HTTP:
                TransformTool.mergeHttp(toolVO);
                //通过restTemplate实现
                return httpClient.execute(toolVO.getHttpRequest());
            case RPC:
                TransformTool.mergeRpc(toolVO);
                //通过json格式的泛化调用实现
                return rpcClient.execute(toolVO.getRpc());
        }
        return null;
    }

}
