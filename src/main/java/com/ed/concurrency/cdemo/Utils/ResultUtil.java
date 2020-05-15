package com.ed.concurrency.cdemo.Utils;

import com.ed.concurrency.cdemo.result.CodeMsg;
import com.ed.concurrency.cdemo.result.Result;

public class ResultUtil {

    //成功获取时返回
    public static <T> Result<T> success(CodeMsg codeMsg, T data){
        return new Result<>(codeMsg, data);
    }
    //失败时返回特殊错误信息
    public static <T> Result<T> error(CodeMsg codeMsg){
        return new Result<>(codeMsg);
    }

}
