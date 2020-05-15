package com.ed.concurrency.cdemo.mapper;

import com.ed.concurrency.cdemo.bean.User;

public interface UserMapper {

    public User getUserByMobile(String mobile);
}
