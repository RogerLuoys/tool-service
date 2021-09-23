package com.luoys.upgrade.toolservice.web;

import com.luoys.upgrade.toolservice.common.Result;
import com.luoys.upgrade.toolservice.service.UserService;
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
    public Result<UserVO> login(@RequestParam("loginName") String loginName, @RequestParam("passWord") String passWord){
        log.info("--->用户登录开始：loginName={}, passWord={}", loginName, passWord);
        return Result.success(userService.queryByLoginName(loginName, passWord));
    }

    @RequestMapping(value = "/queryDetail", method = RequestMethod.GET)
    public Result<UserVO> queryDetail(@RequestHeader("userId") String userId) {
        log.info("--->查询用户信息开始：userId={}", userId);
        return Result.success(userService.queryByUserId(userId));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result<Integer> register(@RequestBody UserVO userVO){
        log.info("--->注册用户开始：{}", userVO);
        if (userService.checkUserExist(userVO.getLoginName())) {
            return Result.error("登录名已存在");
        }
        return Result.success(userService.newUser(userVO));
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<Boolean> update(@RequestBody UserVO userVO){
        log.info("--->修改用户信息开始：{}", userVO);
        return Result.success(userService.update(userVO));
    }
}
