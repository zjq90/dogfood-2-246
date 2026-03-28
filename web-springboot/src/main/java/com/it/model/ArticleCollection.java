package com.weibo.model;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 文章收藏实体类
 * 对应数据库表: a_collection
 */
@Data
public class ArticleCollection implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 收藏ID
     */
    private Integer collectionId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 文章ID
     */
    private Integer articleId;

    /**
     * 收藏时间
     */
    private Date collectionTime;
}
