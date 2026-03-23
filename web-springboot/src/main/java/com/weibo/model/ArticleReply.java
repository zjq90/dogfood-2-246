package com.weibo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 文章回复实体类
 * 对应数据库表: a_reply
 */
@Data
public class ArticleReply implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 回复ID
     */
    private Integer replyId;

    /**
     * 评论ID
     */
    private Integer commentId;

    /**
     * 回复内容
     */
    private String replyMsg;

    /**
     * 回复者用户ID
     */
    private Integer userReplyFromId;

    /**
     * 被回复用户ID
     */
    private Integer userReplyToId;

    /**
     * 回复者头像
     */
    private String userReplyImg;

    /**
     * 被回复者昵称
     */
    private String userReplyToNick;

    /**
     * 回复者昵称
     */
    private String userReplyFromNick;

    /**
     * 回复点赞数
     */
    private Integer replyStar;

    /**
     * 回复时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date replyTime;
}
