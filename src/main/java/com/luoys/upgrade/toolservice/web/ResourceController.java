package com.luoys.upgrade.toolservice.web;

import com.luoys.upgrade.toolservice.service.common.CacheUtil;
import com.luoys.upgrade.toolservice.service.common.Result;
import com.luoys.upgrade.toolservice.service.ResourceService;
import com.luoys.upgrade.toolservice.web.vo.QueryVO;
import com.luoys.upgrade.toolservice.web.vo.ResourceVO;
import com.luoys.upgrade.toolservice.web.vo.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Result<Integer> create(@RequestHeader("loginInfo") String loginInfo,
                                 @RequestHeader(value = "projectId") Integer projectId,
                                 @RequestBody ResourceVO resourceVO) {
        resourceVO.setProjectId(projectId);
        log.info("--->开始新增资源：{}", resourceVO);
        resourceVO.setOwnerId(CacheUtil.getUserByLoginInfo(loginInfo).getUserId());
        return Result.message(resourceService.create(resourceVO));
    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public Result<Integer> remove(@RequestParam("resourceId") Integer resourceId) {
        log.info("--->开始删除资源：{}", resourceId);
        return Result.message(resourceService.remove(resourceId));
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<Integer> update(@RequestBody ResourceVO resourceVO) {
        log.info("--->开始更新资源：{}", resourceVO);
        return Result.message(resourceService.update(resourceVO));
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public Result<PageInfo<ResourceVO>> query(@RequestHeader("loginInfo") String loginInfo,
                                              @RequestHeader("projectId") Integer projectId,
                                              @RequestBody QueryVO queryVO) {
        queryVO.setProjectId(projectId);
        log.info("--->开始查询资源列表：{}", queryVO);
        Integer userId = CacheUtil.getUserByLoginInfo(loginInfo).getUserId();
        queryVO.setUserId(userId);
        PageInfo<ResourceVO> pageInfo = new PageInfo<>();
        pageInfo.setList(resourceService.query(queryVO));
        pageInfo.setTotal(resourceService.count(queryVO));
        return Result.success(pageInfo);
    }

    @RequestMapping(value = "/queryDetail", method = RequestMethod.GET)
    public Result<ResourceVO> queryDetail(@RequestParam("resourceId") Integer resourceId) {
        log.info("--->开始查询资源详情：resourceId={}", resourceId);
        return Result.success(resourceService.queryDetail(resourceId));
    }

}
