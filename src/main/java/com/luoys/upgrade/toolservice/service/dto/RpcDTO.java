package com.luoys.upgrade.toolservice.service.dto;

import lombok.Data;

import java.util.List;

/**
 * rpc类
 *
 * @author luoys
 */
@Data
@Deprecated
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


//    /**
//     * 将参数合并入rpc请求中，处理url和参数列表中的值
//     * @param parameterList -
//     */
//    public void merge(List<ParameterDTO> parameterList) {
//        // 无变量
//        if (parameterList == null || parameterList.size() == 0) {
//            return;
//        }
//        StringBuilder fullParamSymbol = new StringBuilder();
//        String oneValue;
//        // 替换url字段中的变量
//        if (url.matches(StringUtil.PARAM_REGEX)) {
//            for (ParameterDTO parameterDTO : parameterList) {
//                fullParamSymbol.delete(0, fullParamSymbol.length());
//                fullParamSymbol.append("${").append(parameterDTO.getName()).append("}");
//                if (url.contains(fullParamSymbol)) {
//                    url = url.replace(fullParamSymbol, parameterDTO.getValue());
//                }
//            }
//        }
//
//        //遍历目标，替换rpc入参
//        for (int i = 0; i < this.parameterList.size(); i++) {
//            oneValue = this.parameterList.get(i).getValue();
//            //判断入参中是否有指定占位符，无则不用替换直接插入
//            if (!oneValue.matches(StringUtil.PARAM_REGEX)) {
//                continue;
//            }
//            //将所有实际参数与其中一个rpc入参值中的占位符替换
//            for (ParameterDTO parameterDTO : parameterList) {
//                fullParamSymbol.delete(0, fullParamSymbol.length());
//                fullParamSymbol.append("${").append(parameterDTO.getName()).append("}");
//                if (oneValue.contains(fullParamSymbol)) {
//                    this.parameterList.get(i).setValue(oneValue.replace(fullParamSymbol, parameterDTO.getValue()));
//                }
//            }
//        }

//        //遍历目标，替换rpc入参
//        for (ParameterDTO rpcParam : this.parameterList) {
//            String oneValue = rpcParam.getValue();
//            //判断入参中是否有指定占位符，无则不用替换直接插入
//            if (oneValue.matches(".*\\$\\{[A-Za-z0-9]{1,20}}.*")) {
//                //将所有实际参数与其中一个rpc入参值中的占位符替换
//                for (ParameterDTO parameterDTO : parameterList) {
//                    fullParamSymbol = "${" + parameterDTO.getName() + "}";
//                    if (oneValue.contains(fullParamSymbol)) {
//                        rpcParam.setValue(oneValue.replace(fullParamSymbol, parameterDTO.getValue()));
//                    }
//                }
//            }
//            rpcParamList.add(rpcParam);
//        }
//        rpcDTO.setParameterList(rpcParamList);
//        log.info("---->合并后rpc请求：{}", rpcDTO);
//    }

}
