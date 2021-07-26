package com.luoys.upgrade.toolservice.web;

import com.luoys.upgrade.toolservice.common.Result;
import com.luoys.upgrade.toolservice.service.CaseService;
import com.luoys.upgrade.toolservice.web.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/testCase")
public class TestCaseController {

    @Autowired
    private CaseService caseService;


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Result<Boolean> create(@RequestHeader("userId") String userId, @RequestHeader("userName") String userName, @RequestBody TestCaseVO testCaseVO) {
        testCaseVO.setOwnerId(userId);
        testCaseVO.setOwnerName(userName);
        log.info("---》开始新增用例：{}", testCaseVO);
//        int result = testCaseMapper.insert(TransformTestCase.transformVO2PO(testCaseVO));
//        return result == 1 ? Result.success("创建成功") : Result.error("创建失败");
        return Result.ifSuccess(caseService.create(testCaseVO));
    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public Result<Boolean> remove(@RequestParam("testCaseId") Integer testCaseId) {
        log.info("---》开始删除用例：{}", testCaseId);
//        int result = testCaseMapper.delete(testCaseId);
//        return result == 1 ? Result.success("删除成功") : Result.error("删除失败");
        return Result.ifSuccess(caseService.remove(testCaseId));
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<Boolean> update(@RequestBody TestCaseVO testCaseVO) {
        log.info("---》开始更新用例：{}", testCaseVO);
//        int result = testCaseMapper.update(TransformTestCase.transformVO2PO(testCaseVO));
//        return result == 1 ? Result.success("更新成功") : Result.error("更新失败");
        return Result.ifSuccess(caseService.update(testCaseVO));
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Result<PageInfo<TestCaseSimpleVO>> query(@RequestHeader("userId") String userId,
                                                    @RequestParam(value = "isOnlyOwner", required = false) Boolean isOnlyOwner,
                                                    @RequestParam(value = "status", required = false) Integer status,
                                                    @RequestParam(value = "name", required = false) String name,
                                                    @RequestParam("pageIndex") Integer pageIndex) {
        log.info("---》开始查询用例列表：");
        PageInfo<TestCaseSimpleVO> pageInfo = new PageInfo<>(caseService.query(userId, isOnlyOwner, status, name, pageIndex));
//        pageInfo.setList(caseService.query(userId, isOnlyOwner, status, name, pageIndex));
//        pageInfo.setTotal(pageInfo.getList().size());
        return Result.success(pageInfo);
    }

    @RequestMapping(value = "/queryDetail", method = RequestMethod.GET)
    public Result<TestCaseVO> queryDetail(@RequestParam("testCaseId") Integer testCaseId) {
        log.info("---》开始查询用例详情：testCaseId={}", testCaseId);
//        return Result.success(TransformTestCase.transformPO2VO(testCaseMapper.selectById(testCaseId)));
        return Result.success(caseService.queryDetail(testCaseId));
    }

}
