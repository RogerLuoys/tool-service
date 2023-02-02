package com.luoys.upgrade.toolservice.service.automation.client;

import com.alibaba.fastjson.JSON;
import com.luoys.upgrade.toolservice.service.dto.StepDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.rpc.service.GenericService;

/**
 * rpc调用客户端，用于自动化步骤rpc类型的实现
 *
 * @author luoys
 */
@Slf4j
public class RpcClient {

//    private ReferenceConfig<GenericService> reference;

//    /**
//     * 执行rpc调用，仅支持rpc接口有且只有单个入参的情况
//     * 有同步锁
//     *
//     * @param autoStepPO rpc对象
//     * @return 调用的response
//     */
//    public String execute(AutoStepPO autoStepPO) {
//        return invoke(autoStepPO.getParameter1(), autoStepPO.getParameter2(), autoStepPO.getParameter3());
//    }

    /**
     * 执行rpc调用，仅支持rpc接口有且只有单个入参的情况 todo 要支持多入参
     * 有同步锁
     *
     * @param stepDTO rpc对象
     * @return 调用的response
     */
    public String execute(StepDTO stepDTO) {
        return invoke(stepDTO.getParameter1(), stepDTO.getParameter2(), stepDTO.getParameter3());
    }

    /**
     * 泛化调用rpc接口
     *
     * @param fullLocation 完整调用地址，如 "dubbo://ip:port/interface#method"
     * @param paramType 被测rpc接口的入参类型
     * @param paramValue 被测rpc接口的入参
     * @return jason格式调用结果
     */
    public String invoke(String fullLocation, String paramType, String paramValue) {

        // 截取服务器地址
        String url = fullLocation.substring(0, fullLocation.lastIndexOf("/"));
        // 截取接口className
        String interfaceClass = fullLocation.substring(fullLocation.lastIndexOf("/") + 1, fullLocation.lastIndexOf("#"));
        // 截取方法名
        String methodName = fullLocation.substring(fullLocation.lastIndexOf("#") + 1);

        //创建ApplicationConfig
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("autoTester");
        //创建服务引用配置
        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        //设置接口,com.luoys.upgrade.uc.share.service.UserService
        referenceConfig.setInterface(interfaceClass);
        referenceConfig.setApplication(applicationConfig);
        referenceConfig.setUrl(url);
        //重点：设置为泛化调用
        //注：不再推荐使用参数为布尔值的setGeneric函数
        //应该使用referenceConfig.setGeneric("true")代替
        referenceConfig.setGeneric("true");
        //设置超时时间
        referenceConfig.setTimeout(10000);

        //获取服务，由于是泛化调用，所以获取的一定是GenericService类型
        GenericService genericService = referenceConfig.get();

        //使用GenericService类对象的$invoke方法可以代替原方法使用
        //第一个参数是需要调用的方法名,queryByUserId
        //第二个参数是需要调用的方法的参数类型数组，为String数组，里面存入参数的全类名。
        //第三个参数是需要调用的方法的参数数组，为Object数组，里面存入需要的参数。
        Object result;
        if (paramType.equals(String.class.getName())) {
            result = genericService.$invoke(methodName, new String[]{paramType}, new Object[]{paramValue});
        } else if (paramType.equals(Integer.class.getName())) {
            result = genericService.$invoke(methodName, new String[]{paramType}, new Object[]{Integer.valueOf(paramValue)});
        } else if (paramType.equals(Long.class.getName())) {
            result = genericService.$invoke(methodName, new String[]{paramType}, new Object[]{Long.valueOf(paramValue)});
        } else {
            result = genericService.$invoke(methodName, new String[]{paramType}, new Object[]{JSON.parseObject(paramValue)});
        }

        referenceConfig.destroy();
        return JSON.toJSONString(result);
    }

//    /**
//     * 执行rpc调用，仅支持rpc接口有且只有单个入参的情况
//     * 有同步锁
//     *
//     * @param rpcDTO rpc对象
//     * @return 调用的response
//     */
//    public String execute(RpcDTO rpcDTO) {
//
//        // 如果rpc接口入参为Integer String Long Date，则直接取参数列表第一个参数，并调用接口
//        if (rpcDTO.getParameterType().equals(Integer.class.getName())) {
//            return invoke(rpcDTO.getUrl(), rpcDTO.getInterfaceName(), rpcDTO.getMethodName(),
//                    new String[]{rpcDTO.getParameterType()},
//                    new Object[]{Integer.valueOf(rpcDTO.getParameterList().get(0).getValue())});
//        } else if (rpcDTO.getParameterType().equals(String.class.getName())) {
//            return invoke(rpcDTO.getUrl(), rpcDTO.getInterfaceName(), rpcDTO.getMethodName(),
//                    new String[]{rpcDTO.getParameterType()},
//                    new Object[]{rpcDTO.getParameterList().get(0).getValue()});
//        } else if (rpcDTO.getParameterType().equals(Long.class.getName())) {
//            return invoke(rpcDTO.getUrl(), rpcDTO.getInterfaceName(), rpcDTO.getMethodName(),
//                    new String[]{rpcDTO.getParameterType()},
//                    new Object[]{Long.valueOf(rpcDTO.getParameterList().get(0).getValue())});
//        } else if (rpcDTO.getParameterType().equals(Date.class.getName())) {
//            return invoke(rpcDTO.getUrl(), rpcDTO.getInterfaceName(), rpcDTO.getMethodName(),
//                    new String[]{rpcDTO.getParameterType()},
//                    new Object[]{new Date(Long.parseLong(rpcDTO.getParameterList().get(0).getValue()))});
//        }
//
//        // 如果rpc接口入参为pojo对象，则将参数列表转换成对于的map，再调用接口
//        Map<String, Object> paramMap = new HashMap<>();
//        for (ParameterDTO parameterDTO : rpcDTO.getParameterList()) {
//            //如果参数类型是Integer Long Date，则把参数值从String转成Integer Long Date
//            if (parameterDTO.getComment().equals(Integer.class.getName())) {
//                paramMap.put(parameterDTO.getName(), Integer.valueOf(parameterDTO.getValue()));
//            } else if (parameterDTO.getComment().equals(String.class.getName())) {
//                paramMap.put(parameterDTO.getName(), parameterDTO.getValue());
//            } else if (parameterDTO.getComment().equals(Long.class.getName())) {
//                paramMap.put(parameterDTO.getName(), Long.valueOf(parameterDTO.getValue()));
//            } else if (parameterDTO.getComment().equals(Date.class.getName())) {
//                paramMap.put(parameterDTO.getName(), new Date(Long.parseLong(parameterDTO.getValue())));
//            } else {
//                log.error("--->不支持rpc入参类型：{}", parameterDTO);
//                return null;
//            }
//        }
//
//        // 仅支持单入参
//        return invoke(rpcDTO.getUrl(), rpcDTO.getInterfaceName(), rpcDTO.getMethodName(),
//                new String[]{rpcDTO.getParameterType()},
//                new Object[]{paramMap});
//    }





//    /**
//     * 通过泛化调用的形式直接调用目标方法
//     *
//     * @param url           完整的RPC应用地址-格式(协议类型://IP:端口/对象名)
//     * @param interfaceName 服务接口对应的名称(如：UserService.class.getName())
//     * @param methodName    目标方法的名称
//     * @param paramTypeList 目标方法入参类型列表(如：new String[] {"java.lang.String"},...)
//     * @param paramList     目标方法入参列表(如：new Object[]{"入参一"},...)
//     * @return 调用结果的json字符串
//     */
//    private String invoke(String url, String interfaceName, String methodName, String[] paramTypeList, Object[] paramList) {
//        try {
////            Thread.sleep(1000L);
//            reference = new ReferenceConfig<>();
//            // 弱类型接口名
//            reference.setInterface(interfaceName);
//            reference.setUrl(url);
//            reference.setRetries(0);
//            // 声明为泛化接口
//            reference.setGeneric("true");
//            reference.setCheck(false);
//            GenericService genericService = reference.get();
//            // 传递参数对象的json字符串进行一次调用
//            Object result = genericService.$invoke(methodName, paramTypeList, paramList);
//            return JSON.toJSONString(result);
//        } catch (Throwable ex) {
//            log.error("--->调用rpc接口异常", ex);
//            return null;
//        } finally {
//            reference.destroy();
//        }
//    }


}
