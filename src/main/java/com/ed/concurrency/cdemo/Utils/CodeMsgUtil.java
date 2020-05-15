package com.ed.concurrency.cdemo.Utils;

import com.ed.concurrency.cdemo.result.CodeMsg;

public class CodeMsgUtil {
    public static final CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static final CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    public static final CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500214, "手机号不存在");
    public static final CodeMsg SEC_NOT_EXIST = new CodeMsg(500211, "该秒杀商品不存在");
    public static final CodeMsg USER_NOT_LOGIN = new CodeMsg(500210, "用户没有登录");
    public static final CodeMsg PASSWORD_ERROR = new CodeMsg(500215, "密码错误");
    public static final CodeMsg QUERY_ERROR = new CodeMsg(500213, "查询值为空");
    public static final CodeMsg GOODS_NOT_FOUND = new CodeMsg(500212, "查询的商品不存在");
    public static final CodeMsg SEC_SOLD_OUT = new CodeMsg(500222, "秒杀的商品已经售完");
    public static final CodeMsg SEC_FAILED = new CodeMsg(500223, "秒杀失败，服务器错误");
    public static final CodeMsg ALREADY_HAVE_ORDER = new CodeMsg(500233, "已经秒杀，请勿重复下单");
}
