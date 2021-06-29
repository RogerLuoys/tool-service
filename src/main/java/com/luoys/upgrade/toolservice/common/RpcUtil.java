package com.luoys.upgrade.toolservice.common;

import com.alibaba.fastjson.JSON;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.rpc.service.GenericService;

public class RpcUtil {

    /**
     * 获取泛化调用接口
     *
     * @param url           完整的RPC应用地址-格式(协议类型://IP:端口/对象名)
     * @param interfaceName 服务接口对应的名称(如：UserService.class.getName())
     * @return 泛化接口
     */
    public static GenericService getGenericService(String url, String interfaceName) {
        ReferenceConfig<GenericService> reference = new ReferenceConfig();
        reference.setUrl(url);
        reference.setInterface(interfaceName);
        reference.setGeneric("true");
        return reference.get();
    }

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
    public static String invoke(String url, String interfaceName, String methodName, String[] paramTypeList, Object[] paramList) {
        ReferenceConfig<GenericService> reference = new ReferenceConfig();
        reference.setUrl(url);
        reference.setInterface(interfaceName);
        reference.setGeneric("true");
        GenericService genericService = reference.get();
        // 基本类型以及Date,List,Map等不需要转换，直接调用
        Object result = genericService.$invoke(methodName, paramTypeList, paramList);
        return JSON.toJSONString(result);
    }

    /**
     * 进程睡眠，强制等待
     *
     * @param second 等待的时间-单位秒
     */
    public static void forceWait(int second) {
        try {
            Thread.sleep((long) second * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
