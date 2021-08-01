package com.luoys.upgrade.toolservice.service.transform;

import com.luoys.upgrade.toolservice.dao.po.ResourcePO;
import com.luoys.upgrade.toolservice.service.dto.DataSourceDTO;
import com.luoys.upgrade.toolservice.service.dto.DeviceDTO;
import com.luoys.upgrade.toolservice.service.enums.ResourceTypeEnum;
import com.luoys.upgrade.toolservice.web.vo.ResourceSimpleVO;
import com.luoys.upgrade.toolservice.web.vo.ResourceVO;

import java.util.ArrayList;
import java.util.List;

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
        vo.setUserId(po.getUserId());
        vo.setUserName(po.getUserName());
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
        vo.setUserId(po.getUserId());
        vo.setUserName(po.getUserName());
        //根据类型转换不同的对象
        switch (ResourceTypeEnum.fromCode(po.getType())) {
            case DATA_SOURCE:
                DataSourceDTO dataSourceDTO = new DataSourceDTO();
                dataSourceDTO.setDriver(po.getJdbcDriver());
                dataSourceDTO.setUrl(po.getEnvUrl());
                dataSourceDTO.setUsername(po.getJdbcUsername());
                dataSourceDTO.setPassword(po.getJdbcPassword());
                vo.setDataSource(dataSourceDTO);
                break;
            case DEVICE:
                DeviceDTO deviceDTO = new DeviceDTO();
                deviceDTO.setDpi(po.getDeviceDpi());
                deviceDTO.setModel(po.getDeviceModel());
                deviceDTO.setOs(po.getDeviceOs());
                deviceDTO.setSize(po.getDeviceSize());
                vo.setDevice(deviceDTO);
                break;
            case TEST_ENV:
                vo.setEnvUrl(po.getEnvUrl());
                break;
            case SLAVE_NODE:
                vo.setSlaveUrl(po.getSlaveUrl());
                break;

        }
        return vo;
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
        po.setUserId(vo.getUserId());
        po.setUserName(vo.getUserName());
        //根据类型转换不同的对象
        switch (ResourceTypeEnum.fromCode(vo.getType())) {
            case DATA_SOURCE:
                po.setJdbcPassword(vo.getDataSource().getPassword());
                po.setJdbcDriver(vo.getDataSource().getDriver());
                po.setJdbcUrl(vo.getDataSource().getUrl());
                po.setJdbcUsername(vo.getDataSource().getUsername());
                break;
            case DEVICE:
                po.setDeviceDpi(vo.getDevice().getDpi());
                po.setDeviceModel(vo.getDevice().getModel());
                po.setDeviceOs(vo.getDevice().getOs());
                po.setDeviceSize(vo.getDevice().getSize());
                break;
            case TEST_ENV:
                po.setEnvUrl(vo.getEnvUrl());
                break;
            case SLAVE_NODE:
                po.setSlaveUrl(vo.getSlaveUrl());
                break;
        }
        return po;
    }

}
