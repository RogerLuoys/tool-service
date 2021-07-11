package com.luoys.upgrade.toolservice.controller.transform;

import com.alibaba.fastjson.JSON;
import com.luoys.upgrade.toolservice.controller.dto.ContainerDTO;
import com.luoys.upgrade.toolservice.controller.dto.DataSourceDTO;
import com.luoys.upgrade.toolservice.controller.dto.MobilePhoneDTO;
import com.luoys.upgrade.toolservice.controller.vo.DeviceSimpleVO;
import com.luoys.upgrade.toolservice.controller.vo.DeviceVO;
import com.luoys.upgrade.toolservice.dao.po.DevicePO;

import java.util.ArrayList;
import java.util.List;

public class TransformDevice {
    private static final String SEPARATOR=" &&& ";

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
        vo.setId(po.getId());
        vo.setDescription(po.getDescription());
        vo.setPermission(po.getPermission());
        vo.setTitle(po.getTitle());
        vo.setType(po.getType());
        switch (po.getType()) {
            case 1:
                vo.setDataSource(toDataSource(po.getItems()));
                break;
            case 2:
                vo.setMobilePhone(toMobilePhone(po.getItems()));
                break;
            case 3:
                vo.setContainer(toContainer(po.getItems()));
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
        switch (vo.getType()) {
            case 1:
                po.setItems(toDataSource(vo.getDataSource()));
                break;
            case 2:
                po.setItems(toMobilePhone(vo.getMobilePhone()));
                break;
            case 3:
                po.setItems(toContainer(vo.getContainer()));
                break;
            default:
                break;
        }
        return po;
    }

    private static DataSourceDTO toDataSource(String template) {
        return (DataSourceDTO) JSON.parse(template);
    }

    private static String toDataSource(DataSourceDTO dataSourceDTO) {
        return JSON.toJSONString(dataSourceDTO);
    }

    private static MobilePhoneDTO toMobilePhone(String template) {
        return (MobilePhoneDTO) JSON.parse(template);
    }

    private static String toMobilePhone(MobilePhoneDTO mobilePhoneDTO) {
        return JSON.toJSONString(mobilePhoneDTO);
    }

    private static ContainerDTO toContainer(String template) {
        return (ContainerDTO) JSON.parse(template);
    }

    private static String toContainer(ContainerDTO containerDTO) {
        return JSON.toJSONString(containerDTO);
    }
}
