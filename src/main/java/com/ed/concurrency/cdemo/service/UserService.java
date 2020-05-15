package com.ed.concurrency.cdemo.service;

import com.ed.concurrency.cdemo.bean.User;
import com.ed.concurrency.cdemo.result.Result;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

public interface UserService {

    public Result login(HttpServletResponse response, User user);

    public User getById(String mobile);

    public void addCookie(HttpServletResponse response, String token, User user);
}
