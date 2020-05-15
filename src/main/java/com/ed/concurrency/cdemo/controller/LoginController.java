package com.ed.concurrency.cdemo.controller;

import com.ed.concurrency.cdemo.Utils.CodeMsgUtil;
import com.ed.concurrency.cdemo.Utils.ResultUtil;
import com.ed.concurrency.cdemo.bean.User;
import com.ed.concurrency.cdemo.redisService.RedisService;
import com.ed.concurrency.cdemo.result.Result;
import com.ed.concurrency.cdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class LoginController {

    @Autowired
    UserService userService;


    @RequestMapping("/login")
    @ResponseBody
    public Result UserLogin(@RequestBody User user, HttpServletResponse response){
        return userService.login(response, user);
    }

}
