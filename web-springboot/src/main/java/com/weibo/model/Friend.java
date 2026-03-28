package com.weibo.model;

import lombok.Data;
import java.io.Serializable;

/**
 * 好友关系实体类
 * 对应数据库表: friend
 */
@Data
public class Friend implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 好友关系ID
     */
    private Integer friendId;

    /**
     * 关系发起者用户ID
     */
    private Integer fromUserId;

    /**
     * 关系接收者用户ID
     */
    private Integer toUserId;

    /**
     * 关系状态(1-关注, 2-互为好友, 3-拉黑)
     */
    private Integer status;

    /**
     * 好友分组ID
     */
    private Integer groupId;
}
