package com.luoys.upgrade.toolservice.service.common;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.luoys.upgrade.toolservice.dao.AutoCaseMapper;
import com.luoys.upgrade.toolservice.dao.CaseStepRelationMapper;
import com.luoys.upgrade.toolservice.dao.ResourceMapper;
import com.luoys.upgrade.toolservice.dao.po.AutoCasePO;
import com.luoys.upgrade.toolservice.dao.po.ResourcePO;
import com.luoys.upgrade.toolservice.service.CaseService;
import com.luoys.upgrade.toolservice.service.dto.DataSourceDTO;
import com.luoys.upgrade.toolservice.service.dto.StepDTO;
import com.luoys.upgrade.toolservice.service.transform.TransformAutoCase;
import com.luoys.upgrade.toolservice.service.transform.TransformAutoStep;
import com.luoys.upgrade.toolservice.service.transform.TransformCaseStepRelation;
import com.luoys.upgrade.toolservice.service.transform.TransformResource;
import com.luoys.upgrade.toolservice.web.vo.AutoCaseVO;
import com.luoys.upgrade.toolservice.web.vo.CaseStepVO;
import org.apache.ibatis.annotations.Case;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class CacheUtil {

    private static ResourceMapper resourceMapper;

    private static CaseStepRelationMapper caseStepRelationMapper;

    private static CaseService caseService;


    private static final Cache<Integer, DataSourceDTO> dataSourceCache = Caffeine.newBuilder()
            //cache的初始容量
            .initialCapacity(5)
            //cache最大缓存数
            .maximumSize(100)
            //设置写缓存后n秒钟过期
            .expireAfterWrite(24, TimeUnit.HOURS)
            .build();

    private static final Cache<Integer, List<StepDTO>> poCache = Caffeine.newBuilder()
            //cache的初始容量
            .initialCapacity(5)
            //cache最大缓存数
            .maximumSize(100)
            //设置写缓存后n秒钟过期
            .expireAfterWrite(24, TimeUnit.HOURS)
            .build();

    private static final Cache<Integer, AutoCaseVO> supperClassCache = Caffeine.newBuilder()
            //cache的初始容量
            .initialCapacity(5)
            //cache最大缓存数
            .maximumSize(100)
            //设置写缓存后n秒钟过期
            .expireAfterWrite(24, TimeUnit.HOURS)
            .build();

    @Autowired
    private void setResourceMapper(ResourceMapper resourceMapper) {
        CacheUtil.resourceMapper = resourceMapper;
    }

    @Autowired
    private void setCaseStepRelationMapper(CaseStepRelationMapper caseStepRelationMapper) {
        CacheUtil.caseStepRelationMapper = caseStepRelationMapper;
    }

    @Autowired
    private void setCaseService(CaseService caseService) {
        CacheUtil.caseService = caseService;
    }

    public static DataSourceDTO getJdbcById(Integer key) {
        return dataSourceCache.get(key, CacheUtil::getJdbcFromDB);
    }

    public static List<StepDTO> getPoById(Integer key) {
        return poCache.get(key, CacheUtil::getPoFromDB);
    }

    public static AutoCaseVO getSupperClassById(Integer key) {
        return supperClassCache.get(key, CacheUtil::getSupperClassFromDB);
    }

    private static DataSourceDTO getJdbcFromDB(Integer key) {
        ResourcePO resourcePO = resourceMapper.selectByID(key);
        return TransformResource.transformPO2DTO(resourcePO);
    }

    private static List<StepDTO> getPoFromDB(Integer key) {
        List<CaseStepVO> caseStepVOList = TransformCaseStepRelation.transformPO2VO(caseStepRelationMapper.listStepByCaseId(key));
        return TransformCaseStepRelation.transformVO2DTO(caseStepVOList);
    }

    private static AutoCaseVO getSupperClassFromDB(Integer key) {
        return caseService.queryDetail(key);
    }

}
