package com.ed.concurrency.cdemo.redisService;

import redis.clients.jedis.JedisPool;

import java.io.*;
import java.net.Socket;

public class socketRedis {
    //几乎我们所使用的绝大部分service都是web service，redis，mysql等等，
    //
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 6379);
        System.out.println("Redis is connected!");
        //从系统读取
        BufferedReader out = new BufferedReader(new InputStreamReader(System.in));
        //向socket写入
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        while(true){
            String str = out.readLine();
            if("".equals(str)){
                break;
            }
            writer.println(str);
            writer.flush();
        }
        writer.close();
        socket.close();
    }

}
