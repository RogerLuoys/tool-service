package com.luoys.upgrade.toolservice.controller;

import com.luoys.upgrade.toolservice.common.Result;
import com.luoys.upgrade.toolservice.controller.transform.TransformTestCase;
import com.luoys.upgrade.toolservice.controller.vo.*;
import com.luoys.upgrade.toolservice.dao.TestCaseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/testCase")
public class TestCaseController {

    @Autowired
    private TestCaseMapper testCaseMapper;


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Result<String> create(@RequestHeader("userId") String userId, @RequestHeader("userName") String userName, @RequestBody TestCaseVO testCaseVO) {
        testCaseVO.setOwnerId(userId);
        testCaseVO.setOwnerName(userName);
        log.info("---》开始新增用例：{}", testCaseVO);
        int result = testCaseMapper.insert(TransformTestCase.transformVO2PO(testCaseVO));
        return result == 1 ? Result.success("创建成功") : Result.error("创建失败");
    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public Result<String> remove(@RequestParam("testCaseId") int testCaseId) {
        log.info("---》开始删除用例：{}", testCaseId);
        int result = testCaseMapper.delete(testCaseId);
        return result == 1 ? Result.success("删除成功") : Result.error("删除失败");
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<String> update(@RequestBody TestCaseVO testCaseVO) {
        log.info("---》开始更新用例：{}", testCaseVO);
        int result = testCaseMapper.update(TransformTestCase.transformVO2PO(testCaseVO));
        return result == 1 ? Result.success("更新成功") : Result.error("更新失败");
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Result<PageInfo<TestCaseSimpleVO>> query(@RequestHeader("userId") String userId,
                                                    @RequestParam(value = "isOnlyOwner", required = false) Boolean isOnlyOwner,
                                                    @RequestParam(value = "status", required = false) Integer status,
                                                    @RequestParam(value = "name", required = false) String name,
                                                    @RequestParam("pageIndex") Integer pageIndex) {
        log.info("---》开始查询用例列表：");
        if (!isOnlyOwner) {
            userId = null;
        }
        PageInfo<TestCaseSimpleVO> pageInfo = new PageInfo<>();
        pageInfo.setList(TransformTestCase.transformPO2SimpleVO(testCaseMapper.list(status, name, pageIndex-1)));
        pageInfo.setTotal(pageInfo.getList().size());
        return Result.success(pageInfo);
    }

    @RequestMapping(value = "/queryDetail", method = RequestMethod.GET)
    public Result<TestCaseVO> queryDetail(@RequestParam("testCaseId") Integer testCaseId) {
        log.info("---》开始查询用例详情：testCaseId={}", testCaseId);
        return Result.success(TransformTestCase.transformPO2VO(testCaseMapper.selectById(testCaseId)));
    }

}
