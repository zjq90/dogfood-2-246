package com.weibo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class Doumail implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer doumailId;

    private Integer fromUserId;

    private String fromUserImg;

    private String fromUserNick;

    private Integer toUserId;

    private String toUserImg;

    private String toUserNick;

    private String chatMsg;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;

    private Integer status;

    private Integer isRead;
}
