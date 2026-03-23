package com.it.model;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleStar {
    private Integer starId;
    private Integer userId;
    private Integer articleId;
    private Date createTime;
}
