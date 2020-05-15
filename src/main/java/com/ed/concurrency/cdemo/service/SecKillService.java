package com.ed.concurrency.cdemo.service;

import com.ed.concurrency.cdemo.bean.SecKillGoods;
import com.ed.concurrency.cdemo.bean.User;
import com.ed.concurrency.cdemo.result.Result;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SecKillService {

    public void SecKillGoods(User user, String secId);

    public Result SecKillDetail(String secId, HttpServletRequest request);

    public User getLoginUser(HttpServletRequest request);

    public List<SecKillGoods> queryAllGoods();

    public boolean checkSeckOrder(User user);
}
