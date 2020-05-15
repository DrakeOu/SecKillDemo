package com.ed.concurrency.cdemo.rabbitMQ;

import com.ed.concurrency.cdemo.bean.User;

public class SecKillMessage {
    private User user;
    private String secId;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }
}
