package com.weibo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 豆邮(私信)实体类
 * 对应数据库表: doumail
 */
@Data
public class Doumail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 豆邮ID
     */
    private Integer doumailId;

    /**
     * 发送者用户ID
     */
    private Integer fromUserId;

    /**
     * 发送者头像
     */
    private String fromUserImg;

    /**
     * 发送者昵称
     */
    private String fromUserNick;

    /**
     * 接收者用户ID
     */
    private Integer toUserId;

    /**
     * 接收者头像
     */
    private String toUserImg;

    /**
     * 接收者昵称
     */
    private String toUserNick;

    /**
     * 聊天内容
     */
    private String chatMsg;

    /**
     * 聊天时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date chatTime;

    /**
     * 删除状态(0-双方都未删除, 1-发送者删除, 2-接收者删除)
     */
    private Integer status;

    /**
     * 阅读状态(0-未读, 1-已读)
     */
    private Integer read;
}
