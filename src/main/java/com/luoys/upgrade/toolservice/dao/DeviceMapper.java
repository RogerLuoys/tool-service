package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.DevicePO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceMapper {

    int insert(DevicePO devicePO);

    int delete(Integer id);

    int update(DevicePO record);

    List<DevicePO> list(@Param("type") Integer type,
                        @Param("title") String title,
                        @Param("ownerId") String ownerId,
                        @Param("startIndex") Integer startIndex);

    DevicePO selectById(Integer id);

}
