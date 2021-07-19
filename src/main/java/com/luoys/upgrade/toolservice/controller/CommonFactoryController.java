package com.luoys.upgrade.toolservice.controller;

import com.luoys.upgrade.toolservice.common.HttpUtil;
import com.luoys.upgrade.toolservice.common.JdbcUtil;
import com.luoys.upgrade.toolservice.common.NumberSender;
import com.luoys.upgrade.toolservice.controller.enums.ToolTypeEnum;
import com.luoys.upgrade.toolservice.controller.transform.TransformTool;
import com.luoys.upgrade.toolservice.controller.vo.PageInfo;
import com.luoys.upgrade.toolservice.controller.vo.ToolSimpleVO;
import com.luoys.upgrade.toolservice.controller.vo.ToolVO;
import com.luoys.upgrade.toolservice.dao.ToolMapper;
import com.luoys.upgrade.toolservice.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/commonFactory")
public class CommonFactoryController {

    @Autowired
    private ToolMapper toolMapper;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Result<String> create(@RequestHeader("userId") String userId, @RequestHeader("userName") String userName, @RequestBody ToolVO toolVO) {
        toolVO.setToolId(NumberSender.createToolId());
        toolVO.setOwnerId(userId);
        toolVO.setOwnerName(userName);
        log.info("---》开始新增通用工具：{}", toolVO);
        if (toolVO.getTitle() == null) {
            return Result.error("标题不能为空");
        }
        int result = toolMapper.insert(TransformTool.transformVO2PO(toolVO));
        return result == 1 ? Result.success("新增成功") : Result.error("新增失败");
    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public Result<String> remove(@RequestParam("toolId") String toolId) {
        log.info("---》开始删除通用工具：{}", toolId);
        int result = toolMapper.deleteByUID(toolId);
        return result == 1 ? Result.success("删除成功") : Result.error("删除失败");
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<Boolean> update(@RequestBody ToolVO toolVO) {
        log.info("---》开始更新通用工具：{}", toolVO);
        int result = toolMapper.updateByUID(TransformTool.transformVO2PO(toolVO));
        return result == 1 ? Result.success("成功") : Result.error("更新失败");
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Result<PageInfo<ToolSimpleVO>> query(@RequestParam(value = "type", required = false) Integer type,
                                                @RequestParam(value = "name", required = false) String name,
                                                @RequestHeader("userId") String userId,
                                                @RequestParam("pageIndex") Integer pageIndex) {
        log.info("---》开始查询通用工具列表：type={}, title={}, userId={}, startIndex={}", type, name, userId, pageIndex);
        PageInfo<ToolSimpleVO> pageInfo = new PageInfo<>();
        //数据库startIndex从0开始
        pageInfo.setList(TransformTool.transformPO2VO(toolMapper.list(type, name, userId, pageIndex-1)));
        pageInfo.setTotal(pageInfo.getList().size());
        return Result.success(pageInfo);
    }

    @RequestMapping(value = "/queryDetail", method = RequestMethod.GET)
    public Result<ToolVO> queryDetail(@RequestParam("toolId") String toolId) {
        log.info("---》开始查询通用工具详情：toolId={}", toolId);
        return Result.success(TransformTool.transformPO2VO(toolMapper.selectByUID(toolId)));
    }

    @RequestMapping(value = "/use", method = RequestMethod.POST)
    public Result<String> use(@RequestBody ToolVO toolVO) {
        log.info("---》开始使用通用工具：{}", toolVO);
        switch (ToolTypeEnum.fromCode(toolVO.getType())) {
            case SQL:
                //先将参数与sql模板合并
                TransformTool.mergeSql(toolVO);
                return Result.success(JdbcUtil.execute(toolVO.getJdbc()));
            case HTTP:
                TransformTool.mergeHttp(toolVO);
                return Result.success(HttpUtil.execute(toolVO.getHttpRequest()));
            case RPC:
                //todo rpc
                break;
            default:
                break;
        }
        return null;
    }
}
