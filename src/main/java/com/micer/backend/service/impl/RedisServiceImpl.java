package com.micer.backend.service.impl;

import com.micer.backend.service.RedisService;
import com.micer.backend.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService{

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 一周有多少秒
     */
    private static final long WEEK_SECONDS = 7 * 24 * 60 * 60;

    @Override
    public boolean expire(String key, long time) {
        try {
            redisTemplate.expire(key,time,TimeUnit.SECONDS);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public long getExpire(String key) {
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }

    @Override
    public boolean setDefault(String key, Object value) {
        try{
            redisTemplate.opsForValue().set(key, JsonUtil.convertObj2Json(value), WEEK_SECONDS, TimeUnit.SECONDS);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean setExpireTime(String key, Object value, long expireTime) {
        try{
            redisTemplate.opsForValue().set(key, JsonUtil.convertObj2Json(value), expireTime, TimeUnit.SECONDS);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean hSet(String key, String hk, Object hv, long time) {
        try{
            redisTemplate.opsForHash().put(key,hk,JsonUtil.convertObj2Json(hv));
            if(time > 0) {
                expire(key,time);
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Object hGet(String key, String hk) {
        return redisTemplate.opsForHash().get(key,hk);
    }

    @Override
    public Object hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    @Override
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        String s = get(key);
        if (s == null) {
            return null;
        }
        return JsonUtil.convertJson2Obj(s, clazz);
    }

    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public boolean delete(String key) {
        try{
            redisTemplate.delete(key);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}