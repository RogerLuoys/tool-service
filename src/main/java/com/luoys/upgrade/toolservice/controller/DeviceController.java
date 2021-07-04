package com.luoys.upgrade.toolservice.controller;

import com.luoys.upgrade.toolservice.common.Result;
import com.luoys.upgrade.toolservice.controller.transform.TransformDevice;
import com.luoys.upgrade.toolservice.controller.vo.DeviceDetailVO;
import com.luoys.upgrade.toolservice.dao.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    DeviceMapper deviceMapper;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Result<String> create(@RequestBody DeviceDetailVO deviceDetailVO) {
        int result = deviceMapper.insert(TransformDevice.transformVO2PO(deviceDetailVO));
        return result == 1 ? Result.success("创建成功") : Result.error("创建失败");
    }
}
