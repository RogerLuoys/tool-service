package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.DevicePO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceMapper {
    int deleteByPrimaryKey(Integer id);

    //todo 映射xml文件失败
    @Select("SELECT count(1) FROM device")
    int insert(DevicePO devicePO);

    int insertSelective(DevicePO record);

    DevicePO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DevicePO record);

    int updateByPrimaryKey(DevicePO record);
}