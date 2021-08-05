package com.luoys.upgrade.toolservice.service.client;

import com.alibaba.fastjson.JSON;
import com.luoys.upgrade.toolservice.service.dto.RpcDTO;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.stereotype.Component;

@Component
public class RPCClient {

    private final ReferenceConfig<GenericService> reference = new ReferenceConfig<>();

    /**
     * 通过泛化调用的形式直接调用目标方法
     *
     * @param url           完整的RPC应用地址-格式(协议类型://IP:端口/对象名)
     * @param interfaceName 服务接口对应的名称(如：UserService.class.getName())
     * @param methodName    目标方法的名称
     * @param paramTypeList 目标方法入参类型列表(如：new String[] {"java.lang.String"},...)
     * @param paramList     目标方法入参列表(如：new Object[]{"入参一"},...)
     * @return 调用结果的json字符串
     */
    private String invoke(String url, String interfaceName, String methodName, String[] paramTypeList, Object[] paramList) {
        try {
            // 弱类型接口名
            reference.setInterface(interfaceName);
            reference.setUrl(url);
            reference.setRetries(0);
            // 声明为泛化接口
            reference.setGeneric("true");
            reference.setCheck(false);
            GenericService genericService = reference.get();
            // 传递参数对象的json字符串进行一次调用
            Object result = genericService.$invoke(methodName, paramTypeList, paramList);
            return JSON.toJSONString(result);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 执行rpc调用
     * @param rpcDTO rpc对象
     * @return 调用的response
     */
    public String execute(RpcDTO rpcDTO) {
        String[] paramTypeArray = new String[rpcDTO.getParameterList().size()];
        Object[] paramArray = new Object[rpcDTO.getParameterList().size()];
        //把对象中的参数列表转换成对应的数组
        for (int i = 0; i < rpcDTO.getParameterList().size(); i++) {
            paramTypeArray[i] = rpcDTO.getParameterList().get(i).getComment();
            //如果参数类型是Integer，则把参数值从String转成Integer
            if (rpcDTO.getParameterList().get(i).getComment().equals(Integer.class.getName())) {
                paramArray[i] = Integer.valueOf(rpcDTO.getParameterList().get(i).getValue());
            } else {
                paramArray[i] = rpcDTO.getParameterList().get(i).getValue();
            }
        }
        return invoke(rpcDTO.getUrl(), rpcDTO.getInterfaceName(), rpcDTO.getMethodName(), paramTypeArray, paramArray);
    }


}
