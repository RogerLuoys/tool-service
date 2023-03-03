package com.luoys.upgrade.toolservice.web;

import com.alibaba.fastjson.JSON;
import com.luoys.upgrade.toolservice.service.CaseService;
import com.luoys.upgrade.toolservice.service.common.CacheUtil;
import com.luoys.upgrade.toolservice.service.common.Result;
import com.luoys.upgrade.toolservice.web.vo.AutoCaseSimpleVO;
import com.luoys.upgrade.toolservice.web.vo.CaseModuleStatVO;
import com.luoys.upgrade.toolservice.web.vo.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/stat")
public class StatController {

    @Autowired
    private CaseService caseService;

    @RequestMapping(value = "/totalCase", method = RequestMethod.GET)
    public Result<List<CaseModuleStatVO>> totalCase(@RequestHeader("projectId") Integer projectId) {
//        List<CaseModuleStatVO> stat = new ArrayList<>();
//        CaseModuleStatVO caseModuleStatVO = new CaseModuleStatVO();
//        caseModuleStatVO.setTotalCase(100);
//        caseModuleStatVO.setNewCase(10);
//        caseModuleStatVO.setName("测试模块一二三");
//        stat.add(caseModuleStatVO);
        log.info("--->查询用例统计数据开始：projectId={}", projectId);
        return Result.success(caseService.totalCase(projectId));
    }

}
