package com.ed.concurrency.cdemo.redisService;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {

    @Autowired
    JedisPool jedisPool;
    //标准的池化连接操作，从初始化好的资源池中取出资源操作，操作完之后返还给池

    public boolean set(String key, String value){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.set(key, value);
            return true;
        }finally {
            returnToPool(jedis);
        }
    }

    public String get(String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String s = jedis.get(key);
            if(s!=null && !"".equals(s)){
                return s;
            }
            return "";
        }finally {
            returnToPool(jedis);
        }
    }


    public Long del(String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.del(key);
        }finally {
            returnToPool(jedis);
        }
    }

    public Long incr(String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.incr(key);
        }finally {
            returnToPool(jedis);
        }
    }

    public String add(String key, Integer count){
        Jedis jedis = null;
        try{
            synchronized (RedisService.class){
                jedis = jedisPool.getResource();
                Integer c = Integer.valueOf(jedis.get(key));
                return jedis.set(key, ""+c+count);
            }
        }finally {
            returnToPool(jedis);
        }
    }

    public Long decr(String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            return jedis.decr(key);
        }finally {
            returnToPool(jedis);
        }
    }

    public boolean exists(String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            if(key!=null&&!"".equals(key)){
                return jedis.exists(key);
            }
            return false;
        }finally {
            returnToPool(jedis);
        }
    }

    public void flushAll(){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.flushAll();

        }finally {
            returnToPool(jedis);
        }
    }


    private void returnToPool(Jedis jedis) {
        if(jedis!=null) jedis.close();
    }


    public static <T> String beanToString(T value){
        if(value == null) return null;
        //获取T的字节码，所有未知类型的字节码都长这样
        Class<?> aClass = value.getClass();

        if(aClass==int.class || aClass==Integer.class || aClass==long.class || aClass==Long.class){
            return ""+value;
        }else if(aClass==String.class){
            return (String) value;
        }else{
            return JSON.toJSONString(value);
        }
    }

    public static <T> T stringToBean(String str, Class<T> clazz){
        if(str==null || str.length()<=0 || clazz==null){
            return null;
        }
        if(clazz==int.class || clazz==Integer.class){
            return (T)Integer.valueOf(str);
        }else if(clazz == long.class || clazz == Long.class){
            return (T)Long.valueOf(str);
        }else{
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }


}
