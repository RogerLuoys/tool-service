package com.luoys.upgrade.toolservice.service.common;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.luoys.upgrade.toolservice.dao.ResourceMapper;
import com.luoys.upgrade.toolservice.dao.po.ResourcePO;
import com.luoys.upgrade.toolservice.service.dto.DataSourceDTO;
import com.luoys.upgrade.toolservice.service.dto.StepDTO;
import com.luoys.upgrade.toolservice.service.transform.TransformResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class CacheUtil {

    private static ResourceMapper resourceMapper;


    private static final Cache<Integer, DataSourceDTO> dataSourceCache = Caffeine.newBuilder()
            //cache的初始容量
            .initialCapacity(5)
            //cache最大缓存数
            .maximumSize(10)
            //设置写缓存后n秒钟过期
            .expireAfterWrite(24, TimeUnit.HOURS)
            .build();

    private static final Cache<Integer, List<StepDTO>> poCache = Caffeine.newBuilder()
            //cache的初始容量
            .initialCapacity(5)
            //cache最大缓存数
            .maximumSize(10)
            //设置写缓存后n秒钟过期
            .expireAfterWrite(24, TimeUnit.HOURS)
            .build();

    @Autowired
    private void setResourceMapper(ResourceMapper resourceMapper) {
        CacheUtil.resourceMapper = resourceMapper;
    }

    public static DataSourceDTO getJdbcById(Integer key) {
        return dataSourceCache.get(key, CacheUtil::getJdbcFromDB);
    }

    public static List<StepDTO> getPoById(Integer key) {
        return poCache.get(key, CacheUtil::getPoFromDB);
    }

    private static DataSourceDTO getJdbcFromDB(Integer key) {
        ResourcePO resourcePO = resourceMapper.selectByID(key);
        return TransformResource.transformPO2DTO(resourcePO);
    }

    private static List<StepDTO> getPoFromDB(Integer key) {
        // todo ?
        return null;
    }

}
