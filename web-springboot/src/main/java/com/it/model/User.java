package com.it.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;


@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    
    private Integer userId;

    
    private String username;

    
    private String password;

    
    private Integer status;

    
    private Integer reported;

    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date titleTime;

    
    private String portrait;

    
    private String signature;

    
    private String selfIntroduc;

    
    private String nickname;

    
    private String address;

    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;

    
    private Integer code;

    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date outTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
