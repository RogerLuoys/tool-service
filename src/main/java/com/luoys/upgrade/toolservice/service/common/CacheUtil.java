package com.luoys.upgrade.toolservice.service.common;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.luoys.upgrade.toolservice.service.dto.JdbcDTO;

import java.util.concurrent.TimeUnit;

public class CacheUtil {

    private static Cache<Integer, JdbcDTO> jdbcCache = Caffeine.newBuilder()
            //cache的初始容量
            .initialCapacity(5)
            //cache最大缓存数
            .maximumSize(10)
            //设置写缓存后n秒钟过期
            .expireAfterWrite(12, TimeUnit.HOURS)
            //设置读写缓存后n秒钟过期,实际很少用到,类似于expireAfterWrite
            //.expireAfterAccess(17, TimeUnit.SECONDS)
            .build();

    public static JdbcDTO getJdbcById(Integer key) {
        return jdbcCache.get(key, CacheUtil::getValue);
    }

    private static JdbcDTO getValue(Integer key) {
        jdbcCache.put(key, null);
        return null;
    }

    public static void test() {
        //创建guava cache
        Cache<String, Object> cache = Caffeine.newBuilder()
                //cache的初始容量
                .initialCapacity(5)
                //cache最大缓存数
                .maximumSize(10)
                //设置写缓存后n秒钟过期
                .expireAfterWrite(12, TimeUnit.HOURS)
                //设置读写缓存后n秒钟过期,实际很少用到,类似于expireAfterWrite
                //.expireAfterAccess(17, TimeUnit.SECONDS)
                .build();
        String key = "key";
        // 往缓存写数据
        cache.put(key, "v");

        // 获取value的值，如果key不存在，获取value后再返回
        Object value = cache.get(key, CacheUtil::getValueFromDB);

        // 删除key
        cache.invalidate(key);
    }

    private static Object getValueFromDB(String key) {
        return "v";
    }


}
