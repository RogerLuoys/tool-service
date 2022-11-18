package com.luoys.upgrade.toolservice.web;


import com.luoys.upgrade.toolservice.common.Result;
import com.luoys.upgrade.toolservice.service.FactoryService;
import com.luoys.upgrade.toolservice.web.vo.PageInfo;
import com.luoys.upgrade.toolservice.web.vo.ToolSimpleVO;
import com.luoys.upgrade.toolservice.web.vo.ToolVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Deprecated
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/factory")
public class FactoryController {

    @Autowired
    private FactoryService factoryService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Result<String> create(@RequestHeader("userId") String userId, @RequestBody ToolVO toolVO) {
        toolVO.setOwnerId(userId);
        log.info("--->开始新增通用工具：{}", toolVO);
        return Result.message(factoryService.create(toolVO));
    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public Result<String> remove(@RequestParam("toolId") String toolId) {
        log.info("--->开始删除通用工具：{}", toolId);
        return Result.message(factoryService.remove(toolId));

    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<Boolean> update(@RequestBody ToolVO toolVO) {
        log.info("--->开始更新通用工具：{}", toolVO);
        return Result.message(factoryService.update(toolVO));
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Result<PageInfo<ToolSimpleVO>> query(@RequestHeader("userId") String userId,
                                                @RequestParam(value = "isOnlyOwner", required = false) Boolean isOnlyOwner,
                                                @RequestParam(value = "type", required = false) Integer type,
                                                @RequestParam(value = "name", required = false) String name,
                                                @RequestParam("pageIndex") Integer pageIndex) {
        log.info("--->开始查询通用工具列表：type={}, title={}, userId={}, startIndex={}", type, name, userId, pageIndex);
        PageInfo<ToolSimpleVO> pageInfo = new PageInfo<>();
        pageInfo.setList(factoryService.query(userId, isOnlyOwner, type, name, pageIndex));
        pageInfo.setTotal(factoryService.count(userId, isOnlyOwner, type, name));
        return Result.success(pageInfo);
    }

    @RequestMapping(value = "/queryDetail", method = RequestMethod.GET)
    public Result<ToolVO> queryDetail(@RequestParam("toolId") String toolId) {
        log.info("--->开始查询通用工具详情：toolId={}", toolId);
        return Result.success(factoryService.queryDetail(toolId));
    }

    @RequestMapping(value = "/use", method = RequestMethod.POST)
    public Result<String> use(@RequestBody ToolVO toolVO) {
        log.info("--->开始使用通用工具：{}", toolVO);
        return Result.message(factoryService.use(toolVO), "执行异常，请确认参数是否正常");
    }
}
