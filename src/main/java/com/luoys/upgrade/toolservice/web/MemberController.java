package com.luoys.upgrade.toolservice.web;

import com.luoys.upgrade.toolservice.service.MemberService;
import com.luoys.upgrade.toolservice.service.common.Result;
import com.luoys.upgrade.toolservice.web.vo.MemberVO;
import com.luoys.upgrade.toolservice.web.vo.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;


    @RequestMapping(value = "/invite", method = RequestMethod.POST)
    public Result<String> create(@RequestHeader(value = "projectId") Integer projectId,
                                 @RequestBody MemberVO memberVO) {
        memberVO.setProjectId(projectId);
        log.info("--->开始邀请成员：{}", memberVO);
        return Result.message(memberService.invite(memberVO));
    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public Result<String> remove(@RequestHeader(value = "projectId") Integer projectId,
                                 @RequestParam(value = "userId") Integer userId) {
        log.info("--->开始移除成员：");
        return Result.message(memberService.remove(userId, projectId));
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<String> update(@RequestBody MemberVO memberVO) {
        log.info("--->开始更新成员：{}", memberVO);
        return Result.message(memberService.update(memberVO));
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Result<PageInfo<MemberVO>> query(@RequestParam(value = "nickname", required = false) String nickname,
                                              @RequestHeader("projectId") Integer projectId,
                                              @RequestParam("pageIndex") Integer pageIndex) {
        log.info("--->开始查询成员列表：");
        PageInfo<MemberVO> pageInfo = new PageInfo<>();
        pageInfo.setList(memberService.query(projectId, nickname, pageIndex));
        pageInfo.setTotal(memberService.count(projectId, nickname));
        return Result.success(pageInfo);
    }

}
