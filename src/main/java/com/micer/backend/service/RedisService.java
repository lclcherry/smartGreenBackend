package com.micer.backend.service;

import java.util.Map;

public interface RedisService {
    /**
     * 指定缓存失效时间,以秒为单位
     *
     * @param key
     * @param time
     * @return
     */
    public boolean expire(String key, long time);

    /**
     * 将 key，value 存放到redis数据库中，默认设置过期时间为一周
     *
     * @param key
     * @param value
     */
    public boolean setDefault(String key, Object value);

    /**
     * 根据key获取过期时间
     * @param key 不能为null
     * @return
     */
    public long getExpire(String key);

    /**
     * 将 key，value 存放到redis数据库中，设置过期时间单位是秒
     *
     * @param key
     * @param value
     * @param expireTime
     */
    public boolean setExpireTime(String key, Object value, long expireTime);

    /**
     * 将 key，value 以hash的形式存放到redis数据库中，设置过期时间单位是秒
     *
     * @param key
     * @param hk
     * @param hv
     * @param time
     */
    public boolean hSet(String key, String hk, Object hv, long time);

    /**
     * 获取value值
     * @param key
     * @param hk
     * @return
     */
    public Object hGet(String key, String hk);

    /**
     * 获取所有键值对
     * @param key
     * @return
     */
    public Object hGetAll(String key);

    /**
     * 判断 key 是否在 redis 数据库中
     *
     * @param key
     * @return
     */
    public boolean exists(final String key);

    /**
     * 获取与 key 对应的对象
     * @param key
     * @param clazz 目标对象类型
     * @param <T>
     * @return
     */
    public <T> T get(String key, Class<T> clazz);

    /**
     * 获取 key 对应的字符串
     * @param key
     * @return
     */
    public String get(String key);

    /**
     * 删除 key 对应的 value
     * @param key
     */
    public boolean delete(String key);
}
