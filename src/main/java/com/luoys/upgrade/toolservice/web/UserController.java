package com.luoys.upgrade.toolservice.web;

import com.luoys.upgrade.toolservice.common.Result;
import com.luoys.upgrade.toolservice.service.UserService;
import com.luoys.upgrade.toolservice.web.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Result<UserVO> login(@RequestParam("loginName") String loginName, @RequestParam("password") String password){
        log.info("====》用户登录开始：loginName={}, password={}", loginName, password);
        return Result.success(userService.queryByLoginName(loginName, password));
    }

    @RequestMapping(value = "/queryByUserId", method = RequestMethod.GET)
    public Result<UserVO> queryByUserId(@RequestParam("userId") String userId) {
        log.info("====》查询用户信息开始：userId={}", userId);
        return Result.success(userService.queryByUserId(userId));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result<Integer> register(@RequestBody UserVO userBO){
        log.info("====》注册用户开始：{}", userBO);
        return Result.success(userService.newUser(userBO));
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<Boolean> update(@RequestBody UserVO userBO){
        log.info("====》修改用户信息开始：{}", userBO);
        return Result.success(userService.update(userBO));
    }
}
