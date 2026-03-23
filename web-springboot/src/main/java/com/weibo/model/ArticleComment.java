package com.weibo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 文章评论实体类
 * 对应数据库表: a_comment
 */
@Data
public class ArticleComment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评论ID
     */
    private Integer commentId;

    /**
     * 文章ID
     */
    private Integer articleId;

    /**
     * 评论内容
     */
    private String comMsg;

    /**
     * 评论用户ID
     */
    private Integer userComId;

    /**
     * 评论用户头像
     */
    private String userComImg;

    /**
     * 评论用户昵称
     */
    private String userComNick;

    /**
     * 评论点赞数
     */
    private Integer comStar;

    /**
     * 评论时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date comTime;

    /**
     * 当前用户点赞状态(0-未点赞, 1-已点赞)
     */
    private Integer starStatus;
}
