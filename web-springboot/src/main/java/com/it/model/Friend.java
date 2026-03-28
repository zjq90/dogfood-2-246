package com.it.model;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

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

    // ==================== 兼容字段 ====================

    /**
     * 用户ID（兼容）
     */
    private Integer userId;

    /**
     * 关注者ID（兼容）
     */
    private Integer followerId;

    /**
     * 创建时间（兼容）
     */
    private Date createTime;
}
