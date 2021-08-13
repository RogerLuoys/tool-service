package com.luoys.upgrade.toolservice.web;

import com.luoys.upgrade.toolservice.common.Result;
import com.luoys.upgrade.toolservice.service.SuiteService;
import com.luoys.upgrade.toolservice.service.enums.KeywordEnum;
import com.luoys.upgrade.toolservice.web.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/autoSuite")
public class AutoSuiteController {

    @Autowired
    private SuiteService suiteService;


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Result<Boolean> create(@RequestBody AutoSuiteVO autoSuiteVO) {
        log.info("---》开始新增测试集：{}", autoSuiteVO);
        return Result.message(suiteService.create(autoSuiteVO));
    }

    @RequestMapping(value = "/quickCreate", method = RequestMethod.GET)
    public Result<Boolean> quickCreate(@RequestParam(value = "name") String name,
                                       @RequestHeader(value = "userId") String userId) {
        log.info("---》开始快速新增测试集：{}", name);
        AutoSuiteVO autoSuiteVO = new AutoSuiteVO();
        autoSuiteVO.setOwnerId(userId);
        autoSuiteVO.setOwnerName(KeywordEnum.DEFAULT_USER.getDescription());
        return Result.message(suiteService.quickCreate(autoSuiteVO));
    }

    @RequestMapping(value = "/createRelatedCase", method = RequestMethod.POST)
    public Result<Boolean> createRelatedCase(@RequestBody SuiteCaseVO suiteCaseVO) {
        log.info("---》开始新增测试集关联的用例：{}", suiteCaseVO);
        return Result.message(suiteService.createRelatedCase(suiteCaseVO));
    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public Result<Boolean> remove(@RequestParam("testSuiteId") String testSuiteId) {
        log.info("---》开始删除用例：{}", testSuiteId);
        return Result.message(suiteService.remove(testSuiteId));
    }

    @RequestMapping(value = "/removeRelatedCase", method = RequestMethod.DELETE)
    public Result<Boolean> removeRelatedCase(@RequestBody SuiteCaseVO suiteCaseVO) {
        log.info("---》开始删除测试集关联的用例：{}", suiteCaseVO);
        return Result.message(suiteService.removeRelatedCase(suiteCaseVO));
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<Boolean> update(@RequestBody AutoSuiteVO autoSuiteVO) {
        log.info("---》开始更新用例：{}", autoSuiteVO);
        return Result.message(suiteService.update(autoSuiteVO));
    }

    @RequestMapping(value = "/updateRelatedCase", method = RequestMethod.PUT)
    public Result<Boolean> updateRelatedCase(@RequestBody SuiteCaseVO suiteCaseVO) {
        log.info("---》开始更新测试集关联的用例：{}", suiteCaseVO);
        return Result.message(suiteService.updateRelatedCase(suiteCaseVO));
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Result<PageInfo<AutoSuiteSimpleVO>> query(@RequestParam(value = "name", required = false) String name,
                                                     @RequestHeader(value = "userId") String userId,
                                                     @RequestParam("pageIndex") Integer pageIndex) {
        log.info("---》开始查询用例列表：");
        PageInfo<AutoSuiteSimpleVO> pageInfo = new PageInfo<>(suiteService.query(name, userId, pageIndex));
        return Result.success(pageInfo);
    }

    @RequestMapping(value = "/queryDetail", method = RequestMethod.GET)
    public Result<AutoSuiteVO> queryDetail(@RequestParam("suiteId") String suiteId) {
        log.info("---》开始查询用例详情：suiteId={}", suiteId);
        return Result.success(suiteService.queryDetail(suiteId));
    }

    @RequestMapping(value = "/use", method = RequestMethod.POST)
    public Result<String> use(@RequestBody AutoSuiteVO autoSuiteVO) {
        log.info("---》开始执行测试集：{}", autoSuiteVO);
        return Result.message(suiteService.useAsync(autoSuiteVO), "超出队列，请稍后");
    }

}
