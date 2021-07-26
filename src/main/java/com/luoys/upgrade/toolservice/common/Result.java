package com.luoys.upgrade.toolservice.common;

import com.luoys.upgrade.toolservice.service.enums.KeywordEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {
    private int code;
    private boolean success;
    private String message;
    private T data;
    private static final String COMMON_SUCCESS_MESSAGE = "业务处理成功";
    private static final String COMMON_ERROR_MESSAGE = "业务处理失败";
    private static final int COMMON_SUCCESS_CODE = 1;
    private static final int COMMON_ERROR_CODE = 0;
    private static final long serialVersionUID = 1L;

    Result(int code, boolean success, String message, T data) {
        this.code = code;
        this.success = success;
        this.message = message;
        this.data = data;
    }

    /**
     * 根据data的值和类型，返回不同的结果
     * @param data -支持Boolean、String、Integer
     * @param successMessage -如果判断为业务处理成功，则使用此参数为message
     * @param errorMessage -如果判断为业务处理失败，则使用此参数为message
     * @return -Result实例
     */
    public static <T> Result ifSuccess(T data, String successMessage, String errorMessage) {
        if (data instanceof Boolean && (Boolean) data) {
            return new Result<>(COMMON_SUCCESS_CODE, true, successMessage, data);
        } else if (data instanceof String) {
            return new Result<>(COMMON_SUCCESS_CODE, true, successMessage, data);
        } else if (data instanceof Integer) {
            return new Result<>(COMMON_SUCCESS_CODE, true, successMessage, data);
        } else {
            return new Result<>(COMMON_SUCCESS_CODE, false, errorMessage, data);
        }
    }

    /**
     * 根据data的值和类型，返回不同的结果
     * @param data -支持Boolean、String、Integer
     * @param errorMessage -如果判断为业务处理失败，则使用此参数为message
     * @return -Result实例
     */
    public static <T> Result ifSuccess(T data, String errorMessage) {
        return ifSuccess(data, COMMON_SUCCESS_MESSAGE, errorMessage);
    }

    /**
     * 根据data的值和类型，返回不同的结果
     * @param data -支持Boolean、String、Integer
     * @return -Result实例
     */
    public static <T> Result ifSuccess(T data) {
        return ifSuccess(data, COMMON_SUCCESS_MESSAGE, COMMON_ERROR_MESSAGE);
    }

    public static <T> Result success(T data) {
        return new Result<>(COMMON_SUCCESS_CODE, true, COMMON_SUCCESS_MESSAGE, data);
    }

    public static <T> Result error(String message) {
        return new Result<>(COMMON_ERROR_CODE, false, message, null);
    }

}
