package com.luoys.upgrade.toolservice.controller;

import com.luoys.upgrade.toolservice.common.Result;
import com.luoys.upgrade.toolservice.controller.transform.TransformDevice;
import com.luoys.upgrade.toolservice.controller.vo.DeviceSimpleVO;
import com.luoys.upgrade.toolservice.controller.vo.DeviceVO;
import com.luoys.upgrade.toolservice.controller.vo.PageInfo;
import com.luoys.upgrade.toolservice.dao.DeviceMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    private DeviceMapper deviceMapper;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Result<String> create(@RequestBody DeviceVO deviceVO) {
        log.info("---》开始新增设备：{}", deviceVO);
        int result = deviceMapper.insert(TransformDevice.transformVO2PO(deviceVO));
        return result == 1 ? Result.success("创建成功") : Result.error("创建失败");
    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public Result<String> remove(@RequestParam("deviceId") int deviceId) {
        log.info("---》开始删除设备：{}", deviceId);
        int result = deviceMapper.delete(deviceId);
        return result == 1 ? Result.success("删除成功") : Result.error("删除失败");
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result<String> update(@RequestBody DeviceVO deviceVO) {
        log.info("---》开始更新设备：{}", deviceVO);
        int result = deviceMapper.update(TransformDevice.transformVO2PO(deviceVO));
        return result == 1 ? Result.success("更新成功") : Result.error("更新失败");
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Result<PageInfo<DeviceSimpleVO>> query(@RequestParam(value = "type", required = false) Integer type,
                                                  @RequestParam(value = "title", required = false) String title,
                                                  @RequestParam("owner") String owner,
                                                  @RequestParam("startIndex") Integer startIndex) {
        log.info("---》开始查询设备列表：");
        PageInfo<DeviceSimpleVO> pageInfo = new PageInfo<>();
        pageInfo.setData(TransformDevice.transformPO2SimpleVO(deviceMapper.list(type, title, owner, startIndex)));
        pageInfo.setTotal(pageInfo.getData().size());
        return Result.success(pageInfo);
    }
}
