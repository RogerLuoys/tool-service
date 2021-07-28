package com.luoys.upgrade.toolservice.web;

import com.luoys.upgrade.toolservice.web.vo.PageInfo;
import com.luoys.upgrade.toolservice.web.vo.ToolSimpleVO;
import com.luoys.upgrade.toolservice.web.vo.ToolVO;
import com.luoys.upgrade.toolservice.common.Result;
import com.luoys.upgrade.toolservice.service.FactoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/commonFactory")
public class CommonFactoryController {
//
//    @Autowired
//    private ToolMapper toolMapper;
//
    @Autowired
    private FactoryService factoryService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Result<String> create(@RequestHeader("userId") String userId, @RequestBody ToolVO toolVO) {
        toolVO.setOwnerId(userId);
        log.info("---》开始新增通用工具：{}", toolVO);
//        if (toolVO.getTitle() == null) {
//            return Result.error("标题不能为空");
//        }
//        int result = toolMapper.insert(TransformTool.transformVO2PO(toolVO));
//        return result == 1 ? Result.success("新增成功") : Result.error("新增失败");
        return Result.message(factoryService.create(toolVO));
    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public Result<String> remove(@RequestParam("toolId") String toolId) {
        log.info("---》开始删除通用工具：{}", toolId);
//        int result = toolMapper.deleteByUID(toolId);
//        return result == 1 ? Result.success("删除成功") : Result.error("删除失败");
        return Result.message(factoryService.remove(toolId));

    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<Boolean> update(@RequestBody ToolVO toolVO) {
        log.info("---》开始更新通用工具：{}", toolVO);
//        int result = toolMapper.updateByUID(TransformTool.transformVO2PO(toolVO));
//        return result == 1 ? Result.success("成功") : Result.error("更新失败");
        return Result.message(factoryService.update(toolVO));
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Result<PageInfo<ToolSimpleVO>> query(@RequestHeader("userId") String userId,
                                                @RequestParam(value = "isOnlyOwner", required = false) Boolean isOnlyOwner,
                                                @RequestParam(value = "type", required = false) Integer type,
                                                @RequestParam(value = "name", required = false) String name,
                                                @RequestParam("isTestStep") Boolean isTestStep,
                                                @RequestParam("pageIndex") Integer pageIndex) {
        log.info("---》开始查询通用工具列表：type={}, title={}, userId={}, startIndex={}", type, name, userId, pageIndex);
        PageInfo<ToolSimpleVO> pageInfo = new PageInfo<>();
        //数据库startIndex从0开始
        pageInfo.setList(factoryService.query(userId, isOnlyOwner, type, name, isTestStep, pageIndex));
        pageInfo.setTotal(pageInfo.getList().size());
        return Result.success(pageInfo);
    }

    @RequestMapping(value = "/queryDetail", method = RequestMethod.GET)
    public Result<ToolVO> queryDetail(@RequestParam("toolId") String toolId) {
        log.info("---》开始查询通用工具详情：toolId={}", toolId);
//        return Result.success(TransformTool.transformPO2VO(toolMapper.selectByUID(toolId)));
        return Result.success(factoryService.queryDetail(toolId));
    }

    @RequestMapping(value = "/use", method = RequestMethod.POST)
    public Result<String> use(@RequestBody ToolVO toolVO) {
        log.info("---》开始使用通用工具：{}", toolVO);
        return Result.message(factoryService.use(toolVO), "使用工具失败");
    }
}
