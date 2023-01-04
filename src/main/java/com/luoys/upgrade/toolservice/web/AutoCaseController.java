package com.luoys.upgrade.toolservice.web;

import com.luoys.upgrade.toolservice.service.UserService;
import com.luoys.upgrade.toolservice.service.common.Result;
import com.luoys.upgrade.toolservice.service.CaseService;
import com.luoys.upgrade.toolservice.service.enums.KeywordEnum;
import com.luoys.upgrade.toolservice.web.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/autoCase")
public class AutoCaseController {

    @Autowired
    private CaseService caseService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Result<String> test(@RequestParam("caseId") String caseId) {
        log.info("--->开始测试连接：caseId={}", caseId);
        // http:36336920834783  rpc:36334448520507
        AutoCaseVO autoCaseVO = caseService.queryDetail(1);
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
        autoCaseVO.setOwnerId(userService.queryByLoginInfo(loginInfo).getUserId());
        return Result.message(caseService.create(autoCaseVO));
    }

    @RequestMapping(value = "/quickCreate", method = RequestMethod.POST)
    public Result<String> quickCreate(@RequestBody AutoCaseVO autoCaseVO,
                                      @RequestHeader(value = "projectId") Integer projectId,
                                       @RequestHeader(value = "userId") Integer userId) {
        autoCaseVO.setOwnerId(userId);
        autoCaseVO.setProjectId(projectId);
        autoCaseVO.setOwnerName(KeywordEnum.DEFAULT_USER.getValue());
        log.info("--->开始快速新增用例：{}", autoCaseVO);
        return Result.message(caseService.quickCreate(autoCaseVO));
    }

    @RequestMapping(value = "/createRelatedStep", method = RequestMethod.POST)
    public Result<Boolean> createRelatedStep(@RequestBody CaseStepVO caseStepVO) {
        log.info("--->开始新增测试集关联的用例：{}", caseStepVO);
        return Result.message(caseService.createRelatedStep(caseStepVO));
    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public Result<Boolean> remove(@RequestParam("caseId") Integer caseId) {
        log.info("--->开始删除用例：{}", caseId);
        return Result.message(caseService.remove(caseId));
    }

    @RequestMapping(value = "/removeRelatedStep", method = RequestMethod.DELETE)
    public Result<Boolean> removeRelatedStep(@RequestBody CaseStepVO caseStepVO) {
        log.info("--->开始删除测试集关联的用例：{}", caseStepVO);
        return Result.message(caseService.removeRelatedStep(caseStepVO));
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<Boolean> update(@RequestBody AutoCaseVO autoCaseVO) {
        log.info("--->开始更新用例：{}", autoCaseVO);
        return Result.message(caseService.update(autoCaseVO));
    }

    @RequestMapping(value = "/updateRelatedStep", method = RequestMethod.PUT)
    public Result<Boolean> updateRelatedCase(@RequestBody CaseStepVO caseStepVO) {
        log.info("--->开始更新测试集关联的用例：{}", caseStepVO);
        return Result.message(caseService.updateRelatedStep(caseStepVO));
    }

    @RequestMapping(value = "/changeUiMode", method = RequestMethod.PUT)
    public Result<Boolean> changeUiMode(@RequestBody AutoCaseVO autoCaseVO) {
        log.info("--->开始将脚本步骤转换为可视化步骤：{}", autoCaseVO);
        return Result.success(caseService.change2UiMode(autoCaseVO));
    }

    @RequestMapping(value = "/changeScriptMode", method = RequestMethod.PUT)
    public Result<Boolean> changeScriptMode(@RequestBody AutoCaseVO autoCaseVO) {
        log.info("--->开始将可视化步骤转换为脚本步骤：{}", autoCaseVO);
        return Result.message(caseService.change2ScriptMode(autoCaseVO));
    }

//    @RequestMapping(value = "/query", method = RequestMethod.GET)
//    public Result<PageInfo<AutoCaseSimpleVO>> query(@RequestHeader("loginInfo") String loginInfo,
//                                                    @RequestParam(value = "isOnlyOwner", required = false) Boolean isOnlyOwner,
//                                                    @RequestParam(value = "status", required = false) Integer status,
//                                                    @RequestParam(value = "name", required = false) String name,
//                                                    @RequestParam("pageIndex") Integer pageIndex) {
//        log.info("--->开始查询用例列表：");
//        PageInfo<AutoCaseSimpleVO> pageInfo = new PageInfo();
//        pageInfo.setList(caseService.query(userId, isOnlyOwner, status, name, pageIndex));
//        pageInfo.setTotal(caseService.count(userId, isOnlyOwner, status, name));
//        return Result.success(pageInfo);
//    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public Result<PageInfo<AutoCaseSimpleVO>> query2(@RequestHeader("loginInfo") String loginInfo,
                                                     @RequestHeader("projectId") Integer projectId,
                                                    @RequestBody QueryVO queryVO) {
        queryVO.setProjectId(projectId);
        log.info("--->开始查询用例列表：{}", queryVO);
        queryVO.setUserId(userService.queryByLoginInfo(loginInfo).getUserId());
        PageInfo<AutoCaseSimpleVO> pageInfo = new PageInfo();
        pageInfo.setList(caseService.query(queryVO));
        pageInfo.setTotal(caseService.count(queryVO));
        return Result.success(pageInfo);
    }

    @RequestMapping(value = "/queryDetail", method = RequestMethod.GET)
    public Result<AutoCaseVO> queryDetail(@RequestParam("caseId") Integer caseId) {
        log.info("--->开始查询用例详情：caseId={}", caseId);
        return Result.success(caseService.queryDetail(caseId));
    }

//    @RequestMapping(value = "/use", method = RequestMethod.POST)
//    public Result<Boolean> use(@RequestBody String autoCase) {
//        log.info("--->开始执行用例：{}", autoCase);
//        try {
//            AutoCaseVO autoCaseVO = JSON.parseObject(autoCase, AutoCaseVO.class);
//            return Result.message(caseService.useAsync(autoCaseVO), "执行异常，请检查步骤");
//        } catch (RejectedExecutionException e) {
//            return Result.errorMessage("执行队列已满，请稍后再试");
//        }
//    }

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
