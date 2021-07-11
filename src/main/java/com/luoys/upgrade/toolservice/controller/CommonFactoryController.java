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
    public Result<String> create(@RequestBody ToolVO toolVO) {
        log.info("---》开始新增通用工具：{}", toolVO);
        toolVO.setToolId(NumberSender.createToolId());
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
                                                      @RequestParam(value = "title", required = false) String title,
                                                      @RequestParam("owner") String owner,
                                                      @RequestParam("startIndex") Integer startIndex) {
        log.info("---》开始查询通用工具列表：");
        PageInfo<ToolSimpleVO> pageInfo = new PageInfo<>();
        pageInfo.setData(TransformTool.transformPO2VO(toolMapper.list(type, title, owner, startIndex)));
        pageInfo.setTotal(pageInfo.getData().size());
        return Result.success(pageInfo);
    }

    @RequestMapping(value = "/use", method = RequestMethod.POST)
    public Result<String> use(@RequestBody ToolVO toolVO) {
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
