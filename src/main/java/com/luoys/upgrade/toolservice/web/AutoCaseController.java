package com.luoys.upgrade.toolservice.web;

import com.luoys.upgrade.toolservice.common.Result;
import com.luoys.upgrade.toolservice.service.CaseService;
import com.luoys.upgrade.toolservice.service.enums.AutoStepTypeEnum;
import com.luoys.upgrade.toolservice.service.enums.KeywordEnum;
import com.luoys.upgrade.toolservice.web.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/autoCase")
public class AutoCaseController {

    @Autowired
    private CaseService caseService;


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Result<Boolean> create(@RequestHeader("userId") String userId, @RequestBody AutoCaseVO autoCaseVO) {
        autoCaseVO.setOwnerId(userId);
        log.info("---》开始新增用例：{}", autoCaseVO);
        return Result.message(caseService.create(autoCaseVO));
    }

    @RequestMapping(value = "/quickCreate", method = RequestMethod.POST)
    public Result<Boolean> quickCreate(@RequestBody AutoCaseVO autoCaseVO,
                                       @RequestHeader(value = "userId") String userId) {
        log.info("---》开始快速新增用例");
        autoCaseVO.setOwnerId(userId);
        autoCaseVO.setOwnerName(KeywordEnum.DEFAULT_USER.getDescription());
        return Result.message(caseService.quickCreate(autoCaseVO));
    }

    @RequestMapping(value = "/createRelatedStep", method = RequestMethod.POST)
    public Result<Boolean> createRelatedStep(@RequestBody CaseStepVO caseStepVO) {
        log.info("---》开始新增测试集关联的用例：{}", caseStepVO);
        return Result.message(caseService.createRelatedStep(caseStepVO));
    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public Result<Boolean> remove(@RequestParam("caseId") String caseId) {
        log.info("---》开始删除用例：{}", caseId);
        return Result.message(caseService.remove(caseId));
    }

    @RequestMapping(value = "/removeRelatedStep", method = RequestMethod.DELETE)
    public Result<Boolean> removeRelatedStep(@RequestBody CaseStepVO caseStepVO) {
        log.info("---》开始删除测试集关联的用例：{}", caseStepVO);
        return Result.message(caseService.removeRelatedStep(caseStepVO));
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<Boolean> update(@RequestBody AutoCaseVO autoCaseVO) {
        log.info("---》开始更新用例：{}", autoCaseVO);
        return Result.message(caseService.update(autoCaseVO));
    }

    @RequestMapping(value = "/updateRelatedStep", method = RequestMethod.PUT)
    public Result<Boolean> updateRelatedCase(@RequestBody CaseStepVO caseStepVO) {
        log.info("---》开始更新测试集关联的用例：{}", caseStepVO);
        return Result.message(caseService.updateRelatedStep(caseStepVO));
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Result<PageInfo<AutoCaseSimpleVO>> query(@RequestHeader("userId") String userId,
                                                    @RequestParam(value = "isOnlyOwner", required = false) Boolean isOnlyOwner,
                                                    @RequestParam(value = "status", required = false) Integer status,
                                                    @RequestParam(value = "name", required = false) String name,
                                                    @RequestParam("pageIndex") Integer pageIndex) {
        log.info("---》开始查询用例列表：");
        PageInfo<AutoCaseSimpleVO> pageInfo = new PageInfo(caseService.query(userId, isOnlyOwner, status, name, pageIndex));
        return Result.success(pageInfo);
    }

    @RequestMapping(value = "/queryDetail", method = RequestMethod.GET)
    public Result<AutoCaseVO> queryDetail(@RequestParam("caseId") String caseId) {
        log.info("---》开始查询用例详情：caseId={}", caseId);
        return Result.success(caseService.queryDetail(caseId));
    }

    @RequestMapping(value = "/use", method = RequestMethod.POST)
    public Result<String> use(@RequestBody AutoCaseVO autoCaseVO) {
        log.info("---》开始调试用例：{}", autoCaseVO);
        return Result.message(caseService.useAsync(autoCaseVO), "执行异常，请确认步骤是否正常");
    }
}
