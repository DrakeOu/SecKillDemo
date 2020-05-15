package com.ed.concurrency.cdemo.service.serviceImpl;

import com.ed.concurrency.cdemo.Utils.CodeMsgUtil;
import com.ed.concurrency.cdemo.Utils.MD5Util;
import com.ed.concurrency.cdemo.Utils.ResultUtil;
import com.ed.concurrency.cdemo.bean.User;
import com.ed.concurrency.cdemo.mapper.UserMapper;
import com.ed.concurrency.cdemo.redisService.RedisService;
import com.ed.concurrency.cdemo.result.Result;
import com.ed.concurrency.cdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    RedisService redisService;

    @Autowired
    UserMapper userMapper;

    @Override
    public Result login(HttpServletResponse response, User user) {
        //user中包含mobile和密码
        //服务器错误
        if(user == null){
            return ResultUtil.error(CodeMsgUtil.SERVER_ERROR);
        }
        //取出手机和密码
        String mobile = user.getMobile();
        String password = user.getPassword();
        //缓存中,和数据库中 查询是否存在账号
        User user1 = getById(mobile);
        if(user1==null){
            //用户在缓存和数据库中均不存在
            return ResultUtil.error(CodeMsgUtil.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPass = MD5Util.formPassToDBPass(password, user1.getSalt());
        if(!dbPass.equals(user1.getPassword())){
            return ResultUtil.error(CodeMsgUtil.PASSWORD_ERROR);
        }
        //生成cookie
        String token = UUID.randomUUID().toString();
        addCookie(response, token, user1);
        return ResultUtil.success(CodeMsgUtil.SUCCESS, user1);
    }

    @Override
    public User getById(String mobile) {
        //从redis中读用户应该用token
        //redis中token作为存根保存真正的用户，但是用户用mobile查询也应该可以得到数据
        //mobile查询用户，token用于验证登录
        String jsonUser = redisService.get(mobile);
        User user = RedisService.stringToBean(jsonUser, User.class);

        if(user!=null) return user;
        User user1 = userMapper.getUserByMobile(mobile);
        if(user1!=null) return user1;
        return null;
    }

    @Override
    public void addCookie(HttpServletResponse response, String token, User user) {
        //添加到缓存中
        String beanString = RedisService.beanToString(user);
        //这里mobile最好设置过期时间
        redisService.set(token, beanString);
        redisService.set(user.getMobile(), beanString);
        //添加到cookie中，仅添加用于保存状态的token
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(60*30);
        cookie.setPath("/");
        response.addCookie(cookie);

    }
}
