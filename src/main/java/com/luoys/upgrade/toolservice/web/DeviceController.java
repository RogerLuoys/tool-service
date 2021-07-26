package com.luoys.upgrade.toolservice.web;

import com.luoys.upgrade.toolservice.common.Result;
import com.luoys.upgrade.toolservice.service.DeviceService;
import com.luoys.upgrade.toolservice.web.vo.DeviceSimpleVO;
import com.luoys.upgrade.toolservice.web.vo.DeviceVO;
import com.luoys.upgrade.toolservice.web.vo.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Result<String> create(@RequestHeader("userId") String userId, @RequestHeader("userName") String userName, @RequestBody DeviceVO deviceVO) {
        deviceVO.setOwnerId(userId);
        deviceVO.setOwnerName(userName);
        log.info("---》开始新增设备：{}", deviceVO);
//        int result = deviceMapper.insert(TransformDevice.transformVO2PO(deviceVO));
//        return result == 1 ? Result.success("创建成功") : Result.error("创建失败");
        return Result.ifSuccess(deviceService.create(deviceVO));
    }

//    @RequestMapping(value = "/quickCreate", method = RequestMethod.POST)
//    public Result<String> quickCreate(@RequestHeader("userId") String userId, @RequestHeader("userName") String userName, @RequestBody DataSourceDTO dataSourceDTO) {
//        DeviceVO deviceVO = new DeviceVO();
//        deviceVO.setOwnerId(userId);
//        deviceVO.setOwnerName(userName);
//        deviceVO.setDataSource(dataSourceDTO);
//        deviceVO.setTitle(dataSourceDTO.getUserName());
//        deviceVO.setType(DeviceTypeEnum.DATA_SOURCE.getCode());
//        deviceVO.setPermission(PermissionEnum.OWNER.getCode());
//        log.info("---》开始新增设备：{}", deviceVO);
//        int result = deviceMapper.insert(TransformDevice.transformVO2PO(deviceVO));
//        return result == 1 ? Result.success("创建成功") : Result.error("创建失败");
//    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public Result<String> remove(@RequestParam("deviceId") int deviceId) {
        log.info("---》开始删除设备：{}", deviceId);
//        int result = deviceMapper.delete(deviceId);
//        return result == 1 ? Result.success("删除成功") : Result.error("删除失败");
        return Result.ifSuccess(deviceService.remove(deviceId));
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result<String> update(@RequestBody DeviceVO deviceVO) {
        log.info("---》开始更新设备：{}", deviceVO);
//        int result = deviceMapper.update(TransformDevice.transformVO2PO(deviceVO));
//        return result == 1 ? Result.success("更新成功") : Result.error("更新失败");
        return Result.ifSuccess(deviceService.update(deviceVO));
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public Result<PageInfo<DeviceSimpleVO>> query(@RequestParam(value = "type", required = false) Integer type,
                                                  @RequestParam(value = "name", required = false) String name,
                                                  @RequestHeader("userId") String userId,
                                                  @RequestParam("pageIndex") Integer pageIndex) {
        log.info("---》开始查询设备列表：");
        PageInfo<DeviceSimpleVO> pageInfo = new PageInfo<>();
        pageInfo.setList(deviceService.query(type, name, userId, pageIndex));
        pageInfo.setTotal(pageInfo.getList().size());
        return Result.success(pageInfo);
    }

    @RequestMapping(value = "/queryDetail", method = RequestMethod.GET)
    public Result<DeviceVO> queryDetail(@RequestParam("deviceId") Integer deviceId) {
        log.info("---》开始查询设备详情：deviceId={}", deviceId);
//        return Result.success(TransformDevice.transformPO2VO(deviceMapper.selectById(deviceId)));
        return Result.success(deviceService.queryDetail(deviceId));
    }

}
