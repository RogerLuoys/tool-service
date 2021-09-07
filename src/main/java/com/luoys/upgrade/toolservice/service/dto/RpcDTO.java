package com.luoys.upgrade.toolservice.service.dto;

import lombok.Data;

import java.util.List;

/**
 * rpc类
 *
 * @author luoys
 */
@Data
public class RpcDTO {

    /**
     * rpc服务的地址，格式(协议类型://IP:端口/)
     */
    private String url;

    /**
     * 服务接口的完整名字，className，如：com.luoys.upgrade.uc.share.service.UserService
     */
    private String interfaceName;

    /**
     * 需要调用的具体方法，如 queryByUserId
     */
    private String methodName;

    /**
     * rpc入参类型
     */
    private String parameterType;

    /**
     * rpc接口的传参列表，其中
     * -name只作为前端展示
     * -value是参数的值，json格式，如："{'name':'Tom','age':24}"
     * -comment是参数类型，className，如：java.lang.String或com.luoys.upgrade.uc.share.dto.UserDTO
     */
    private List<ParameterDTO> parameterList;
}
