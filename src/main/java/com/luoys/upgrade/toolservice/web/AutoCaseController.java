package com.luoys.upgrade.toolservice.web;

import com.luoys.upgrade.toolservice.service.common.CacheUtil;
import com.luoys.upgrade.toolservice.service.common.Result;
import com.luoys.upgrade.toolservice.service.CaseService;
import com.luoys.upgrade.toolservice.web.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/autoCase")
public class AutoCaseController {

    @Autowired
    private CaseService caseService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Result<String> test(@RequestParam("caseId") String caseId) {
        log.info("--->开始测试连接：caseId={}", caseId);
        // http:36336920834783  rpc:36334448520507
//        AutoCaseVO autoCaseVO = caseService.queryDetail(1);
//        autoCaseVO.setDescription("copy来的用例");
//        for (int i = 1000; i < 10000; i++) {
//            autoCaseVO.setName("http自动化批量1万copy"+(i+1));
//            caseService.copyCase(autoCaseVO);
//        }
       return Result.success("测试连接从服务器成功: " + caseId);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Result<Integer> create(@RequestHeader("loginInfo") String loginInfo, @RequestBody AutoCaseVO autoCaseVO) {
        log.info("--->开始新增用例：{}", autoCaseVO);
        autoCaseVO.setOwnerId(CacheUtil.getUserByLoginInfo(loginInfo).getUserId());
        return Result.message(caseService.create(autoCaseVO));
    }

    @RequestMapping(value = "/quickCreate", method = RequestMethod.POST)
    public Result<String> quickCreate(@RequestBody AutoCaseVO autoCaseVO,
                                      @RequestHeader(value = "projectId") Integer projectId,
                                      @RequestHeader("loginInfo") String loginInfo) {
        autoCaseVO.setProjectId(projectId);
        log.info("--->开始快速新增用例：{}", autoCaseVO);
        autoCaseVO.setOwnerId(CacheUtil.getUserByLoginInfo(loginInfo).getUserId());
        return Result.message(caseService.quickCreate(autoCaseVO));
    }

    @RequestMapping(value = "/quickCreateConfig", method = RequestMethod.POST)
    public Result<Integer> quickCreateConfig(@RequestBody ConfigVO configVO) {
        log.info("--->开始快速新增配置：{}", configVO);
        return Result.message(caseService.quickCreate(configVO));
    }

    @RequestMapping(value = "/createRelatedStep", method = RequestMethod.POST)
    public Result<AutoStepVO> createRelatedStep(@RequestBody AutoStepVO autoStepVO) {
        log.info("--->开始快速新增用例关联的步骤：{}", autoStepVO);
        return Result.success(caseService.createRelatedStep(autoStepVO));
    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public Result<Integer> remove(@RequestParam("caseId") Integer caseId) {
        log.info("--->开始删除用例：{}", caseId);
        return Result.message(caseService.remove(caseId));
    }

    @RequestMapping(value = "/removeRelatedStep", method = RequestMethod.DELETE)
    public Result<Integer> removeRelatedStep(@RequestBody AutoStepVO autoStepVO) {
        log.info("--->开始删除用例关联的步骤：{}", autoStepVO);
        return Result.message(caseService.removeRelatedStep(autoStepVO));
    }

    @RequestMapping(value = "/removeConfig", method = RequestMethod.DELETE)
    public Result<Integer> removeConfig(@RequestParam("configId") Integer configId) {
        log.info("--->开始删除配置：{}", configId);
        return Result.message(caseService.removeConfig(configId));
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<Integer> update(@RequestBody AutoCaseVO autoCaseVO) {
        log.info("--->开始更新用例：{}", autoCaseVO);
        return Result.message(caseService.update(autoCaseVO));
    }

    @RequestMapping(value = "/updateRelatedStep", method = RequestMethod.PUT)
    public Result<Integer> updateRelatedCase(@RequestBody AutoStepVO autoStepVO) {
        log.info("--->开始更新测试集关联的用例：{}", autoStepVO);
        return Result.message(caseService.updateRelatedStep(autoStepVO));
    }

    @RequestMapping(value = "/updateConfig", method = RequestMethod.PUT)
    public Result<Integer> updateConfig(@RequestBody ConfigVO configVO) {
        log.info("--->开始更新配置：{}", configVO);
        return Result.message(caseService.updateConfig(configVO));
    }

    @RequestMapping(value = "/updateScript", method = RequestMethod.PUT)
    public Result<ScriptVO> updateScript(@RequestBody ScriptVO scriptVO) {
        log.info("--->开始更新脚本：{}", scriptVO.getCaseId());
        return Result.success(caseService.updateScript(scriptVO));
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public Result<PageInfo<AutoCaseSimpleVO>> query(@RequestHeader("loginInfo") String loginInfo,
                                                     @RequestHeader("projectId") Integer projectId,
                                                    @RequestBody QueryVO queryVO) {
        queryVO.setProjectId(projectId);
        log.info("--->开始查询用例列表：{}", queryVO);
        queryVO.setUserId(CacheUtil.getUserByLoginInfo(loginInfo).getUserId());
        PageInfo<AutoCaseSimpleVO> pageInfo = new PageInfo();
        pageInfo.setList(caseService.query(queryVO));
        pageInfo.setTotal(caseService.count(queryVO));
        return Result.success(pageInfo);
    }

    @RequestMapping(value = "/queryConfig", method = RequestMethod.GET)
    public Result<ConfigVO> queryConfig(@RequestParam("caseId") Integer caseId,
                                        @RequestParam("type") Integer type) {
        log.info("--->开始查询配置详情：caseId={}, type={}", caseId, type);
        return Result.success(caseService.queryConfig(caseId, type));
    }

    @RequestMapping(value = "/queryDetail", method = RequestMethod.GET)
    public Result<AutoCaseVO> queryDetail(@RequestParam("caseId") Integer caseId) {
        log.info("--->开始查询用例详情：caseId={}", caseId);
        return Result.success(caseService.queryDetail(caseId));
    }

    @RequestMapping(value = "/use", method = RequestMethod.POST)
    public Result<Boolean> use(@RequestBody AutoCaseVO autoCaseVO) {
        log.info("--->开始执行用例：caseId={}", autoCaseVO.getCaseId());
        try {
            return Result.message(caseService.useAsync(autoCaseVO), "执行异常，请检查步骤");
        } catch (RejectedExecutionException e) {
            return Result.errorMessage("执行队列已满，请稍后再试");
        }
    }

}
