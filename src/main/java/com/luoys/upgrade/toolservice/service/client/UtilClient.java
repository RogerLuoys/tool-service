package com.luoys.upgrade.toolservice.service.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.luoys.upgrade.toolservice.service.dto.UtilDTO;
import com.luoys.upgrade.toolservice.service.enums.UtilTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Random;

/**
 * 工具客户端，用于自动化步骤的工具类型实现
 *
 * @author luoys
 */
@Slf4j
@Component
public class UtilClient {

    public synchronized String execute(UtilDTO utilDTO) {
        switch (UtilTypeEnum.fromCode(utilDTO.getType())) {
            case SLEEP:
                return sleep(Integer.parseInt(utilDTO.getParam1()));
            case GET_JSON_VALUE:
                return getJsonValue(utilDTO.getParam1(), utilDTO.getParam2());
            case GET_TIME:
                return getTime();
            case GET_RANDOM_NUMBER:
                return getRandomNumber();
            default:
                return "执行异常";
        }
    }


    private String sleep(int sec) {
        try {
            Thread.sleep(sec * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "成功";
    }

    private String getJsonValue(String key, String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        return jsonObject.getString(key);
    }

    private String getTime() {
        Date currentTime = new Date();
        return String.valueOf(currentTime.getTime());
    }

    private String getRandomNumber() {
        Random random = new Random();
        return String.valueOf(random.nextInt(100));
    }
}
