package com.luoys.upgrade.toolservice.web;

import com.luoys.upgrade.toolservice.service.UserService;
import com.luoys.upgrade.toolservice.service.common.CacheUtil;
import com.luoys.upgrade.toolservice.service.common.Result;
import com.luoys.upgrade.toolservice.service.SuiteService;
import com.luoys.upgrade.toolservice.service.dto.SuiteDTO;
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
    public Result<Integer> create(@RequestBody AutoSuiteVO autoSuiteVO) {
        log.info("--->开始新增套件：{}", autoSuiteVO);
        return Result.message(suiteService.create(autoSuiteVO));
    }

    @RequestMapping(value = "/quickCreate", method = RequestMethod.POST)
    public Result<Integer> quickCreate(@RequestBody AutoSuiteVO autoSuiteVO,
                                      @RequestHeader(value = "projectId") Integer projectId,
                                      @RequestHeader(value = "loginInfo") String loginInfo) {
        log.info("--->开始快速新增套件：{}", autoSuiteVO);
        autoSuiteVO.setOwnerId(CacheUtil.getUserByLoginInfo(loginInfo).getUserId());
        autoSuiteVO.setProjectId(projectId);
        return Result.message(suiteService.quickCreate(autoSuiteVO));
    }

    @RequestMapping(value = "/createRelatedSlave", method = RequestMethod.POST)
    public Result<Integer> createRelatedCase(@RequestBody SuiteCaseVO suiteCaseVO) {
        log.info("--->开始新增套件关联的用例：{}", suiteCaseVO);
        return Result.message(suiteService.createRelatedCase(suiteCaseVO));
    }

    @RequestMapping(value = "/createRelatedSlave", method = RequestMethod.GET)
    public Result<Integer> createRelatedSlave(@RequestParam("suiteId") Integer suiteId,
                                             @RequestParam("resourceId") Integer resourceId) {
        log.info("--->开始新增套件关联的机器：");
        return Result.message(suiteService.createRelateSlave(suiteId, resourceId));
    }

    @RequestMapping(value = "/batchRelatedCase", method = RequestMethod.GET)
    public Result<Integer> batchRelatedCase(@RequestParam("suiteId") Integer suiteId,
                                            @RequestHeader("loginInfo") String loginInfo,
                                            @RequestParam("caseName") String caseName) {
        log.info("--->开始批量关联的用例");
        Integer userId = CacheUtil.getUserByLoginInfo(loginInfo).getUserId();
        return Result.message(suiteService.createRelatedCase(suiteId, userId, caseName));
    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public Result<Integer> remove(@RequestParam("suiteId") Integer suiteId) {
        log.info("--->开始删除套件：suiteId={}", suiteId);
        return Result.message(suiteService.remove(suiteId));
    }

    @RequestMapping(value = "/removeRelatedCase", method = RequestMethod.DELETE)
    public Result<Integer> removeRelatedCase(@RequestParam("suiteId") Integer suiteId,
                                             @RequestParam("caseId") Integer caseId) {
        log.info("--->开始删除套件关联的用例：suiteId={}, caseId={}", suiteId, caseId);
        return Result.message(suiteService.removeRelatedCase(suiteId, caseId));
    }

    @RequestMapping(value = "/removeRelatedSlave", method = RequestMethod.DELETE)
    public Result<Integer> removeRelatedSlave(@RequestParam("suiteId") Integer suiteId,
                                             @RequestParam("resourceId") Integer resourceId) {
        log.info("--->开始删除套件关联的机器：suiteId={}, caseId={}", suiteId, resourceId);
        return Result.message(suiteService.removeRelatedSlave(suiteId, resourceId));
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<Integer> update(@RequestBody AutoSuiteVO autoSuiteVO) {
        log.info("--->开始更新用例：{}", autoSuiteVO);
        return Result.message(suiteService.update(autoSuiteVO));
    }

    @RequestMapping(value = "/updateRelatedCase", method = RequestMethod.PUT)
    public Result<Integer> updateRelatedCase(@RequestBody SuiteCaseVO suiteCaseVO) {
        log.info("--->开始更新套件关联的用例：{}", suiteCaseVO);
        return Result.message(suiteService.updateRelatedCase(suiteCaseVO));
    }

    @RequestMapping(value = "/reset", method = RequestMethod.PUT)
    public Result<Integer> reset(@RequestParam Integer suiteId) {
        log.info("--->开始重置套件：suiteId={}", suiteId);
        return Result.message(suiteService.reset(suiteId));
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public Result<PageInfo<AutoSuiteSimpleVO>> query(@RequestHeader(value = "loginInfo") String loginInfo,
                                                     @RequestHeader(value = "projectId") Integer projectId,
                                                     @RequestBody QueryVO queryVO) {
        queryVO.setProjectId(projectId);
        log.info("--->开始查询套件列表：{}", queryVO);
        queryVO.setUserId(CacheUtil.getUserByLoginInfo(loginInfo).getUserId());
        PageInfo<AutoSuiteSimpleVO> pageInfo = new PageInfo<>();
        pageInfo.setList(suiteService.query(queryVO));
        pageInfo.setTotal(suiteService.count(queryVO));
        return Result.success(pageInfo);
    }

    @RequestMapping(value = "/queryDetail", method = RequestMethod.GET)
    public Result<AutoSuiteVO> queryDetail(@RequestParam("suiteId") Integer suiteId) {
        log.info("--->开始查询套件详情：suiteId={}", suiteId);
        return Result.success(suiteService.queryDetail(suiteId));
    }

    @RequestMapping(value = "/queryRelateCase", method = RequestMethod.POST)
    public Result<PageInfo<SuiteCaseVO>> queryRelateCase(@RequestBody QueryVO queryVO) {
        log.info("--->开始查询套件关联的用例：{}", queryVO);
        return Result.success(suiteService.queryRelateCase(queryVO));
    }

    @RequestMapping(value = "/executeByLocal", method = RequestMethod.GET)
    public Result<String> executeByLocal(@RequestParam("suiteId") Integer suiteId,
                                         @RequestParam(value = "retry", required = false) Boolean retry) {
        log.info("--->开始执行套件(localhost)：suiteId={}, retry={}", suiteId, retry);
        try {
            return Result.message(suiteService.executeByLocal(suiteId, retry), "套件未执行，请检查状态");
        } catch (RejectedExecutionException e) {
            return Result.errorMessage("执行队列已满，请稍后再试");
        }
    }

    @RequestMapping(value = "/executeBySchedule", method = RequestMethod.POST)
    public Result<String> executeBySchedule(@RequestBody AutoSuiteVO autoSuiteVO) {
        log.info("--->开始执行套件(调度)：autoSuiteVO={}", autoSuiteVO);
        try {
            return Result.message(suiteService.executeBySchedule(autoSuiteVO), "套件未执行，请检查状态");
        } catch (RejectedExecutionException e) {
            return Result.errorMessage("执行队列已满，请稍后再试");
        }
    }

    // 此接口给后端调用
    @RequestMapping(value = "/scheduleRun", method = RequestMethod.POST)
    public Result<String> executeBySchedule(@RequestBody SuiteDTO suiteDTO) {
        log.info("--->开始执行套件(通过服务器调度)：{}", suiteDTO);
        try {
            return Result.success(suiteService.scheduleRun(suiteDTO));
        } catch (RejectedExecutionException e) {
            return Result.error("执行队列已满，请稍后再试");
        }
    }

    @RequestMapping(value = "/executeBySingle", method = RequestMethod.POST)
    public Result<String> executeBySingle(@RequestBody SuiteCaseVO suiteCaseVO) {
        log.info("--->开始执行套件的单个用例：{}", suiteCaseVO);
        try {
            return Result.message(suiteService.executeBySingle(suiteCaseVO), "套件未执行，请检查状态");
        } catch (RejectedExecutionException e) {
            return Result.errorMessage("执行队列已满，请稍后再试");
        }
    }

}
