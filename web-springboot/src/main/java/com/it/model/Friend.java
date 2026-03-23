package com.it.model;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class Friend implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer friendId;
    private Integer fromUserId;
    private Integer toUserId;
    private Integer userId;
    private Integer followerId;
    private Integer status;
    private Integer groupId;
    private Date createTime;
}
