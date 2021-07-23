package com.luoys.upgrade.toolservice.dao;

import com.luoys.upgrade.toolservice.dao.po.CasePO;
import com.luoys.upgrade.toolservice.dao.po.DevicePO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaseMapper {
    int delete(Integer id);

    int insert(CasePO casePO);


    CasePO selectById(Integer id);

    List<CasePO> list(@Param("title") String title,
                        @Param("ownerId") String ownerId,
                        @Param("startIndex") Integer startIndex);

    int update(CasePO casePO);

}
