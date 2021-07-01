package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.DevicePO;

public interface DeviceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DevicePO record);

    int insertSelective(DevicePO record);

    DevicePO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DevicePO record);

    int updateByPrimaryKey(DevicePO record);
}