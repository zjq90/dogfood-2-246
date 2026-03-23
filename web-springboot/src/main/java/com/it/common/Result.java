package com.it.common;

import lombok.Data;
import java.io.Serializable;


@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    
    private Integer code;

    
    private String msg;

    
    private T data;

    public Result() {
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> success() {
        return new Result<>(200, "Operation successful", null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(200, "Operation successful", data);
    }

    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(200, msg, data);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<>(500, msg, null);
    }

    public static <T> Result<T> error(Integer code, String msg) {
        return new Result<>(code, msg, null);
    }

    public boolean isSuccess() {
        return this.code != null && this.code == 200;
    }
}
