package com.weibo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 文章实体类
 * 对应数据库表: a_article
 */
@Data
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文章ID
     */
    private Integer articleId;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章分类名
     */
    private String tagName;

    /**
     * 文章作者ID
     */
    private Integer authorId;

    /**
     * 作者昵称
     */
    private String authorNick;

    /**
     * 作者头像
     */
    private String authorImg;

    /**
     * 发表时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date publishedTime;

    /**
     * 分类ID
     */
    private Integer tagId;

    /**
     * 文章内容(HTML)
     */
    private String content;

    /**
     * 点赞数
     */
    private Integer starNum;

    /**
     * 收藏数
     */
    private Integer collectionNum;

    /**
     * 评论数
     */
    private Integer commentNum;

    /**
     * 转发数
     */
    private Integer shareNum;

    /**
     * 浏览量
     */
    private Integer pageView;

    /**
     * 是否置顶(0-否, 1-是)
     */
    private Integer stick;
}
