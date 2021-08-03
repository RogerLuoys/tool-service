package com.luoys.upgrade.toolservice.web;

import com.luoys.upgrade.toolservice.common.Result;
import com.luoys.upgrade.toolservice.service.CaseService;
import com.luoys.upgrade.toolservice.web.vo.PageInfo;
import com.luoys.upgrade.toolservice.web.vo.AutoCaseSimpleVO;
import com.luoys.upgrade.toolservice.web.vo.AutoCaseVO;
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

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public Result<Boolean> remove(@RequestParam("caseId") Integer caseId) {
        log.info("---》开始删除用例：{}", caseId);
        return Result.message(caseService.remove(caseId));
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<Boolean> update(@RequestBody AutoCaseVO autoCaseVO) {
        log.info("---》开始更新用例：{}", autoCaseVO);
        return Result.message(caseService.update(autoCaseVO));
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
    public Result<AutoCaseVO> queryDetail(@RequestParam("caseId") Integer caseId) {
        log.info("---》开始查询用例详情：caseId={}", caseId);
        return Result.success(caseService.queryDetail(caseId));
    }
}
