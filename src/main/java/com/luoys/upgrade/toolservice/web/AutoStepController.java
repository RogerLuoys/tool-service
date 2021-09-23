package com.luoys.upgrade.toolservice.web;

import com.luoys.upgrade.toolservice.common.Result;
import com.luoys.upgrade.toolservice.service.StepService;
import com.luoys.upgrade.toolservice.service.enums.AutoStepTypeEnum;
import com.luoys.upgrade.toolservice.web.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/autoStep")
public class AutoStepController {

    @Autowired
    private StepService stepService;


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Result<Boolean> create(@RequestHeader("userId") String userId, @RequestBody AutoStepVO autoStepVO) {
        autoStepVO.setOwnerId(userId);
        log.info("--->开始新增步骤：{}", autoStepVO);
        return Result.message(stepService.create(autoStepVO));
    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public Result<Boolean> remove(@RequestParam("stepId") String stepId) {
        log.info("--->开始删除步骤：{}", stepId);
        return Result.message(stepService.remove(stepId));
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<Boolean> update(@RequestBody AutoStepVO autoStepVO) {
        log.info("--->开始更新步骤：{}", autoStepVO);
        return Result.message(stepService.update(autoStepVO));
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Result<PageInfo<AutoStepSimpleVO>> query(@RequestHeader("userId") String userId,
                                                    @RequestParam(value = "isOnlyOwner", required = false) Boolean isOnlyOwner,
                                                    @RequestParam(value = "type", required = false) Integer type,
                                                    @RequestParam(value = "name", required = false) String name,
                                                    @RequestParam(value = "isPublic", required = false) Boolean isPublic,
                                                    @RequestParam("pageIndex") Integer pageIndex) {
        log.info("--->开始查询步骤列表：");
        PageInfo<AutoStepSimpleVO> pageInfo = new PageInfo();
        pageInfo.setList(stepService.query(userId, isOnlyOwner, type, name, isPublic, pageIndex));
        pageInfo.setTotal(stepService.count(userId, isOnlyOwner, type, name, isPublic));
        return Result.success(pageInfo);
    }

    @RequestMapping(value = "/queryDetail", method = RequestMethod.GET)
    public Result<AutoStepVO> queryDetail(@RequestParam("stepId") String stepId) {
        log.info("--->开始查询步骤详情：stepId={}", stepId);
        return Result.success(stepService.queryDetail(stepId));
    }

    @RequestMapping(value = "/use", method = RequestMethod.POST)
    public Result<String> use(@RequestBody AutoStepVO autoStepVO) {
        log.info("--->开始调试步骤：{}", autoStepVO);
        if (autoStepVO.getType().equals(AutoStepTypeEnum.STEP_UI.getCode())) {
            return Result.error("UI自动化步骤不可单步调试");
        }
        try {
            return Result.message(stepService.useAsync(autoStepVO), "执行异常，请检查步骤");
        } catch (RejectedExecutionException e) {
            return Result.errorMessage("执行队列已满，请稍后再试");
        }
    }
}
