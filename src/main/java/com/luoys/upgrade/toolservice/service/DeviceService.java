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

    /**
     * 创建单个设备
     * @param deviceVO 设备对象
     * @return 成功为true，失败为false
     */
    public Boolean create(DeviceVO deviceVO) {
        int result = deviceMapper.insert(TransformDevice.transformVO2PO(deviceVO));
        return result == 1;
    }

    /**
     * 逻辑删除单个设备
     * @param deviceId 设备主键id
     * @return 成功为true，失败为false
     */
    public Boolean remove(int deviceId) {
        int result = deviceMapper.delete(deviceId);
        return result == 1;
    }

    /**
     * 更新单个设备
     * @param deviceVO 设备对象
     * @return 成功为true，失败为false
     */
    public Boolean update(DeviceVO deviceVO) {
        int result = deviceMapper.update(TransformDevice.transformVO2PO(deviceVO));
        return result == 1;
    }

    /**
     * 查询设备列表
     * @param type 类型
     * @param name 名字
     * @param userId 用户id
     * @param pageIndex 页码
     * @return 设备列表
     */
    public List<DeviceSimpleVO> query(Integer type, String name, String userId, Integer pageIndex) {
        return TransformDevice.transformPO2SimpleVO(deviceMapper.list(type, name, userId, pageIndex-1));
    }

    /**
     * 查询设备详情
     * @param deviceId 设备主键id
     * @return 设备对象
     */
    public DeviceVO queryDetail(Integer deviceId) {
        return TransformDevice.transformPO2VO(deviceMapper.selectById(deviceId));
    }

}
