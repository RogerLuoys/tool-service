package com.luoys.upgrade.toolservice.controller.transform;

import com.alibaba.fastjson.JSON;
import com.luoys.upgrade.toolservice.controller.dto.ContainerDTO;
import com.luoys.upgrade.toolservice.controller.dto.DataBaseDTO;
import com.luoys.upgrade.toolservice.controller.dto.MobilePhoneDTO;
import com.luoys.upgrade.toolservice.controller.vo.DeviceDetailVO;
import com.luoys.upgrade.toolservice.controller.vo.DeviceVO;
import com.luoys.upgrade.toolservice.dao.po.DevicePO;

public class TransformDevice {
    private static final String SEPARATOR=" &&& ";

    public static DeviceVO transformPO2DeviceVO(DevicePO po) {
        if (po == null) {
            return null;
        }
        DeviceVO vo = new DeviceVO();
        vo.setDescription(po.getDescription());
        vo.setOwnerId(po.getOwnerId());
        vo.setPermission(po.getPermission());
        vo.setTitle(po.getTitle());
        vo.setType(po.getType());
        return vo;
    }

    public static DeviceDetailVO transformPO2VO(DevicePO po) {
        if (po == null) {
            return null;
        }
        DeviceDetailVO vo = new DeviceDetailVO();
        vo.setId(po.getId());
        vo.setDescription(po.getDescription());
        vo.setPermission(po.getPermission());
        vo.setTitle(po.getTitle());
        vo.setType(po.getType());
        switch (po.getType()) {
            case 1:
                vo.setDataBase(toDataBase(po.getItems()));
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

    public static DevicePO transformVO2PO(DeviceDetailVO vo) {
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
                po.setItems(toDataBase(vo.getDataBase()));
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

    private static DataBaseDTO toDataBase(String template) {
        return (DataBaseDTO) JSON.parse(template);
    }

    private static String toDataBase(DataBaseDTO dataBaseDTO) {
        return JSON.toJSONString(dataBaseDTO);
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
