package com.weibo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer articleId;

    private String title;

    private String tagName;

    private Integer authorId;

    private String authorNick;

    private String authorImg;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date publishedTime;

    private Integer tagId;

    private String content;

    private Integer starNum;

    private Integer collectionNum;

    private Integer commentNum;

    private Integer shareNum;

    private Integer pageView;

    private Integer stick;
}
