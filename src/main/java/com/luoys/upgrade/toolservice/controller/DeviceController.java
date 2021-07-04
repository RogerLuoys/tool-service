package com.luoys.upgrade.toolservice.controller;

import com.luoys.upgrade.toolservice.common.Result;
import com.luoys.upgrade.toolservice.controller.transform.TransformDevice;
import com.luoys.upgrade.toolservice.controller.vo.DeviceDetailVO;
import com.luoys.upgrade.toolservice.dao.DeviceMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    private DeviceMapper deviceMapper;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Result<String> create(@RequestBody DeviceDetailVO deviceDetailVO) {
        log.info("---》开始新增设备：{}", deviceDetailVO);
        int result = deviceMapper.insert(TransformDevice.transformVO2PO(deviceDetailVO));
        return result == 1 ? Result.success("创建成功") : Result.error("创建失败");
    }
}
