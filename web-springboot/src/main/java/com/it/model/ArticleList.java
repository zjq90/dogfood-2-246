package com.weibo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 文章列表实体类
 * 用于文章列表展示
 */
@Data
public class ArticleList implements Serializable {

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
     * 发表时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date publishedTime;

    /**
     * 文章分类名
     */
    private String tagName;

    /**
     * 作者ID
     */
    private Integer authorId;

    /**
     * 作者头像
     */
    private String authorImg;

    /**
     * 作者昵称
     */
    private String authorNick;

    /**
     * 转发数
     */
    private Integer shareNum;

    /**
     * 点赞数
     */
    private Integer starNum;

    /**
     * 评论数
     */
    private Integer commentNum;

    /**
     * 收藏数
     */
    private Integer collectionNum;

    /**
     * 文章内容预览
     */
    private String content;

    /**
     * 文章第一张图片
     */
    private String firstImg;

    /**
     * 是否置顶
     */
    private Integer stick;

    /**
     * 浏览量
     */
    private Integer pageView;
}
