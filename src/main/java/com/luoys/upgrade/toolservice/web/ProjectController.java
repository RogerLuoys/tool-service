package com.luoys.upgrade.toolservice.web;

import com.luoys.upgrade.toolservice.service.ProjectService;
import com.luoys.upgrade.toolservice.service.common.Result;
import com.luoys.upgrade.toolservice.web.vo.PageInfo;
import com.luoys.upgrade.toolservice.web.vo.ProjectVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Result<String> create(@RequestHeader(value = "userId") Integer userId,
                                 @RequestBody ProjectVO projectVO) {
        log.info("--->开始创建项目：{}", projectVO);
        return Result.message(projectService.create(projectVO));
    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public Result<String> remove(@RequestHeader(value = "projectId") Integer projectId) {
        log.info("--->开始移除项目：");
        return Result.message(projectService.remove(projectId));
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<String> update(@RequestBody ProjectVO projectVO) {
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

}
