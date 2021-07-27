package com.luoys.upgrade.toolservice.service.dto;

import lombok.Data;

import java.util.List;

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
//
//    /**
//     * 参数类型列表，className，如：java.lang.String或com.luoys.upgrade.uc.share.dto.UserDTO
//     */
//    private List<String> paramTypeList;
//
//    /**
//     * 入参列表，json格式，如："{'name':'Tom','age':24}"
//     */
//    private List<Object> paramList;

    /**
     * rpc接口的传参列表，其中
     * -name只作为前端展示
     * -value是参数的值，json格式，如："{'name':'Tom','age':24}"
     * -comment是参数类型，className，如：java.lang.String或com.luoys.upgrade.uc.share.dto.UserDTO
     */
    private List<ParamDTO> paramList;
}
