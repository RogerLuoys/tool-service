package com.luoys.upgrade.toolservice.web;


import com.luoys.upgrade.toolservice.common.Result;
import com.luoys.upgrade.toolservice.service.CaseService;
import com.luoys.upgrade.toolservice.service.SuiteService;
import com.luoys.upgrade.toolservice.web.vo.PageInfo;
import com.luoys.upgrade.toolservice.web.vo.TestSuiteSimpleVO;
import com.luoys.upgrade.toolservice.web.vo.TestSuiteVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/testSuite")
public class TestSuiteController {

    @Autowired
    private SuiteService suiteService;


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Result<Boolean> create(@RequestBody TestSuiteVO testSuiteVO) {
        log.info("---》开始新增：{}", testSuiteVO);
//        int result = testCaseMapper.insert(TransformTestCase.transformVO2PO(testSuiteVO));
//        return result == 1 ? Result.success("创建成功") : Result.error("创建失败");
        return Result.message(suiteService.create(testSuiteVO));
    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public Result<Boolean> remove(@RequestParam("testSuiteId") Integer testSuiteId) {
        log.info("---》开始删除用例：{}", testSuiteId);
//        int result = testCaseMapper.delete(testSuiteId);
//        return result == 1 ? Result.success("删除成功") : Result.error("删除失败");
        return Result.message(suiteService.remove(testSuiteId));
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<Boolean> update(@RequestBody TestSuiteVO testSuiteVO) {
        log.info("---》开始更新用例：{}", testSuiteVO);
//        int result = testCaseMapper.update(TransformTestCase.transformVO2PO(testSuiteVO));
//        return result == 1 ? Result.success("更新成功") : Result.error("更新失败");
        return Result.message(suiteService.update(testSuiteVO));
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Result<PageInfo<TestSuiteSimpleVO>> query(@RequestParam(value = "name", required = false) String name,
                                                     @RequestParam("pageIndex") Integer pageIndex) {
        log.info("---》开始查询用例列表：");
        PageInfo<TestSuiteSimpleVO> pageInfo = new PageInfo<>(suiteService.query(name, pageIndex));
//        pageInfo.setList(suiteService.query(userId, isOnlyOwner, status, name, pageIndex));
//        pageInfo.setTotal(pageInfo.getList().size());
        return Result.success(pageInfo);
    }

    @RequestMapping(value = "/queryDetail", method = RequestMethod.GET)
    public Result<TestSuiteVO> queryDetail(@RequestParam("testSuiteId") Integer testSuiteId) {
        log.info("---》开始查询用例1详情：testSuiteId={}", testSuiteId);
//        return Result.success(TransformTestCase.transformPO2VO(testCaseMapper.selectById(testSuiteId)));
        return Result.success(suiteService.queryDetail(testSuiteId));
    }

}
