package com.luoys.upgrade.toolservice.controller.transform;

import com.luoys.upgrade.toolservice.controller.vo.DeviceVO;
import com.luoys.upgrade.toolservice.dao.po.DevicePO;

public class TransformDevice {
    private static final String SEPARATOR=" &&& ";

    public static DeviceVO transformPO2VO(DevicePO po) {
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
}
