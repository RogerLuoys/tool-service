package com.luoys.upgrade.toolservice.web;

import com.luoys.upgrade.toolservice.common.Result;
import com.luoys.upgrade.toolservice.service.SuiteService;
import com.luoys.upgrade.toolservice.service.enums.KeywordEnum;
import com.luoys.upgrade.toolservice.web.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/autoSuite")
public class AutoSuiteController {

    @Autowired
    private SuiteService suiteService;


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Result<Boolean> create(@RequestBody AutoSuiteVO autoSuiteVO) {
        log.info("--->开始新增套件：{}", autoSuiteVO);
        return Result.message(suiteService.create(autoSuiteVO));
    }

    @RequestMapping(value = "/quickCreate", method = RequestMethod.POST)
    public Result<String> quickCreate(@RequestBody AutoSuiteVO autoSuiteVO,
                                       @RequestHeader(value = "userId") String userId) {
        log.info("--->开始快速新增套件：{}", autoSuiteVO);
        autoSuiteVO.setOwnerId(userId);
        autoSuiteVO.setOwnerName(KeywordEnum.DEFAULT_USER.getValue());
        return Result.message(suiteService.quickCreate(autoSuiteVO));
    }

    @RequestMapping(value = "/createRelatedCase", method = RequestMethod.POST)
    public Result<Boolean> createRelatedCase(@RequestBody SuiteCaseVO suiteCaseVO) {
        log.info("--->开始新增套件关联的用例：{}", suiteCaseVO);
        return Result.message(suiteService.createRelatedCase(suiteCaseVO));
    }

    @RequestMapping(value = "/batchRelatedCase", method = RequestMethod.GET)
    public Result<Boolean> batchRelatedCase(@RequestParam("suiteId") String suiteId,
                                            @RequestHeader("userId") String userId,
                                            @RequestParam("caseName") String caseName) {
        log.info("--->开始批量关联的用例");
        return Result.message(suiteService.createRelatedCase(suiteId, userId, caseName));
    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public Result<Boolean> remove(@RequestParam("suiteId") String suiteId) {
        log.info("--->开始删除套件：suiteId={}", suiteId);
        return Result.message(suiteService.remove(suiteId));
    }

    @RequestMapping(value = "/removeRelatedCase", method = RequestMethod.DELETE)
    public Result<Boolean> removeRelatedCase(@RequestParam("suiteId") String suiteId,
                                             @RequestParam("caseId") String caseId) {
        log.info("--->开始删除套件关联的用例：suiteId={}, caseId={}", suiteId, caseId);
        return Result.message(suiteService.removeRelatedCase(suiteId, caseId));
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<Boolean> update(@RequestBody AutoSuiteVO autoSuiteVO) {
        log.info("--->开始更新用例：{}", autoSuiteVO);
        return Result.message(suiteService.update(autoSuiteVO));
    }

    @RequestMapping(value = "/updateRelatedCase", method = RequestMethod.PUT)
    public Result<Boolean> updateRelatedCase(@RequestBody SuiteCaseVO suiteCaseVO) {
        log.info("--->开始更新套件关联的用例：{}", suiteCaseVO);
        return Result.message(suiteService.updateRelatedCase(suiteCaseVO));
    }

    @RequestMapping(value = "/reset", method = RequestMethod.PUT)
    public Result<Boolean> reset(@RequestParam String suiteId) {
        log.info("--->开始重置套件：suiteId={}", suiteId);
        return Result.message(suiteService.reset(suiteId));
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Result<PageInfo<AutoSuiteSimpleVO>> query(@RequestParam(value = "name", required = false) String name,
                                                     @RequestHeader(value = "userId") String userId,
                                                     @RequestParam("pageIndex") Integer pageIndex) {
        log.info("--->开始查询套件列表：");
        PageInfo<AutoSuiteSimpleVO> pageInfo = new PageInfo<>();
        pageInfo.setList(suiteService.query(name, userId, pageIndex));
        pageInfo.setTotal(suiteService.count(name, userId));
        return Result.success(pageInfo);
    }

    @RequestMapping(value = "/queryDetail", method = RequestMethod.GET)
    public Result<AutoSuiteVO> queryDetail(@RequestParam("suiteId") String suiteId,
                                           @RequestParam("startIndex") Integer startIndex) {
        log.info("--->开始查询套件详情：suiteId={}", suiteId);
        return Result.success(suiteService.queryDetail(suiteId, startIndex));
    }

    @RequestMapping(value = "/use", method = RequestMethod.GET)
    public Result<String> use(@RequestParam("suiteId") String suiteId,
                              @RequestParam(value = "retry", required = false) Boolean retry) {
        log.info("--->开始执行套件：suiteId={}, retry={}", suiteId, retry);
        try {
            return Result.message(suiteService.useAsync(suiteId, retry), "套件未执行，请检查状态");
        } catch (RejectedExecutionException e) {
            return Result.errorMessage("执行队列已满，请稍后再试");
        }
    }

    @RequestMapping(value = "/useSingle", method = RequestMethod.POST)
    public Result<String> useSingle(@RequestBody SuiteCaseVO suiteCaseVO) {
        log.info("--->开始执行套件的单个用例：{}", suiteCaseVO);
        try {
            return Result.message(suiteService.useAsync(suiteCaseVO), "套件未执行，请检查状态");
        } catch (RejectedExecutionException e) {
            return Result.errorMessage("执行队列已满，请稍后再试");
        }
    }

}
