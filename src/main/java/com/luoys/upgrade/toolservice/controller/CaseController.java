package com.luoys.upgrade.toolservice.controller;

import com.luoys.upgrade.toolservice.common.Result;
import com.luoys.upgrade.toolservice.controller.transform.TransformCase;
import com.luoys.upgrade.toolservice.controller.vo.*;
import com.luoys.upgrade.toolservice.dao.CaseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/case")
public class CaseController {

    @Autowired
    private CaseMapper caseMapper;


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Result<String> create(@RequestHeader("userId") String userId, @RequestHeader("userName") String userName, @RequestBody CaseVO caseVO) {
        caseVO.setOwnerId(userId);
        caseVO.setOwnerName(userName);
        log.info("---》开始新增用例：{}", caseVO);
        int result = caseMapper.insert(TransformCase.transformVO2PO(caseVO));
        return result == 1 ? Result.success("创建成功") : Result.error("创建失败");
    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public Result<String> remove(@RequestParam("caseId") int caseId) {
        log.info("---》开始删除用例：{}", caseId);
        int result = caseMapper.delete(caseId);
        return result == 1 ? Result.success("删除成功") : Result.error("删除失败");
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result<String> update(@RequestBody CaseVO caseVO) {
        log.info("---》开始更新用例：{}", caseVO);
        int result = caseMapper.update(TransformCase.transformVO2PO(caseVO));
        return result == 1 ? Result.success("更新成功") : Result.error("更新失败");
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Result<PageInfo<CaseSimpleVO>> query(@RequestParam(value = "isOnlyOwner", required = false) Boolean isOnlyOwner,
                                                @RequestParam(value = "name", required = false) String name,
                                                @RequestHeader("userId") String userId,
                                                @RequestParam("pageIndex") Integer pageIndex) {
        log.info("---》开始查询用例列表：");
        if (!isOnlyOwner) {
            userId = null;
        }
        PageInfo<CaseSimpleVO> pageInfo = new PageInfo<>();
        pageInfo.setList(TransformCase.transformPO2SimpleVO(caseMapper.list(name, userId, pageIndex-1)));
        pageInfo.setTotal(pageInfo.getList().size());
        return Result.success(pageInfo);
    }

    @RequestMapping(value = "/queryDetail", method = RequestMethod.GET)
    public Result<CaseVO> queryDetail(@RequestParam("caseId") Integer caseId) {
        log.info("---》开始查询用例详情：caseId={}", caseId);
        return Result.success(TransformCase.transformPO2VO(caseMapper.selectById(caseId)));
    }

}
