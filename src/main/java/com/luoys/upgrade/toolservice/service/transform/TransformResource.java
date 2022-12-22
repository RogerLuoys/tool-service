package com.luoys.upgrade.toolservice.service.transform;

import com.luoys.upgrade.toolservice.dao.po.ResourcePO;
import com.luoys.upgrade.toolservice.service.dto.DataSourceDTO;
import com.luoys.upgrade.toolservice.service.dto.DeviceDTO;
import com.luoys.upgrade.toolservice.service.dto.SlaveDTO;
import com.luoys.upgrade.toolservice.service.enums.ResourceTypeEnum;
import com.luoys.upgrade.toolservice.web.vo.ResourceSimpleVO;
import com.luoys.upgrade.toolservice.web.vo.ResourceVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 资源数据转换
 *
 * @author luoys
 */
public class TransformResource {

    public static ResourceSimpleVO transformPO2SimpleVO(ResourcePO po) {
        if (po == null) {
            return null;
        }
        ResourceSimpleVO vo = new ResourceSimpleVO();
        vo.setDescription(po.getDescription());
        vo.setOwnerId(po.getOwnerId());
        vo.setPermission(po.getPermission());
        vo.setName(po.getName());
        vo.setType(po.getType());
        vo.setResourceId(po.getId());
        vo.setOwnerName(po.getOwnerName());
//        vo.setUserId(po.getUserId());
//        vo.setUserName(po.getUserName());
        return vo;
    }

    public static List<ResourceSimpleVO> transformPO2SimpleVO(List<ResourcePO> poList) {
        List<ResourceSimpleVO> voList = new ArrayList<>();
        for (ResourcePO po : poList) {
            voList.add(transformPO2SimpleVO(po));
        }
        return voList;
    }

    public static ResourceVO transformPO2VO(ResourcePO po) {
        if (po == null) {
            return null;
        }
        //转换基本信息
        ResourceVO vo = new ResourceVO();
        vo.setDescription(po.getDescription());
        vo.setType(po.getType());
        vo.setResourceId(po.getId());
        vo.setName(po.getName());
        vo.setPermission(po.getPermission());
        vo.setOwnerId(po.getOwnerId());
        vo.setOwnerName(po.getOwnerName());
//        vo.setUserId(po.getUserId());
//        vo.setUserName(po.getUserName());
        //根据类型转换不同的对象
        switch (ResourceTypeEnum.fromCode(po.getType())) {
            case DATA_SOURCE:
                DataSourceDTO dataSourceDTO = new DataSourceDTO();
                dataSourceDTO.setDriver(po.getJdbcDriver());
                dataSourceDTO.setUrl(po.getJdbcUrl());
                dataSourceDTO.setUsername(po.getJdbcUsername());
                dataSourceDTO.setPassword(po.getJdbcPassword());
                vo.setDataSource(dataSourceDTO);
                break;
            case SLAVE_SOURCE:
                SlaveDTO slaveDTO = new SlaveDTO();
                slaveDTO.setIp(po.getSlaveIp());
                slaveDTO.setPort(po.getSlavePort());
                slaveDTO.setThread(po.getSlaveThread());
                vo.setSlave(slaveDTO);
                break;

        }
        return vo;
    }

    public static List<ResourceVO> transformPO2VO(List<ResourcePO> poList) {
        List<ResourceVO> voList = new ArrayList<>();
        for (ResourcePO po : poList) {
            voList.add(transformPO2VO(po));
        }
        return voList;
    }

    public static ResourcePO transformVO2PO(ResourceVO vo) {
        if (vo == null) {
            return null;
        }
        ResourcePO po = new ResourcePO();
        //转换基本信息
        po.setDescription(vo.getDescription());
        po.setId(vo.getResourceId());
        po.setName(vo.getName());
        po.setPermission(vo.getPermission());
        po.setType(vo.getType());
        po.setOwnerId(vo.getOwnerId());
        po.setOwnerName(vo.getOwnerName());
        //根据类型转换不同的对象
        switch (ResourceTypeEnum.fromCode(vo.getType())) {
            case DATA_SOURCE:
                po.setJdbcPassword(vo.getDataSource().getPassword());
                po.setJdbcDriver(vo.getDataSource().getDriver());
                po.setJdbcUrl(vo.getDataSource().getUrl());
                po.setJdbcUsername(vo.getDataSource().getUsername());
                break;
            case SLAVE_SOURCE:
                po.setSlaveIp(vo.getSlave().getIp());
                po.setSlavePort(vo.getSlave().getPort());
                po.setSlaveThread(vo.getSlave().getThread());
                break;
        }
        return po;
    }

}
