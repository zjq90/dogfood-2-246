package com.weibo.model;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 文章点赞实体类
 * 对应数据库表: a_star
 */
@Data
public class ArticleStar implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 点赞ID
     */
    private Integer starId;

    /**
     * 对象ID（文章ID/评论ID/回复ID）
     */
    private Integer typeId;

    /**
     * 对象类型：1-文章；2-评论；3-回复
     */
    private Integer type;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 点赞时间
     */
    private Date createTime;
}
