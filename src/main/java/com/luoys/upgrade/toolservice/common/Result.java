package com.luoys.upgrade.toolservice.common;

import com.luoys.upgrade.toolservice.service.enums.KeywordEnum;
import com.luoys.upgrade.toolservice.service.enums.ResultEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 对外返回结果
 *
 * @author luoys
 */
@Data
public class Result<T> implements Serializable {
    private int code;
    private boolean success;
    private String message;
    private T data;
    private static final long serialVersionUID = 1L;

    Result(int code, boolean success, String message, T data) {
        this.code = code;
        this.success = success;
        this.message = message;
        this.data = data;
    }

    /**
     * 根据data的值和类型，返回不同的结果
     *
     * @param data           -支持Boolean、String、Integer
     * @param successMessage -如果判断为业务处理成功，则使用此参数为message
     * @param errorMessage   -如果判断为业务处理失败，则使用此参数为message
     * @return -Result实例
     */
    public static <T> Result message(T data, String successMessage, String errorMessage) {
        if (data instanceof Boolean && (Boolean) data) {
            return new Result<>(ResultEnum.SUCCESS_FOR_MESSAGE.getCode(), true, successMessage, (Boolean) data);
        } else if (data instanceof String) {
            return new Result<>(ResultEnum.SUCCESS_FOR_MESSAGE.getCode(), true, successMessage, (String) data);
        } else if (data instanceof Integer) {
            return new Result<>(ResultEnum.SUCCESS_FOR_MESSAGE.getCode(), true, successMessage, (Integer) data);
        } else {
            return new Result<>(ResultEnum.ERROR_FOR_MESSAGE.getCode(), false, errorMessage, (Object) data);
        }
    }

    /**
     * 根据data的值和类型，返回不同的结果
     * 如果业务处理
     *
     * @param data         -支持Boolean、String、Integer
     * @param errorMessage -如果判断为业务处理失败，则使用此参数为message
     * @return -Result实例
     */
    public static <T> Result message(T data, String errorMessage) {
        return message(data, ResultEnum.SUCCESS_FOR_MESSAGE.getValue(), errorMessage);
    }

    /**
     * 根据data的值和类型，返回不同的结果
     *
     * @param data -支持Boolean、String、Integer
     * @return -Result实例
     */
    public static <T> Result message(T data) {
        return message(data, ResultEnum.SUCCESS_FOR_MESSAGE.getValue(), ResultEnum.ERROR_FOR_MESSAGE.getValue());
    }

    /**
     * 根据data的值和类型，返回不同的结果
     *
     * @param data -支持Boolean、String、Integer
     * @return -Result实例，如果data为空，则success字段为false
     */
    public static <T> Result ifSuccess(T data) {
        return message(data, ResultEnum.SUCCESS_FOR_CUSTOM.getValue(), ResultEnum.ERROR_FOR_MESSAGE.getValue());
    }

    public static <T> Result success(T data) {
        return new Result<>(ResultEnum.SUCCESS_FOR_CUSTOM.getCode(), true, ResultEnum.SUCCESS_FOR_CUSTOM.getValue(), data);
    }

    public static <T> Result error(String message) {
        return new Result<>(ResultEnum.ERROR_FOR_CUSTOM.getCode(), false, message, null);
    }

    public static <T> Result errorMessage(String message) {
        return new Result<>(ResultEnum.ERROR_FOR_MESSAGE.getCode(), false, message, null);
    }

}
