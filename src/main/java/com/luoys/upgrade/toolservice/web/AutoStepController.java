package com.luoys.upgrade.toolservice.web;

import com.luoys.upgrade.toolservice.service.common.Result;
import com.luoys.upgrade.toolservice.service.StepService;
import com.luoys.upgrade.toolservice.web.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/autoStep")
public class AutoStepController {

    @Autowired
    private StepService stepService;


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Result<Integer> create(@RequestBody AutoStepVO autoStepVO) {
        log.info("--->开始新增步骤：{}", autoStepVO);
        return Result.message(stepService.create(autoStepVO));
    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public Result<Integer> remove(@RequestParam("stepId") Integer stepId) {
        log.info("--->开始删除步骤：{}", stepId);
        return Result.message(stepService.remove(stepId));
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<Integer> update(@RequestBody AutoStepVO autoStepVO) {
        log.info("--->开始更新步骤：{}", autoStepVO);
        return Result.message(stepService.update(autoStepVO));
    }

//    @RequestMapping(value = "/query", method = RequestMethod.GET)
//    public Result<PageInfo<AutoStepSimpleVO>> query(@RequestHeader("userId") String userId,
//                                                    @RequestParam(value = "isOnlyOwner", required = false) Boolean isOnlyOwner,
//                                                    @RequestParam(value = "type", required = false) Integer type,
//                                                    @RequestParam(value = "name", required = false) String name,
//                                                    @RequestParam(value = "isPublic", required = false) Boolean isPublic,
//                                                    @RequestParam("pageIndex") Integer pageIndex) {
//        log.info("--->开始查询步骤列表：");
//        PageInfo<AutoStepSimpleVO> pageInfo = new PageInfo();
//        pageInfo.setList(stepService.query(userId, isOnlyOwner, type, name, isPublic, pageIndex));
//        pageInfo.setTotal(stepService.count(userId, isOnlyOwner, type, name, isPublic));
//        return Result.success(pageInfo);
//    }

    @RequestMapping(value = "/queryDetail", method = RequestMethod.GET)
    public Result<AutoStepVO> queryDetail(@RequestParam("stepId") Integer stepId) {
        log.info("--->开始查询步骤详情：stepId={}", stepId);
        return Result.success(stepService.queryDetail(stepId));
    }

}
