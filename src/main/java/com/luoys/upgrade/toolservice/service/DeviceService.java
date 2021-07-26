package com.luoys.upgrade.toolservice.service;

import com.luoys.upgrade.toolservice.service.transform.TransformDevice;
import com.luoys.upgrade.toolservice.web.vo.DeviceSimpleVO;
import com.luoys.upgrade.toolservice.web.vo.DeviceVO;
import com.luoys.upgrade.toolservice.dao.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {

    @Autowired
    private DeviceMapper deviceMapper;

    public Boolean create(DeviceVO deviceVO) {
        int result = deviceMapper.insert(TransformDevice.transformVO2PO(deviceVO));
        return result == 1;
    }

    public Boolean remove(int deviceId) {
        int result = deviceMapper.delete(deviceId);
        return result == 1;
    }

    public Boolean update(DeviceVO deviceVO) {
        int result = deviceMapper.update(TransformDevice.transformVO2PO(deviceVO));
        return result == 1;
    }

    public List<DeviceSimpleVO> query(Integer type, String name, String userId, Integer pageIndex) {
        return TransformDevice.transformPO2SimpleVO(deviceMapper.list(type, name, userId, pageIndex-1));
    }

    public DeviceVO queryDetail(Integer deviceId) {
        return TransformDevice.transformPO2VO(deviceMapper.selectById(deviceId));
    }

}
