package com.weibo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体类
 * 对应数据库表: user
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户名(邮箱)
     */
    private String username;

    /**
     * 密码(MD5加密)
     */
    private String password;

    /**
     * 用户权限(0-普通用户, 1-管理员)
     */
    private Integer status;

    /**
     * 是否被举报(0-未举报, 1-被举报)
     */
    private Integer reported;

    /**
     * 封号截止时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date titleTime;

    /**
     * 用户头像路径
     */
    private String portrait;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 自我介绍
     */
    private String selfIntroduc;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户地址
     */
    private String address;

    /**
     * 用户注册时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;

    /**
     * 找回密码凭证(UUID)
     */
    private Integer code;

    /**
     * 找回密码时间限制
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date outTime;
}
