package com.luoys.upgrade.toolservice.controller.transform;

import com.alibaba.fastjson.JSON;
import com.luoys.upgrade.toolservice.controller.dto.ContainerDTO;
import com.luoys.upgrade.toolservice.controller.dto.DataSourceDTO;
import com.luoys.upgrade.toolservice.controller.dto.MobilePhoneDTO;
import com.luoys.upgrade.toolservice.controller.enums.DeviceTypeEnum;
import com.luoys.upgrade.toolservice.controller.vo.DeviceSimpleVO;
import com.luoys.upgrade.toolservice.controller.vo.DeviceVO;
import com.luoys.upgrade.toolservice.dao.po.DevicePO;

import java.util.ArrayList;
import java.util.List;

public class TransformDevice {
//    private static final String SEPARATOR=" &&& ";

    public static DeviceSimpleVO transformPO2SimpleVO(DevicePO po) {
        if (po == null) {
            return null;
        }
        DeviceSimpleVO vo = new DeviceSimpleVO();
        vo.setDescription(po.getDescription());
        vo.setOwnerId(po.getOwnerId());
        vo.setPermission(po.getPermission());
        vo.setTitle(po.getTitle());
        vo.setType(po.getType());
        vo.setDeviceId(po.getId());
        vo.setDetail(po.getDetail());
        return vo;
    }

    public static List<DeviceSimpleVO> transformPO2SimpleVO(List<DevicePO> poList) {
        List<DeviceSimpleVO> voList = new ArrayList<>();
        for (DevicePO po : poList) {
            voList.add(transformPO2SimpleVO(po));
        }
        return voList;
    }

    public static DeviceVO transformPO2VO(DevicePO po) {
        if (po == null) {
            return null;
        }
        DeviceVO vo = new DeviceVO();
        vo.setDeviceId(po.getId());
        vo.setDescription(po.getDescription());
        vo.setPermission(po.getPermission());
        vo.setTitle(po.getTitle());
        vo.setType(po.getType());
        switch (po.getType()) {
            case 1:
                vo.setDataSource(toDataSource(po.getDetail()));
                break;
            case 2:
                vo.setMobilePhone(toMobilePhone(po.getDetail()));
                break;
            case 3:
                vo.setContainer(toContainer(po.getDetail()));
                break;
            default:
                break;
        }
        return vo;
    }

    public static DevicePO transformVO2PO(DeviceVO vo) {
        if (vo == null) {
            return null;
        }
        DevicePO po = new DevicePO();
        po.setDescription(vo.getDescription());
        po.setOwnerId(vo.getOwnerId());
        po.setPermission(vo.getPermission());
        po.setTitle(vo.getTitle());
        po.setType(vo.getType());
        switch (DeviceTypeEnum.fromCode(vo.getType())) {
            case DATA_SOURCE:
                po.setDetail(toDataSource(vo.getDataSource()));
                break;
            case MOBILE_PHONE:
                po.setDetail(toMobilePhone(vo.getMobilePhone()));
                break;
            case CONTAINER:
                po.setDetail(toContainer(vo.getContainer()));
                break;
            default:
                break;
        }
        return po;
    }

    private static DataSourceDTO toDataSource(String template) {
        return JSON.parseObject(template, DataSourceDTO.class);
    }

    private static String toDataSource(DataSourceDTO dataSourceDTO) {
        return JSON.toJSONString(dataSourceDTO);
    }

    private static MobilePhoneDTO toMobilePhone(String template) {
        return JSON.parseObject(template, MobilePhoneDTO.class);
    }

    private static String toMobilePhone(MobilePhoneDTO mobilePhoneDTO) {
        return JSON.toJSONString(mobilePhoneDTO);
    }

    private static ContainerDTO toContainer(String template) {
        return JSON.parseObject(template, ContainerDTO.class);
    }

    private static String toContainer(ContainerDTO containerDTO) {
        return JSON.toJSONString(containerDTO);
    }
}
