package com.weibo.dto;

import lombok.Data;
import java.io.Serializable;

/**
 * 统一响应结果类
 * 用于封装API接口的返回数据
 *
 * @param <T> 返回数据类型
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     * 200-成功，500-失败，其他为业务错误码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 默认构造方法
     */
    public Result() {
    }

    /**
     * 全参数构造方法
     *
     * @param code 状态码
     * @param msg  提示信息
     * @param data 返回数据
     */
    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功响应（无数据）
     *
     * @return Result对象
     */
    public static <T> Result<T> success() {
        return new Result<>(200, "操作成功", null);
    }

    /**
     * 成功响应（带数据）
     *
     * @param data 返回数据
     * @return Result对象
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }

    /**
     * 成功响应（自定义消息和数据）
     *
     * @param msg  提示信息
     * @param data 返回数据
     * @return Result对象
     */
    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(200, msg, data);
    }

    /**
     * 成功响应（仅消息，无数据）
     *
     * @param msg 提示信息
     * @return Result对象
     */
    public static <T> Result<T> success(String msg) {
        return new Result<>(200, msg, null);
    }

    /**
     * 失败响应（默认错误码）
     *
     * @param msg 错误信息
     * @return Result对象
     */
    public static <T> Result<T> error(String msg) {
        return new Result<>(500, msg, null);
    }

    /**
     * 失败响应（自定义错误码）
     *
     * @param code 错误码
     * @param msg  错误信息
     * @return Result对象
     */
    public static <T> Result<T> error(Integer code, String msg) {
        return new Result<>(code, msg, null);
    }

    /**
     * 失败响应（仅消息）
     *
     * @param msg 错误信息
     * @return Result对象
     */
    public static <T> Result<T> fail(String msg) {
        return new Result<>(500, msg, null);
    }

    /**
     * 失败响应（自定义错误码）
     *
     * @param code 错误码
     * @param msg  错误信息
     * @return Result对象
     */
    public static <T> Result<T> fail(Integer code, String msg) {
        return new Result<>(code, msg, null);
    }

    /**
     * 判断是否成功
     *
     * @return true-成功，false-失败
     */
    public boolean isSuccess() {
        return code != null && code == 200;
    }

    /**
     * 获取消息
     *
     * @return 提示信息
     */
    public String getMessage() {
        return msg;
    }
}
