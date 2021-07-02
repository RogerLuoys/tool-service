package com.luoys.upgrade.toolservice.controller;

import com.luoys.upgrade.toolservice.common.NumberSender;
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

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Result<String> create(@RequestBody ToolDetailVO toolDetailVO) {
        LOG.info("---》开始新增通用工具：{}", toolDetailVO);
        toolDetailVO.setToolId(NumberSender.createToolId());
        if (toolDetailVO.getTitle() == null) {
            LOG.error("---》标题不能为空");
            return Result.error("标题不能为空");
        }
        int result = toolMapper.insert(TransformTool.transformVO2PO(toolDetailVO));
        return result == 1 ? Result.success("新增成功") : Result.error("新增失败");
    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public Result<Boolean> remove(@RequestParam("toolId") Integer toolId) {
        return null;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<Boolean> update(@RequestBody ToolDetailVO toolDetailVO) {
        return null;
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Result<PageInfo<ToolDetailVO>> query(
            @RequestParam(value = "type", required = false) Integer type, @RequestParam(value = "title", required = false) String title) {
        return Result.error("---");
    }

    @RequestMapping(value = "/use", method = RequestMethod.POST)
    public Result<ParamVO> use(@RequestBody ToolDetailVO toolDetailVO) {
        switch (toolDetailVO.getType()) {
            case 1:
                //todo sql
                break;
            case 2:
                //todo http
                break;
            case 3:
                //todo rpc
                break;
            default:
                break;
        }
        return null;
    }
}
