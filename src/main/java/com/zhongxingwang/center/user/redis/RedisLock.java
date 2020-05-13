package com.zhongxingwang.center.user.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class RedisLock {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 加锁
     * @param lockKey 加锁的Key
     * @param timeStamp 时间戳：当前时间+超时时间
     * @return
     */
    public boolean lock(String lockKey,String timeStamp){
        if(stringRedisTemplate.opsForValue().setIfAbsent(lockKey, timeStamp)){
            // 对应setnx命令，可以成功设置,也就是key不存在，获得锁成功
            return true;
        }
        // 判断锁超时 - 防止原来的操作异常，没有运行解锁操作 ，防止死锁
        String currentLock = stringRedisTemplate.opsForValue().get(lockKey);
        // 如果锁过期 currentLock不为空且小于当前时间
        if(!StringUtils.isEmpty(currentLock) && Long.parseLong(currentLock) < System.currentTimeMillis()){
            //如果lockKey对应的锁已经存在，获取上一次设置的时间戳之后并重置lockKey对应的锁的时间戳
            String preLock = stringRedisTemplate.opsForValue().getAndSet(lockKey, timeStamp);
            if(!StringUtils.isEmpty(preLock) && preLock.equals(currentLock)){
                return true;
            }
        }
        return false;
    }

    /**
     * 释放锁
     * @param lockKey
     * @param timeStamp
     */
    public void release(String lockKey,String timeStamp){
        try {
            String currentValue = stringRedisTemplate.opsForValue().get(lockKey);
            if(!StringUtils.isEmpty(currentValue) && currentValue.equals(timeStamp) ){
                // 删除锁状态
                stringRedisTemplate.opsForValue().getOperations().delete(lockKey);
            }
        } catch (Exception e) {
            System.out.println("解锁异常!");
        }
    }
}
