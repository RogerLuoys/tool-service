package com.luoys.upgrade.toolservice.web;

import com.luoys.upgrade.toolservice.service.common.Result;
import com.luoys.upgrade.toolservice.service.ResourceService;
import com.luoys.upgrade.toolservice.web.vo.ResourceSimpleVO;
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
    public Result<String> create(@RequestHeader("userId") Integer userId,
                                 @RequestHeader(value = "projectId") Integer projectId,
                                 @RequestBody ResourceVO resourceVO) {
        resourceVO.setOwnerId(userId);
        resourceVO.setProjectId(projectId);
        log.info("--->开始新增资源：{}", resourceVO);
        return Result.message(resourceService.create(resourceVO));
    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public Result<String> remove(@RequestParam("resourceId") Integer resourceId) {
        log.info("--->开始删除资源：{}", resourceId);
        return Result.message(resourceService.remove(resourceId));
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<String> update(@RequestBody ResourceVO resourceVO) {
        log.info("--->开始更新资源：{}", resourceVO);
        return Result.message(resourceService.update(resourceVO));
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Result<PageInfo<ResourceVO>> query(@RequestParam(value = "type", required = false) Integer type,
                                                    @RequestParam(value = "name", required = false) String name,
                                                    @RequestHeader("userId") Integer userId,
                                                    @RequestParam("pageIndex") Integer pageIndex) {
        log.info("--->开始查询资源列表：");
        PageInfo<ResourceVO> pageInfo = new PageInfo<>();
        pageInfo.setList(resourceService.query(type, name, userId, pageIndex));
        pageInfo.setTotal(resourceService.count(type, name, userId));
        return Result.success(pageInfo);
    }

    @RequestMapping(value = "/queryDetail", method = RequestMethod.GET)
    public Result<ResourceVO> queryDetail(@RequestParam("resourceId") Integer resourceId) {
        log.info("--->开始查询资源详情：resourceId={}", resourceId);
        return Result.success(resourceService.queryDetail(resourceId));
    }

}
