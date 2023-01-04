package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.ConfigPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 配置dao层接口
 *
 * @author luoys
 */
@Repository
public interface ConfigMapper {
    int remove(@Param("configId") Integer configId);

    int insert(ConfigPO record);


    ConfigPO select(@Param("configId") Integer configId);

    List<ConfigPO> list(@Param("caseId") Integer caseId);

    int update(ConfigPO record);

}