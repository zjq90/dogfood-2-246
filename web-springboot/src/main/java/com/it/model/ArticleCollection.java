package com.it.model;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleCollection {
    private Integer collectionId;
    private Integer userId;
    private Integer articleId;
    private Date createTime;
}
