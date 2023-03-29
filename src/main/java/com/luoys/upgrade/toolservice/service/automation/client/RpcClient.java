package com.luoys.upgrade.toolservice.service.automation.client;

import com.alibaba.fastjson.JSON;
import com.luoys.upgrade.toolservice.service.dto.StepDTO;
import com.luoys.upgrade.toolservice.service.enums.DefaultEnum;
import com.luoys.upgrade.toolservice.service.enums.autoStep.methodType.RpcEnum;
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

    private String defaultUrl = null;

    /**
     * 执行rpc调用，仅支持rpc接口有且只有单个入参的情况 todo 要支持多入参
     * 有同步锁
     *
     * @param stepDTO rpc对象
     * @return 调用的response
     */
    public String execute(StepDTO stepDTO) {
        switch (RpcEnum.fromCode(stepDTO.getMethodType())) {
            case INVOKE:
                return invoke(stepDTO.getParameter1(), stepDTO.getParameter2(), stepDTO.getParameter3());
            case SET_DEFAULT_URL:
                this.setDefaultUrl(stepDTO.getParameter1());
                return DefaultEnum.DEFAULT_CLIENT_SUCCESS.getValue();
            default:
                return DefaultEnum.DEFAULT_CLIENT_ERROR.getValue();
        }
    }

    private void setDefaultUrl(String defaultUrl) {
        this.defaultUrl = defaultUrl;
    }

    /**
     * 泛化调用rpc接口
     *
     * @param fullLocation 完整调用地址，如 "dubbo://ip:port/interface#method"
     * @param paramType 被测rpc接口的入参类型
     * @param paramValue 被测rpc接口的入参
     * @return jason格式调用结果
     */
    private String invoke(String fullLocation, String paramType, String paramValue) {
        if (!fullLocation.equals(":////")) {
            fullLocation = defaultUrl + fullLocation;
        }
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


}
