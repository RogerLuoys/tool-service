package com.luoys.upgrade.toolservice.web;

import com.luoys.upgrade.toolservice.service.ProjectService;
import com.luoys.upgrade.toolservice.service.common.CacheUtil;
import com.luoys.upgrade.toolservice.service.common.Result;
import com.luoys.upgrade.toolservice.web.vo.PageInfo;
import com.luoys.upgrade.toolservice.web.vo.ProjectVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Result<Integer> create(@RequestHeader(value = "loginInfo") String loginInfo,
                                 @RequestBody ProjectVO projectVO) {
        projectVO.setOwnerId(CacheUtil.getUserByLoginInfo(loginInfo).getUserId());
        log.info("--->开始创建项目：{}", projectVO);
        return Result.message(projectService.create(projectVO));
    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public Result<Integer> remove(@RequestHeader(value = "projectId") Integer projectId) {
        log.info("--->开始移除项目：");
        return Result.message(projectService.remove(projectId));
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<Integer> update(@RequestBody ProjectVO projectVO) {
        log.info("--->开始更新项目：{}", projectVO);
        return Result.message(projectService.update(projectVO));
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Result<PageInfo<ProjectVO>> query(@RequestParam(value = "nickname", required = false) String nickname,
                                            @RequestParam("pageIndex") Integer pageIndex) {
        log.info("--->开始查询项目列表：");
        PageInfo<ProjectVO> pageInfo = new PageInfo<>();
        pageInfo.setList(projectService.query(nickname, pageIndex));
        pageInfo.setTotal(projectService.count(nickname));
        return Result.success(pageInfo);
    }

    @RequestMapping(value = "/queryByUser", method = RequestMethod.GET)
    public Result<List<ProjectVO>> queryByUser(@RequestHeader("loginInfo") String loginInfo) {
        log.info("--->开始查询用户所属的项目：");
        return Result.success(projectService.queryByUser(CacheUtil.getUserByLoginInfo(loginInfo).getUserId()));
    }

}
