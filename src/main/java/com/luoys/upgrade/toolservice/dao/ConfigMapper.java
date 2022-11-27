package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.ConfigPO;
import org.apache.ibatis.annotations.Param;

public interface ConfigMapper {
    int remove(@Param("configId") Integer configId);

    int insert(ConfigPO record);


    ConfigPO select(@Param("configId") Integer configId);

    int update(ConfigPO record);

}