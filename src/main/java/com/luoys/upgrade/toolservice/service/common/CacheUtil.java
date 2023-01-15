package com.luoys.upgrade.toolservice.service.common;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.luoys.upgrade.toolservice.service.dto.DataSourceDTO;
import com.luoys.upgrade.toolservice.service.dto.JdbcDTO;
import com.luoys.upgrade.toolservice.web.vo.UserVO;

import java.util.concurrent.TimeUnit;

public class CacheUtil {

    private static Cache<Integer, DataSourceDTO> jdbcCache = Caffeine.newBuilder()
            //cache的初始容量
            .initialCapacity(5)
            //cache最大缓存数
            .maximumSize(10)
            //设置写缓存后n秒钟过期
            .expireAfterWrite(12, TimeUnit.HOURS)
            .build();

    public static DataSourceDTO getJdbcById(Integer key) {
        return jdbcCache.getIfPresent(key);
    }

}
