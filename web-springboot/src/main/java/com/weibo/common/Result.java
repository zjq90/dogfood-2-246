package com.weibo.common;

import lombok.Data;
import java.io.Serializable;

/**
 * 统一响应结果类
 * 用于封装API接口的返回结果
 * 
 * @param <T> 数据类型
 * @author weibo Team
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码 (200-成功, 其他-失败)
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
     * 无参构造函数
     */
    public Result() {
    }

    /**
     * 全参构造函数
     * 
     * @param code 状态码
     * @param msg 提示信息
     * @param data 返回数据
     */
    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功结果（无数据）
     */
    public static <T> Result<T> success() {
        return new Result<>(200, "操作成功", null);
    }

    /**
     * 成功结果（有数据）
     * 
     * @param data 返回数据
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }

    /**
     * 成功结果（自定义消息和数据）
     * 
     * @param msg 提示信息
     * @param data 返回数据
     */
    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(200, msg, data);
    }

    /**
     * 成功结果（仅消息）
     * 
     * @param msg 提示信息
     */
    public static <T> Result<T> successMsg(String msg) {
        return new Result<>(200, msg, null);
    }

    /**
     * 失败结果（默认错误码）
     * 
     * @param msg 错误信息
     */
    public static <T> Result<T> error(String msg) {
        return new Result<>(500, msg, null);
    }

    /**
     * 失败结果（自定义错误码）
     * 
     * @param code 错误码
     * @param msg 错误信息
     */
    public static <T> Result<T> error(Integer code, String msg) {
        return new Result<>(code, msg, null);
    }

    /**
     * 失败结果（别名方法）
     * 
     * @param msg 错误信息
     */
    public static <T> Result<T> fail(String msg) {
        return new Result<>(500, msg, null);
    }

    /**
     * 失败结果（自定义错误码，别名方法）
     * 
     * @param code 错误码
     * @param msg 错误信息
     */
    public static <T> Result<T> fail(Integer code, String msg) {
        return new Result<>(code, msg, null);
    }

    /**
     * 判断是否成功
     * 
     * @return true-成功, false-失败
     */
    public boolean isSuccess() {
        return this.code != null && this.code == 200;
    }

    /**
     * 获取消息（别名方法）
     * 
     * @return 提示信息
     */
    public String getMessage() {
        return this.msg;
    }
}
