package com.luoys.upgrade.toolservice.service.common;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.luoys.upgrade.toolservice.dao.AutoStepMapper;
import com.luoys.upgrade.toolservice.dao.ResourceMapper;
import com.luoys.upgrade.toolservice.dao.UserMapper;
import com.luoys.upgrade.toolservice.dao.po.ResourcePO;
import com.luoys.upgrade.toolservice.dao.po.UserPO;
import com.luoys.upgrade.toolservice.service.CaseService;
import com.luoys.upgrade.toolservice.service.dto.DataSourceDTO;
import com.luoys.upgrade.toolservice.service.dto.StepDTO;
import com.luoys.upgrade.toolservice.service.enums.DefaultEnum;
import com.luoys.upgrade.toolservice.service.enums.UserTypeEnum;
import com.luoys.upgrade.toolservice.service.transform.TransformAutoStep;
import com.luoys.upgrade.toolservice.service.transform.TransformResource;
import com.luoys.upgrade.toolservice.service.transform.TransformUser;
import com.luoys.upgrade.toolservice.web.vo.AutoCaseVO;
import com.luoys.upgrade.toolservice.web.vo.AutoStepVO;
import com.luoys.upgrade.toolservice.web.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class CacheUtil {

    // 静态变量需要延时注入
    private static ResourceMapper resourceMapper;

    private static AutoStepMapper autoStepMapper;

    private static CaseService caseService;

    private static UserMapper userMapper;

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

    private static final Cache<String, UserVO> userCache = Caffeine.newBuilder()
            //cache的初始容量
            .initialCapacity(10)
            //cache最大缓存数
            .maximumSize(1000)
            //设置写缓存后n秒钟过期
            .expireAfterWrite(12, TimeUnit.HOURS)
            .build();

    private static final Cache<Integer, String> nickNameCache = Caffeine.newBuilder()
            //cache的初始容量
            .initialCapacity(10)
            //cache最大缓存数
            .maximumSize(1000)
            //设置写缓存后n秒钟过期
            .expireAfterWrite(12, TimeUnit.HOURS)
            .build();

    @Autowired
    private void setResourceMapper(ResourceMapper resourceMapper) {
        CacheUtil.resourceMapper = resourceMapper;
    }

    @Autowired
    private void setAutoStepMapper(AutoStepMapper autoStepMapper) {
        CacheUtil.autoStepMapper = autoStepMapper;
    }

    @Autowired
    private void setCaseService(CaseService caseService) {
        CacheUtil.caseService = caseService;
    }

    @Autowired
    private void setUserMapper(UserMapper userMapper) {
        CacheUtil.userMapper = userMapper;
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

    public static String getNickNameById(Integer key) {
        return nickNameCache.get(key, CacheUtil::getNickNameFromDB);
    }

    public static UserVO getUserByLoginInfo(String key) {
        UserVO userVO = userCache.get(key, CacheUtil::getUserByInfoFromDB);
        if (userVO != null) {
            nickNameCache.put(userVO.getUserId(), userVO.getNickname());
        }
        return userVO;
    }

    private static DataSourceDTO getJdbcFromDB(Integer key) {
        ResourcePO resourcePO = resourceMapper.selectByID(key);
        return TransformResource.transformPO2DTO(resourcePO);
    }

    private static List<StepDTO> getPoFromDB(Integer key) {
        List<AutoStepVO> autoStepVOList = TransformAutoStep.transformPO2VO(autoStepMapper.listByCaseId(key));
        return TransformAutoStep.transformVO2DTO(autoStepVOList);
    }

    private static AutoCaseVO getSupperClassFromDB(Integer key) {
        return caseService.queryDetail(key);
    }

    private static UserVO getUserByInfoFromDB(String key) {
        if (key.equals(DefaultEnum.DEFAULT_DEMO_USER.getDescription())) {
            UserVO userVO = new UserVO();
            userVO.setUserId(DefaultEnum.DEFAULT_DEMO_USER.getCode());
            userVO.setLoginInfo(DefaultEnum.DEFAULT_DEMO_USER.getDescription());
            userVO.setType(UserTypeEnum.REGULAR.getCode());
            userVO.setNickname(DefaultEnum.DEFAULT_DEMO_USER.getValue());
            return userVO;
        }
        return TransformUser.transformPO2VO(userMapper.selectByLoginInfo(key));
    }

    private static String getNickNameFromDB(Integer key) {
        UserPO userPO = userMapper.selectById(key);
        if (userPO == null) {
            return DefaultEnum.DEFAULT_UNKNOWN_USER.getValue();
        }
        return userPO.getNickname();
    }

}
