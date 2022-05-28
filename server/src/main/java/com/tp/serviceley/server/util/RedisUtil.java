package com.tp.serviceley.server.util;

import com.tp.serviceley.server.model.redis.RedisSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Autowired
    public RedisTemplate redisTemplate;

    public static RedisUtil redisUtil;

    /**
     * This method is loaded only once when Spring starts
     */
    @PostConstruct
    public void init(){
        redisUtil=this;
        redisUtil.redisTemplate=this.redisTemplate;
    }

    /**
     * Save data and set cache time
     */
    public void set(String key, RedisSession value, long time){
        redisUtil.redisTemplate.opsForValue().set(key, value, time, TimeUnit.MILLISECONDS);
    }


    /**
     * Save data
     */
    public void set(String key,RedisSession value){
        redisUtil.redisTemplate.opsForValue().set(key, value);
    }

    /**
     * Save data
     */
    public RedisSession get(String key){
        return (RedisSession) redisUtil.redisTemplate.opsForValue().get(key);
    }

    /**
     * Get expiration time according to key
     */
    public Long getExpire(String key){
        return redisUtil.redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }

    /**
     * Determine whether the key exists
     */
    public Boolean hasKey(String key){
        return redisUtil.redisTemplate.hasKey(key);
    }

    /**
     * Set the expiration time according to the key
     */
    public Boolean expire(String key,long time){
        return redisUtil.redisTemplate.expire(key,time , TimeUnit.SECONDS);
    }
}