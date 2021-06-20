package com.luoys.upgrade.toolservice.controller;

import com.luoys.upgrade.toolservice.controller.transform.TransformTool;
import com.luoys.upgrade.toolservice.controller.vo.PageInfo;
import com.luoys.upgrade.toolservice.controller.vo.ParamVO;
import com.luoys.upgrade.toolservice.controller.vo.ToolDetailVO;
import com.luoys.upgrade.toolservice.dao.ToolMapper;
import com.luoys.upgrade.toolservice.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/commonFactory")
public class CommonFactoryController {

    private static final Logger LOG = LoggerFactory.getLogger(CommonFactoryController.class);

    @Autowired
    ToolMapper toolMapper;

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public Result<Boolean> newCommonFactory(@RequestBody ToolDetailVO toolDetailVO) {
        LOG.info("---》开始新增通用工具：{}", toolDetailVO);
        if (toolDetailVO.getToolId() == null) {
            //todo
        }
        if (toolDetailVO.getTitle() == null) {
            LOG.error("---》标题不能为空");
            return Result.error("标题不能为空");
        }
        int result = toolMapper.insert(TransformTool.transformVO2PO(toolDetailVO));
        return result == 1 ? Result.success(true) : Result.error("新增失败");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Result<Boolean> deleteCommonFactory(@RequestParam("toolId") Integer toolId) {
        return null;
    }

    @RequestMapping(value = "/modify", method = RequestMethod.PUT)
    public Result<Boolean> modifyCommonFactory(@RequestBody ToolDetailVO toolDetailVO) {
        return null;
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Result<PageInfo<ToolDetailVO>> queryCommonFactory(
            @RequestParam(value = "type", required = false) Integer type, @RequestParam(value = "title", required = false) String title) {
        return Result.error("---");
    }

    @RequestMapping(value = "/use", method = RequestMethod.POST)
    public Result<ParamVO> userCommonFactory(@RequestBody ToolDetailVO toolDetailVO) {
        return null;
    }
}
