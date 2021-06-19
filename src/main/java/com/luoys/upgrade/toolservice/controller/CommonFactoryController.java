package com.luoys.upgrade.toolservice.controller;

import com.luoys.upgrade.toolservice.controller.vo.PageInfo;
import com.luoys.upgrade.toolservice.controller.vo.ParamVO;
import com.luoys.upgrade.toolservice.controller.vo.ToolDetailVO;
import com.luoys.upgrade.toolservice.dao.ToolMapper;
import com.luoys.upgrade.toolservice.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/commonFactory")
public class CommonFactoryController {

    @Autowired
    ToolMapper toolMapper;

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public Result<Boolean> newCommonFactory(@RequestBody ToolDetailVO toolDetailVO) {
        return null;
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
