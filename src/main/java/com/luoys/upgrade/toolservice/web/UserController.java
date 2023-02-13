package com.luoys.upgrade.toolservice.web;

import com.luoys.upgrade.toolservice.service.common.Result;
import com.luoys.upgrade.toolservice.service.UserService;
import com.luoys.upgrade.toolservice.service.common.StringUtil;
import com.luoys.upgrade.toolservice.web.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Result<UserVO> login(@RequestHeader("loginInfo") String loginInfo,
                                @RequestParam(value = "username", required = false) String username,
                                @RequestParam(value = "passWord", required = false) String passWord){
        log.info("--->用户登录开始：loginInfo = {}, username={}, passWord={}", loginInfo, username, passWord);
        if (!StringUtil.isBlank(username)) {
            return Result.success(userService.queryByLoginName(username, passWord));
        } else if (!StringUtil.isBlank(loginInfo) && StringUtil.isBlank(username) && StringUtil.isBlank(passWord)) {
            return Result.success(userService.queryByLoginInfo(loginInfo));
        } else {
            return Result.errorMessage("登录账号信息异常");
        }
    }

    @RequestMapping(value = "/queryDetail", method = RequestMethod.GET)
    public Result<UserVO> queryDetail(@RequestHeader("loginInfo") String loginInfo) {
        log.info("--->查询用户信息开始：loginInfo={}", loginInfo);
        return Result.success(userService.queryByLoginInfo(loginInfo));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result<Integer> register(@RequestBody UserVO userVO){
        log.info("--->注册用户开始：{}", userVO);
        if (userService.checkUserExist(userVO.getUsername())) {
            return Result.error("登录名已存在");
        }
        return Result.success(userService.newUser(userVO));
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<Integer> update(@RequestBody UserVO userVO){
        log.info("--->修改用户信息开始：{}", userVO);
        return Result.success(userService.update(userVO));
    }
}
