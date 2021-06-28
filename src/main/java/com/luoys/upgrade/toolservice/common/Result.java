package com.luoys.upgrade.toolservice.common;

import lombok.Data;

import java.io.Serializable;

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

    public static <T> Result success(T data) {
        Result<T> result = new Result(1, true, "成功", data);
        return result;
    }

    public static <T> Result error(String message) {
        Result<T> result = new Result(0, false, message, null);
        return result;
    }

}
