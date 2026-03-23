package com.weibo.model;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class Friend implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer friendId;

    private Integer fromUserId;

    private Integer toUserId;

    private Integer state;

    private Integer groupId;

    private Date time;

    private String username;

    private String nickname;

    private String portrait;
}
